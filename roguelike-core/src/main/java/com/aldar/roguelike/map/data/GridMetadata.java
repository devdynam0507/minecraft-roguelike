package com.aldar.roguelike.map.data;

import lombok.Data;

@Data
public class GridMetadata {

    private final int width;
    private final int height;
    private final int gridBlockWidth;
    private final int gridBlockHeight;

    public int size() {
        return width * height;
    }

    public static GridMetadata of(
            final int width, final int height, final int gridBlockWidth, final int gridBlockHeight) {
        return new GridMetadata(width, height, gridBlockWidth, gridBlockHeight);
    }
}