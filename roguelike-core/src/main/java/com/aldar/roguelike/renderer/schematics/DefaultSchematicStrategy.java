package com.aldar.roguelike.renderer.schematics;

import com.aldar.roguelike.RoguelikePlugin;
import com.aldar.roguelike.map.type.RoomType;

public class DefaultSchematicStrategy implements SchematicStrategy {

    @Override
    public Schematic getSchematic(final RoomType roomType) {
        switch (roomType) {
            case ROOM -> new Schematic(RoguelikePlugin.schematics[0], roomType, 0);
            case ROAD_EAST_WEST -> new Schematic(RoguelikePlugin.schematics[2], roomType, 0);
            case ROAD_NORTH_SOUTH -> new Schematic(RoguelikePlugin.schematics[2], roomType, 90);
            case ROAD_CORNER_EAST_SOUTH -> new Schematic(RoguelikePlugin.schematics[1], roomType, 0);
            case ROAD_CORNER_WEST_SOUTH -> new Schematic(RoguelikePlugin.schematics[1], roomType, 90);
            case ROAD_CORNER_SOUTH_WEST -> new Schematic(RoguelikePlugin.schematics[1], roomType, 180);
            case ROAD_CORNER_SOUTH_EAST -> new Schematic(RoguelikePlugin.schematics[1], roomType, 270);
        }
        return null;
    }
}
