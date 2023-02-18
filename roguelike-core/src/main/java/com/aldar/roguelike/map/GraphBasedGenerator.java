package com.aldar.roguelike.map;

import java.util.List;
import java.util.Map;

import com.aldar.roguelike.map.data.GridMetadata;
import com.aldar.roguelike.map.data.RoomGenerationOption;
import com.aldar.roguelike.map.data.RoomMetadata;
import com.aldar.roguelike.map.type.RoomType;
import com.aldar.roguelike.utils.Pair;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GraphBasedGenerator {

    private final RoomGeneratorStrategy<Graph> roomGeneratorStrategy;
    private final RoadGeneratorStrategy<Graph> roadGeneratorStrategy;

    public VirtualGrid generate(
            final RoomGenerationOption roomGenerationOption, final GridMetadata gridMetadata) {
        final Graph rooms = roomGeneratorStrategy.generate(roomGenerationOption, gridMetadata);
        final Pair<List<RoomMetadata>, Map<Integer, List<RoomMetadata>>> graphPair = rooms.getGraphPair();
        final VirtualGrid virtualGrid = new VirtualGrid(gridMetadata);
        for (final RoomMetadata roomMetadata : graphPair.getLeft()) {
            List<RoomMetadata> roomMetadata1 = graphPair.getRight().get(roomMetadata.getIndex());
            for (final RoomMetadata roomMetadata2 : roomMetadata1) {
                virtualGrid.setItem(roomMetadata2.getX(), roomMetadata2.getZ(), RoomType.ROOM);
            }
            virtualGrid.setItem(roomMetadata.getX(), roomMetadata.getZ(), RoomType.ROOM);
        }
        return roadGeneratorStrategy.generate(virtualGrid, rooms);
    }
}