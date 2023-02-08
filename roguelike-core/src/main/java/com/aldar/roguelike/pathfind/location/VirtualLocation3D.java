package com.aldar.roguelike.pathfind.location;

import org.bukkit.Location;
import org.bukkit.util.NumberConversions;
import org.jetbrains.annotations.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VirtualLocation3D implements Cloneable {

    private int x, y, z;

    public VirtualLocation3D add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Location toMinecraftLocation() {
        // TODO: Convert minecraft location
        return null;
    }

    @Override
    @NotNull
    public VirtualLocation3D clone() {
        try {
            return (VirtualLocation3D) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    public double distance(final VirtualLocation3D o) {
        if (o == null) {
            throw new IllegalArgumentException("Target location's cannot be null");
        }
        return NumberConversions.square(x - o.x) +
               NumberConversions.square(y - o.y) +
               NumberConversions.square(z - o.z);
    }

    public static VirtualLocation3D of(final int x, int y, int z) {
        return new VirtualLocation3D(x, y, z);
    }

    public static VirtualLocation3D ofZero() {
        return new VirtualLocation3D(0, 0, 0);
    }
}
