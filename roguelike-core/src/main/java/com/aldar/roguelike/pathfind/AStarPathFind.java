package com.aldar.roguelike.pathfind;

import org.bukkit.Location;

public interface AStarPathFind extends PathFind<PathFindScore> {

    /**
     * A* 알고리즘 F = G + H 공식 중 H를 구하는 함수이다.
     * 장애물을 모두 무시하고 출발지, 도착지 간 거리를 구하는 함수 (맨하탄 방법)
     * */
    int h(final Location origin, final Location dest);
}