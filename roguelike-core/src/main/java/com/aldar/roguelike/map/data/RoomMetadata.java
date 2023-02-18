package com.aldar.roguelike.map.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.aldar.roguelike.pathfind.Directions;
import com.aldar.roguelike.pathfind.location.VirtualLocation3D;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RoomMetadata implements Comparable<RoomMetadata> {

    private final int x, y, z;
    private int minecraftX, minecraftZ;
    private final int index;
    private final int roomWidth;
    private final int roomHeight;
    private final List<Directions> doorDirections;

    public VirtualLocation3D toVirtualLocation() {
        return VirtualLocation3D.of(x, y, z);
    }

    public void addDoors(final Directions... doors) {
        if (doorDirections.size() >= 4) {
            return;
        }
        doorDirections.addAll(Arrays.asList(doors));
    }

    @Override
    public int compareTo(@NotNull final RoomMetadata o) {
        return this.index - o.index;
    }

    public static RoomMetadata of(
            final int x, final int y, final int z, final int index, final int roomWidth, final int roomHeight) {
        return new RoomMetadata(x, y, z, index, roomWidth, roomHeight, new ArrayList<>());
    }
}