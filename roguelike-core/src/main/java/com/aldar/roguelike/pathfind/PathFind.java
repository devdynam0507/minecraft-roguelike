package com.aldar.roguelike.pathfind;

import java.util.List;

import org.bukkit.Location;

public interface PathFind<T> {

    List<T> getScores(final Location point, final Location dest);
}