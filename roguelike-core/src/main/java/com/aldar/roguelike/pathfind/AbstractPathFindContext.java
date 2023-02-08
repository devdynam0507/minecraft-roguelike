package com.aldar.roguelike.pathfind;

import java.util.List;

import com.aldar.roguelike.pathfind.location.VirtualLocation3D;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractPathFindContext<DataType, T extends PathFind<DataType>> {

    private final T pathFind;

    abstract public List<DataType> pathfinding(final VirtualLocation3D origin, final VirtualLocation3D dest);

    protected T getPathFind() {
        return pathFind;
    }

    protected boolean isDiagonals(final Directions directions) {
        return directions == Directions.NORTH_EAST ||
               directions == Directions.NORTH_WEST ||
               directions == Directions.SOUTH_EAST ||
               directions == Directions.SOUTH_WEST;
    }
}