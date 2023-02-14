package com.aldar.roguelike.pathfind;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.aldar.roguelike.map.type.RoomType;
import com.aldar.roguelike.pathfind.location.VirtualLocation3D;

import lombok.Getter;

@Getter
public final class Path {

    private final Directions originalDirection;
    private final VirtualLocation3D location;

    @Nullable
    private RoomType roomType;

    public Path(final Directions directions, final VirtualLocation3D location) {
        this.originalDirection = directions;
        this.location = location;
        this.roomType = null;
    }

    void setRoomType(@NotNull final RoomType roomType) {
        this.roomType = roomType;
    }

    public static Path of(final PathFindScore pathFindScore) {
        return new Path(pathFindScore.getDirections(), pathFindScore.getLocation());
    }
}