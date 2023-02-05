package com.aldar.roguelike.map.data;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class RoomMetadata implements Comparable<RoomMetadata> {

    private final int x, y, z;
    private int minecraftX, minecraftZ;
    private final int index;
    private final int roomWidth;
    private final int roomHeight;

    @Override
    public int compareTo(@NotNull final RoomMetadata o) {
        return this.index - o.index;
    }

    public static RoomMetadata of(
            final int x, final int y, final int z, final int index, final int roomWidth, final int roomHeight) {
        return new RoomMetadata(x, y, z, index, roomWidth, roomHeight);
    }
}