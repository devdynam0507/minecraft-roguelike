package com.aldar.roguelike.map.data;

import lombok.Data;

@Data
public class RoomGenerationOption {

    private final int roomAmount;
    private final float battleRoomRatio;
    private final float boxRoomRatio;
    private final float bossRoomRatio;
    private final float lifePodRoomRatio;
}
