package com.aldar.roguelike.map.data;

import lombok.Data;

@Data
public class GridMetadata {

    private final int width;
    private final int height;

    public int size() {
        return width * height;
    }

    public static GridMetadata of(final int width, final int height) {
        return new GridMetadata(width, height);
    }
}