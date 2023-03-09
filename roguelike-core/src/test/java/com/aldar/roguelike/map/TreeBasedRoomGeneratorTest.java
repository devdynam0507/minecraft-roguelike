package com.aldar.roguelike.map;

import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import com.aldar.roguelike.map.data.GridMetadata;
import com.aldar.roguelike.map.data.RoomGenerationOption;
import com.aldar.roguelike.map.data.RoomMetadata;
import com.aldar.roguelike.map.type.RoomType;

class TreeBasedRoomGeneratorTest {

    private final RoomGeneratorStrategy<TreeSet<RoomMetadata>> roomGeneratorStrategy =
            new TreeBasedRoomGenerator();

    @Test
    public void treeRoomGenerator() {
        final RoomGenerationOption roomGenerationOption =
                new RoomGenerationOption(16,
                                         10,
                                         10,
                                         10,
                                         10);
        final GridMetadata gridMetadata = GridMetadata.of(16, 32, 32, 32);
        final TreeSet<RoomMetadata> rooms = roomGeneratorStrategy.generate(
                roomGenerationOption, gridMetadata);
        final VirtualGrid virtualGrid = new VirtualGrid(gridMetadata);

        final RoomMetadata first = rooms.first();
        final RoomMetadata child1 = rooms.higher(first);
        System.out.println(first);
        System.out.println(child1);

        for (final RoomMetadata room : rooms) {
            System.out.println(room);
            virtualGrid.setItem(room.getX(), room.getZ(), RoomType.ROOM);
        }
    }
}