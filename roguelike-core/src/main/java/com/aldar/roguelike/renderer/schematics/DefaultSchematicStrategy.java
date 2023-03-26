package com.aldar.roguelike.renderer.schematics;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.aldar.roguelike.map.type.RoomType;

public class DefaultSchematicStrategy implements SchematicStrategy {

    private final Map<String, File> schematicFile;

    public DefaultSchematicStrategy(final File[] schematicFiles) {
        this.schematicFile = new HashMap<>();
        for (final File file : schematicFiles) {
            if (file.getName().equals(".DS_Store")) {
                continue;
            }
            if (file.getName().equals("cross_chest.schem")) {
                schematicFile.put("room", file);
            }
            else if(file.getName().equals("rightAngle_path.schem")) {
                schematicFile.put("corner_road", file);
            }
            else {
                schematicFile.put("straight_road", file);
            }
        }
    }

    @Override
    public Schematic getSchematic(final RoomType roomType) {
        switch (roomType) {
            case ROOM -> {return new Schematic(schematicFile.get("room"), roomType, 0);}
            case ROAD_EAST_WEST -> {return new Schematic(schematicFile.get("straight_road"), roomType, 0);}
            case ROAD_NORTH_SOUTH -> {return new Schematic(schematicFile.get("straight_road"), roomType, 90);}
            case ROAD_CORNER_EAST_SOUTH -> {
                return new Schematic(schematicFile.get("corner_road"), roomType, 180);
            }
            case ROAD_CORNER_WEST_SOUTH -> {
                return new Schematic(schematicFile.get("corner_road"), roomType, 270);
            }
            case ROAD_CORNER_SOUTH_WEST -> {
                return new Schematic(schematicFile.get("corner_road"), roomType, 90);
            }
            case ROAD_CORNER_SOUTH_EAST -> {
                return new Schematic(schematicFile.get("corner_road"), roomType, 0);
            }
        }
        return null;
    }
}
