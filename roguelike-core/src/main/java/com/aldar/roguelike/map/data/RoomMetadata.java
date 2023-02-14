package com.aldar.roguelike.map.data;

import org.jetbrains.annotations.NotNull;

import com.aldar.roguelike.pathfind.location.VirtualLocation3D;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
public class RoomMetadata implements Comparable<RoomMetadata> {

    private final int x, y, z;
    private int minecraftX, minecraftZ;
    private final int index;
    private final int roomWidth;
    private final int roomHeight;

    public VirtualLocation3D toVirtualLocation() {
        return VirtualLocation3D.of(x, y, z);
    }

    @Override
    public int compareTo(@NotNull final RoomMetadata o) {
        return this.index - o.index;
    }

    public static RoomMetadata of(
            final int x, final int y, final int z, final int index, final int roomWidth, final int roomHeight) {
        return new RoomMetadata(x, y, z, index, roomWidth, roomHeight);
    }
}