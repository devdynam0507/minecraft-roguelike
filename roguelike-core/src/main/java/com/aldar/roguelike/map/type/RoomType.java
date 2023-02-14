package com.aldar.roguelike.map.type;

public enum RoomType {

    ROOM,
    ROAD,
    ROAD_NORTH_SOUTH, // ㅣ
    ROAD_EAST_WEST, // ⎯
    ROAD_CORNER_EAST_SOUTH, // ㄱ
    ROAD_CORNER_SOUTH_EAST, // ㄴ
    ROAD_CORNER_SOUTH_WEST, // ┚
    ROAD_CORNER_WEST_SOUTH, // ┍
    NONE
}
