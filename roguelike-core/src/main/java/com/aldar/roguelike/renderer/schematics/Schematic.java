package com.aldar.roguelike.renderer.schematics;

import java.io.File;

import com.aldar.roguelike.map.type.RoomType;
import com.sk89q.worldedit.EditSession;

import lombok.Data;

@Data
public class Schematic {

    private final File file;
    private final RoomType roomType;
    private final int rotate;
    private EditSession editSession;
}
