package com.aldar.roguelike.map;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import com.aldar.roguelike.map.data.GridMetadata;
import com.aldar.roguelike.map.data.RoomGenerationOption;
import com.aldar.roguelike.map.data.RoomMetadata;
import com.aldar.roguelike.map.type.RoomType;
import com.aldar.roguelike.utils.Pair;

class GraphBasedRoomGeneratorTest {

    private final RoomGeneratorStrategy<Graph> strategy = new GraphBasedRoomGenerator();

    @Test
    void graphBasedRoomGenTest() {
        final RoomGenerationOption roomGenerationOption =
                new RoomGenerationOption(14,
                                         10,
                                         10,
                                         10,
                                         10);
        final GridMetadata gridMetadata = GridMetadata.of(16, 16);
        final Graph rooms = strategy.generate(roomGenerationOption, gridMetadata);
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
    }
}