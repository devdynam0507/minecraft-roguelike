package com.aldar.roguelike.map;

import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingEnumeration;

import com.aldar.roguelike.map.type.RoomType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VirtualGreed3WayPostProcessor implements VirtualGridPostProcessor {

    static int smear(int hashCode) {
        hashCode ^= (hashCode >>> 20) ^ (hashCode >>> 12);
        return hashCode ^ (hashCode >>> 7) ^ (hashCode >>> 4);
    }

    private boolean matchFlowDirection(final String direction1, final String direction2) {
        return direction1.equals(direction2);
    }

    @Override
    public VirtualGrid postProcess(final VirtualGrid virtualGrid) {
        final int width = virtualGrid.width();
        final int height = virtualGrid.height();

        for(int x = 0; x < width - 1; x++) {
            for(int y = 0; y < height; y++) {
                final RoomType currentElement = virtualGrid.getItem(x, y);
                final RoomType nextElement = virtualGrid.getItem(x + 1, y);
                if (currentElement == RoomType.NONE) {
                    continue;
                }
                if (currentElement == RoomType.ROAD_EAST_WEST &&
                    nextElement == RoomType.ROAD_CORNER_WEST_SOUTH ||
                    currentElement == RoomType.ROOM &&
                    nextElement == RoomType.ROAD_CORNER_WEST_SOUTH) {
                    virtualGrid.setItemForce(x + 1, y, RoomType.ROAD_3WAY_NORTH_EAST_WEST);
                }
                if (currentElement == RoomType.ROAD_CORNER_EAST_SOUTH &&
                    nextElement == RoomType.ROOM) {
                    virtualGrid.setItemForce(x, y, RoomType.ROAD_3WAY_NORTH_EAST_WEST);
                }
                //┓━
                if (currentElement == RoomType.ROAD_CORNER_EAST_SOUTH &&
                    (nextElement == RoomType.ROAD_EAST_WEST || nextElement == RoomType.ROAD_3WAY_NORTH_EAST_WEST)) {
                    virtualGrid.setItemForce(x, y, RoomType.ROAD_3WAY_NORTH_EAST_WEST);
                }
                if (currentElement == RoomType.ROAD_CORNER_WEST_SOUTH &&
                    nextElement == currentElement) {
                    virtualGrid.setItemForce(x + 1, y, RoomType.ROAD_3WAY_NORTH_EAST_WEST);
                }
                if (currentElement == RoomType.ROAD_CORNER_SOUTH_WEST &&
                    nextElement == RoomType.ROAD_EAST_WEST) {
                    virtualGrid.setItemForce(x, y, RoomType.ROAD_3WAY_SOUTH_EAST_WEST);
                }
                if (currentElement == RoomType.ROAD_EAST_WEST &&
                    nextElement == RoomType.ROAD_CORNER_SOUTH_EAST) {
                    virtualGrid.setItemForce(x + 1, y, RoomType.ROAD_3WAY_SOUTH_EAST_WEST);
                }
                // ┃┓ ┃┚
                if (currentElement == RoomType.ROAD_NORTH_SOUTH &&
                    (nextElement == RoomType.ROAD_CORNER_EAST_SOUTH || nextElement == RoomType.ROAD_CORNER_SOUTH_WEST)) {
                    virtualGrid.setItemForce(x, y, RoomType.ROAD_3WAY_WEST_NORTH_SOUTH);

                }
                //┖┃
                if (currentElement == RoomType.ROAD_CORNER_SOUTH_EAST &&
                    nextElement == RoomType.ROAD_NORTH_SOUTH) {
                    virtualGrid.setItemForce(x + 1, y, RoomType.ROAD_3WAY_EAST_NORTH_SOUTH);
                }
                // ┚┓
                if (currentElement == RoomType.ROAD_CORNER_SOUTH_WEST &&
                    nextElement == RoomType.ROAD_CORNER_EAST_SOUTH) {
                    virtualGrid.setItemForce(x, y, RoomType.ROAD_3WAY_SOUTH_EAST_WEST);
                }
                //┓┚
                if (currentElement == RoomType.ROAD_CORNER_EAST_SOUTH &&
                    nextElement == RoomType.ROAD_CORNER_SOUTH_WEST) {
                    virtualGrid.setItemForce(x, y, RoomType.ROAD_3WAY_NORTH_EAST_WEST);
                }
                // ┍┖ ┖┍
                if (currentElement == RoomType.ROAD_CORNER_WEST_SOUTH &&
                    nextElement == RoomType.ROAD_CORNER_SOUTH_EAST ||
                    currentElement == RoomType.ROAD_CORNER_SOUTH_EAST &&
                    nextElement == RoomType.ROAD_CORNER_WEST_SOUTH) {
                    virtualGrid.setItemForce(x + 1, y, RoomType.ROAD_3WAY_SOUTH_EAST_WEST);
                }
                // ┳┖
                if (currentElement == RoomType.ROAD_3WAY_NORTH_EAST_WEST &&
                    nextElement == RoomType.ROAD_CORNER_SOUTH_EAST) {
                    virtualGrid.setItemForce(x + 1, y, RoomType.ROAD_3WAY_SOUTH_EAST_WEST);
                }
                // ■┚■
                if (currentElement == RoomType.ROOM &&
                    (nextElement == RoomType.ROAD_CORNER_SOUTH_WEST || nextElement == RoomType.ROAD_CORNER_SOUTH_EAST)) {
                    if (x + 2 >= width || virtualGrid.getItem(x + 2, y) != RoomType.ROOM) {
                        continue;
                    }
                    virtualGrid.setItemForce(x + 1, y, RoomType.ROAD_3WAY_SOUTH_EAST_WEST);
                }
                // ┚┚
                if (currentElement == RoomType.ROAD_CORNER_SOUTH_EAST && nextElement == currentElement) {
                    virtualGrid.setItemForce(x, y, RoomType.ROAD_3WAY_SOUTH_EAST_WEST);
                }
            }
        }
        return virtualGrid;
    }
}
