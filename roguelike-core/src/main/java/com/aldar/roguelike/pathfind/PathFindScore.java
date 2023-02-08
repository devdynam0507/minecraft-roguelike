package com.aldar.roguelike.pathfind;

import com.aldar.roguelike.pathfind.location.VirtualLocation3D;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class PathFindScore {

    private final int g, h;
    private final VirtualLocation3D location;
    private final Directions directions;

    public int getScore() {
        return g + h;
    }

    public static PathFindScore of(
            final int g, final int h, final VirtualLocation3D location, final Directions directions) {
        return new PathFindScore(g, h, location, directions);
    }
}