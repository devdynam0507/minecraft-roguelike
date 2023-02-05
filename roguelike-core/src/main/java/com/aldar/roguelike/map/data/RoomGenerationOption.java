package com.aldar.roguelike.map.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RoomGenerationOption {

    private final int roomAmount;
    private final float battleRoomRatio;
    private final float boxRoomRatio;
    private final float bossRoomRatio;
    private final float lifePodRoomRatio;
}
