package com.aldar.roguelike.map;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.aldar.roguelike.map.data.GridMetadata;
import com.aldar.roguelike.map.data.RoomGenerationOption;
import com.aldar.roguelike.map.data.RoomMetadata;
import com.aldar.roguelike.map.type.RoomType;
import com.aldar.roguelike.pathfind.AStarPathFindImpl;
import com.aldar.roguelike.pathfind.AStarPathFindContext;
import com.aldar.roguelike.pathfind.PathFindPostProcessor;

class GraphBasedRoadGeneratorTest {

    final GridMetadata gridMetadata = GridMetadata.of(16, 16, 32, 32);
    final RoomGeneratorStrategy<Graph> roomGenerator =
            new GraphBasedRoomGenerator();
    final AStarPathFindContext pathFindContext = new AStarPathFindContext(new AStarPathFindImpl());
    final PathFindPostProcessor postProcessor = new PathFindPostProcessor();
    RoadGeneratorStrategy<Graph> roadGeneratorStrategy = new GraphBasedRoadGenerator(
            gridMetadata, pathFindContext, postProcessor);

    @Test
    void test() {
        final RoomGenerationOption roomGenerationOption =
                new RoomGenerationOption(16,
                                         10,
                                         10,
                                         10,
                                         10);
        final Graph rooms = roomGenerator.generate(roomGenerationOption, gridMetadata);
        final VirtualGrid virtualGrid = new VirtualGrid(gridMetadata);
        for (final RoomMetadata roomMetadata : rooms.getGraphPair().getLeft()) {
            List<RoomMetadata> roomMetadata1 = rooms.getGraphPair().getRight().get(roomMetadata.getIndex());
            for (final RoomMetadata roomMetadata2 : roomMetadata1) {
                virtualGrid.setItem(roomMetadata2.getX(), roomMetadata2.getZ(), RoomType.ROOM);
            }
            System.out.println("[" + roomMetadata.getIndex() + "] " + roomMetadata1);
            virtualGrid.setItem(roomMetadata.getX(), roomMetadata.getZ(), RoomType.ROOM);
        }
        virtualGrid.print();
        System.out.println("================================");
        final VirtualGrid roadVG = roadGeneratorStrategy.generate(virtualGrid, rooms);
        roadVG.print();
    }
}