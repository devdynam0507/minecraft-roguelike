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

    ROAD_3WAY_SOUTH_EAST_WEST, // ┷
    ROAD_3WAY_EAST_NORTH_SOUTH, // ┫
    ROAD_3WAY_WEST_NORTH_SOUTH, // ┣
    ROAD_3WAY_NORTH_EAST_WEST, // ┳
    NONE
}
