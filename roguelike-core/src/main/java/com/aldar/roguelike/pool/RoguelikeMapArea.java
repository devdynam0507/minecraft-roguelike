package com.aldar.roguelike.pool;

import com.aldar.roguelike.pathfind.location.VirtualLocation3D;

import lombok.Data;

@Data
public class RoguelikeMapArea {

    private final int index;
    private final int width;
    private final int height;
    private final VirtualLocation3D start;
    private final VirtualLocation3D end;
}
