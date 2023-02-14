package com.aldar.roguelike.map;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.aldar.roguelike.map.data.GridMetadata;
import com.aldar.roguelike.map.data.RoomGenerationOption;
import com.aldar.roguelike.map.data.RoomMetadata;
import com.aldar.roguelike.map.type.RoomType;
import com.aldar.roguelike.pathfind.AStarPathFindImpl;
import com.aldar.roguelike.pathfind.AStarPathFindContext;
import com.aldar.roguelike.pathfind.PathFindPostProcessor;
import com.aldar.roguelike.utils.Pair;

class GraphBasedRoadGeneratorTest {

    final GridMetadata gridMetadata = GridMetadata.of(16, 16);
    final RoomGeneratorStrategy<Pair<List<RoomMetadata>, Map<Integer, List<RoomMetadata>>>> roomGenerator =
            new GraphBasedRoomGenerator();
    final PathFindPostProcessor postProcessor = new PathFindPostProcessor();
    RoadGeneratorStrategy<Pair<List<RoomMetadata>, Map<Integer, List<RoomMetadata>>>> roadGeneratorStrategy =
            new GraphBasedRoadGenerator(gridMetadata, new AStarPathFindContext(new AStarPathFindImpl()), postProcessor);

    @Test
    void test() {
        final RoomGenerationOption roomGenerationOption =
                new RoomGenerationOption(16,
                                         10,
                                         10,
                                         10,
                                         10);
        final Pair<List<RoomMetadata>, Map<Integer, List<RoomMetadata>>> rooms = roomGenerator.generate(
                roomGenerationOption, gridMetadata);
        final VirtualGrid virtualGrid = new VirtualGrid(gridMetadata);
        for (final RoomMetadata roomMetadata : rooms.getLeft()) {
            List<RoomMetadata> roomMetadata1 = rooms.getRight().get(roomMetadata.getIndex());
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