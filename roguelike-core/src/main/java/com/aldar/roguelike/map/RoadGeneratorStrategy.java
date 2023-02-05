package com.aldar.roguelike.map;

public interface RoadGeneratorStrategy<T> {

    VirtualGrid generate(final T generatedRooms);
}
