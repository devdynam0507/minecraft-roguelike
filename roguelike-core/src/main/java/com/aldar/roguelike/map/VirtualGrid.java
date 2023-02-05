package com.aldar.roguelike.map;

import com.aldar.roguelike.map.data.GridMetadata;
import com.aldar.roguelike.map.type.RoomType;

import lombok.Getter;

@Getter
public class VirtualGrid {

    private final RoomType[][] grid;
    private final GridMetadata gridMetadata;

    public VirtualGrid(final GridMetadata gridMetadata) {
        this.grid = new RoomType[gridMetadata.getWidth()][gridMetadata.getHeight()];
        this.gridMetadata = gridMetadata;
    }

    public void setItem(final int x, final int y, final RoomType roomType) {
        grid[x][y] = roomType;
    }

    public int translate(final int n, final int weight) {
        return (n + 1) * weight;
    }

    public void print() {
        for (int y = 0; y < grid[0].length; y++) {
            for (int x = 0; x < grid.length; x++) {
                if (grid[x][y] != RoomType.ROOM) {
                    System.out.print("□");
                }
                else {
                    System.out.print("■");
                }
            }
            System.out.println();
        }
    }

    public int size() {
        return gridMetadata.size();
    }
}