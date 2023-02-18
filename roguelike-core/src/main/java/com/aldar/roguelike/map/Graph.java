package com.aldar.roguelike.map;

import java.util.List;
import java.util.Map;

import com.aldar.roguelike.map.data.RoomMetadata;
import com.aldar.roguelike.utils.Pair;

import lombok.Data;

@Data
public class Graph {

    private final Pair<List<RoomMetadata>, Map<Integer, List<RoomMetadata>>> graphPair;

    public static Graph to(final Pair<List<RoomMetadata>, Map<Integer, List<RoomMetadata>>> graphPair) {
        return new Graph(graphPair);
    }
}