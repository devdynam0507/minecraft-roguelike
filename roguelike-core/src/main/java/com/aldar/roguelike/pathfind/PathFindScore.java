package com.aldar.roguelike.pathfind;

import org.bukkit.Location;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class PathFindScore {

    private final int g, h;
    private final Location location;
    private final Directions directions;

    public int getScore() {
        return g + h;
    }

    public static PathFindScore of(
            final int g, final int h, final Location location, final Directions directions) {
        return new PathFindScore(g, h, location, directions);
    }
}