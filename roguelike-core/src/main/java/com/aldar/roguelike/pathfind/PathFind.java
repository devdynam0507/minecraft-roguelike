package com.aldar.roguelike.pathfind;

import java.util.List;

import com.aldar.roguelike.pathfind.location.VirtualLocation3D;

public interface PathFind<T> {

    List<T> getScores(final VirtualLocation3D point, final VirtualLocation3D dest);
}