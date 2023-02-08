package com.aldar.roguelike.map;

public interface RoadGeneratorStrategy<T> {

    VirtualGrid generate(final T generatedRooms);

    VirtualGrid generate(final VirtualGrid virtualGrid, final T generatedRooms);
}