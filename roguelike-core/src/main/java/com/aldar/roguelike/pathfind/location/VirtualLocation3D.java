package com.aldar.roguelike.pathfind.location;

import org.bukkit.Location;
import org.bukkit.World;
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

    /**
     * GridSize 기준으로 위치를 정규화 한 후 실제 마인크래프트 Location 객체를 반환합니다.
     * @param world 로그라이크 월드
     * @param gridSize 격자 하나의 너비, 높이
     */
    public Location normalize(final World world, final int gridSize) {
        final VirtualLocation3D normalized = normalize(gridSize);
        return new Location(world, normalized.getX(), normalized.getY(), normalized.getZ());
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

    public VirtualLocation3D normalize(final int gridSize) {
        return VirtualLocation3D.of(x + (gridSize / 2), y, z + (gridSize / 2));
    }

    public static VirtualLocation3D of(final int x, int y, int z) {
        return new VirtualLocation3D(x, y, z);
    }

    public static VirtualLocation3D ofZero() {
        return new VirtualLocation3D(0, 0, 0);
    }
}
