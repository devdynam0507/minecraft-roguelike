package com.aldar.roguelike.map;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GraphBasedGenerator {

    private final RoomGeneratorStrategy<Graph> roomGeneratorStrategy;
    private final RoadGeneratorStrategy<Graph> roadGeneratorStrategy;
}