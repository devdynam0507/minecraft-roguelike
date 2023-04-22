package com.aldar.roguelike.map;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.aldar.roguelike.map.data.GridMetadata;
import com.aldar.roguelike.map.data.RoomGenerationOption;
import com.aldar.roguelike.map.data.RoomMetadata;
import com.aldar.roguelike.map.type.RoomType;
import com.aldar.roguelike.pathfind.AStarPathFindContext;
import com.aldar.roguelike.pathfind.AStarPathFindImpl;
import com.aldar.roguelike.pathfind.PathFindPostProcessor;

class VirtualGreed3WayPostProcessorTest {


    final GridMetadata gridMetadata = GridMetadata.of(16, 16, 32, 32);
    final RoomGeneratorStrategy<Graph> roomGenerator =
            new GraphBasedRoomGenerator();
    final AStarPathFindContext pathFindContext = new AStarPathFindContext(new AStarPathFindImpl());
    final PathFindPostProcessor postProcessor = new PathFindPostProcessor();
    RoadGeneratorStrategy<Graph> roadGeneratorStrategy = new GraphBasedRoadGenerator(
            gridMetadata, pathFindContext, postProcessor);
    VirtualGrid _virtualGrid;
    VirtualGridPostProcessor virtualGridPostProcessor = new VirtualGreed3WayPostProcessor();

    @BeforeEach
    void before() {
        final RoomGenerationOption roomGenerationOption =
                new RoomGenerationOption(14,
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
            virtualGrid.setItem(roomMetadata.getX(), roomMetadata.getZ(), RoomType.ROOM);
        }
        _virtualGrid = roadGeneratorStrategy.generate(virtualGrid, rooms);
    }

    @Test
    @DisplayName("기존 생성된 길에서 3방향 길로 잘 바뀌는지 테스트")
    void test3WayConvert() {
        System.out.println("================================");
        VirtualGrid virtualGrid = virtualGridPostProcessor.postProcess(_virtualGrid);
        virtualGrid.print();
    }
}