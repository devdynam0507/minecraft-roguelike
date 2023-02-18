package com.aldar.roguelike.map;

import com.aldar.roguelike.map.data.GridMetadata;
import com.aldar.roguelike.map.type.RoomType;

import lombok.Getter;

@Getter
public class VirtualGrid {

    private final RoomType[][] grid;
    private final GridMetadata gridMetadata;

    public VirtualGrid(final GridMetadata gridMetadata) {
        this.grid = new RoomType[gridMetadata.getWidth()][gridMetadata.getHeight()];
        this.gridMetadata = gridMetadata;
    }

    public boolean exists(final int x, final int y) {
        return grid[x][y] != null;
    }

    public void setItem(final int x, final int y, final RoomType roomType) {
        if (roomType == null) {
            return;
        }
        if (grid[x][y] != null) {
            return;
        }
        grid[x][y] = roomType;
    }

    public RoomType getItem(final int x, final int y) {
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length) {
            return RoomType.NONE;
        }
        if (grid[x][y] == null) {
            return RoomType.NONE;
        }
        return grid[x][y];
    }

    public int translate(final int n, final int weight) {
        return (n + 1) * weight;
    }

    public void print() {
        for (int y = 0; y < grid[0].length; y++) {
            for (int x = 0; x < grid.length; x++) {
                RoomType roomType = grid[x][y];
                if (roomType == null) {
                    roomType = RoomType.NONE;
                }
                switch (roomType) {
                    case ROOM -> System.out.print("■");
                    case ROAD -> System.out.print("R");
                    case ROAD_NORTH_SOUTH -> System.out.print("┃");
                    case ROAD_EAST_WEST -> System.out.print("━");
                    case ROAD_CORNER_EAST_SOUTH -> System.out.print("┓");
                    case ROAD_CORNER_SOUTH_EAST -> System.out.print("┖");
                    case ROAD_CORNER_SOUTH_WEST -> System.out.print("┚");
                    case ROAD_CORNER_WEST_SOUTH -> System.out.print("┍");
                    default -> System.out.print("□");
                }
            }
            System.out.println();
        }
    }

    public int size() {
        return gridMetadata.size();
    }
}