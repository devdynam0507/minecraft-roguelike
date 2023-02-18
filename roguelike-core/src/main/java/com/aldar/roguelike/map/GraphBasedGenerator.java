package com.aldar.roguelike.map;

import java.util.List;
import java.util.Map;

import com.aldar.roguelike.map.data.GridMetadata;
import com.aldar.roguelike.map.data.RoomGenerationOption;
import com.aldar.roguelike.map.data.RoomMetadata;
import com.aldar.roguelike.map.type.RoomType;
import com.aldar.roguelike.pathfind.Directions;
import com.aldar.roguelike.pathfind.location.VirtualLocation3D;
import com.aldar.roguelike.utils.Pair;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GraphBasedGenerator {

    private final RoomGeneratorStrategy<Graph> roomGeneratorStrategy;
    private final RoadGeneratorStrategy<Graph> roadGeneratorStrategy;

    public VirtualGrid generate(
            final RoomGenerationOption roomGenerationOption, final GridMetadata gridMetadata) {
        // 방 생성
        final Graph rooms = roomGeneratorStrategy.generate(roomGenerationOption, gridMetadata);
        final Pair<List<RoomMetadata>, Map<Integer, List<RoomMetadata>>> graphPair = rooms.getGraphPair();
        VirtualGrid virtualGrid = new VirtualGrid(gridMetadata);
        // 생성된 방을 기반으로 VirtualGrid에 세팅
        for (final RoomMetadata roomMetadata : graphPair.getLeft()) {
            final List<RoomMetadata> roomMetadata1 = graphPair.getRight().get(roomMetadata.getIndex());
            for (final RoomMetadata roomMetadata2 : roomMetadata1) {
                virtualGrid.setItem(roomMetadata2.getX(), roomMetadata2.getZ(), RoomType.ROOM);
            }
            virtualGrid.setItem(roomMetadata.getX(), roomMetadata.getZ(), RoomType.ROOM);
        }
        // 길 생성
        virtualGrid = roadGeneratorStrategy.generate(virtualGrid, rooms);
        for (final RoomMetadata roomMetadata : graphPair.getLeft()) {
            final List<RoomMetadata> roomMetadata1 = graphPair.getRight().get(roomMetadata.getIndex());
            decisionDoorDirection(virtualGrid, roomMetadata);
            for (final RoomMetadata roomMetadata2 : roomMetadata1) {
                decisionDoorDirection(virtualGrid, roomMetadata2);
            }
            virtualGrid.setItem(roomMetadata.getX(), roomMetadata.getZ(), RoomType.ROOM);
        }
        return virtualGrid;
    }

    private void decisionDoorDirection(final VirtualGrid grid, final RoomMetadata roomMetadata) {
        final VirtualLocation3D location = roomMetadata.toVirtualLocation();
        final VirtualLocation3D south = location.clone().add(0, 0, 1);
        final VirtualLocation3D west = location.add(-1, 0, 0);
        final VirtualLocation3D north = location.add(0, 0, -1);
        final VirtualLocation3D east = location.add(1, 0, 0);
        if (isRoad(grid, south.getX(), south.getZ())) {
            roomMetadata.addDoors(Directions.SOUTH);
        }
        if (isRoad(grid, west.getX(), west.getZ())) {
            roomMetadata.addDoors(Directions.WEST);
        }
        if (isRoad(grid, east.getX(), east.getZ())) {
            roomMetadata.addDoors(Directions.EAST);
        }
        if (isRoad(grid, north.getX(), north.getZ())) {
            roomMetadata.addDoors(Directions.NORTH);
        }
    }

    private boolean isRoad(final VirtualGrid grid, final int x, final int y) {
        return grid.getItem(x, y).name().startsWith("ROAD");
    }
}