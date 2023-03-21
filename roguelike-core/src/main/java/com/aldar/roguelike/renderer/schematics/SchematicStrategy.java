package com.aldar.roguelike.renderer.schematics;

import com.aldar.roguelike.map.type.RoomType;

public interface SchematicStrategy {

    Schematic getSchematic(final RoomType roomType);
}
