package com.aldar.roguelike.pathfind;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import com.aldar.roguelike.map.type.RoomType;

import lombok.RequiredArgsConstructor;

/**
 * 길 찾기 이후 후처리 클래스
 * */
@RequiredArgsConstructor
public class PathFindPostProcessor {

    public List<Path> postProcess(final List<PathFindScore> pathFindScores) {
        final List<Path> mapToPaths = pathFindScores.stream()
                                                    .map(Path::of)
                                                    .toList();
        int p1 = 0, p2 = 1;
        for (int i = 0 ; i < mapToPaths.size() - 1; i++) {
            final Path path1 = mapToPaths.get(p1);
            final Path path2 = mapToPaths.get(p2);
            if (path1.getOriginalDirection() != path2.getOriginalDirection()) {
                RoomType roomType;
                if (path1.getOriginalDirection() == Directions.SOUTH &&
                    path2.getOriginalDirection() == Directions.NORTH) {
                    roomType = RoomType.ROAD_NORTH_SOUTH;
                }
                else {
                    roomType = RoomType.valueOf("ROAD_CORNER_" +
                                                path1.getOriginalDirection().name() +
                                                "_" +
                                                path2.getOriginalDirection().name());
                }
                path1.setRoomType(roomType);
                if (p2 == mapToPaths.size() - 1) {
                    path2.setRoomType(getRoomType(path2));
                }
            }
            else {
                path1.setRoomType(getRoomType(path1));
                path2.setRoomType(getRoomType(path2));
            }
            p1++; p2++;
        }
        return mapToPaths;
    }

    private RoomType getRoomType(final Path path) {
        final Directions direction = path.getOriginalDirection();
        if (direction == Directions.NORTH || direction == Directions.SOUTH) {
            return RoomType.ROAD_NORTH_SOUTH;
        }
        else {
            return RoomType.ROAD_EAST_WEST;
        }
    }
}
