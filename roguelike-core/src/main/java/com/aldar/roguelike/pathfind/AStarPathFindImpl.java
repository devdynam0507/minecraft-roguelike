package com.aldar.roguelike.pathfind;

import java.util.Arrays;
import java.util.List;

import com.aldar.roguelike.pathfind.location.VirtualLocation3D;

public class AStarPathFindImpl implements AStarPathFind {

    private final static int DIAGONAL_SCORE = 14;
    private final static int DEFAULT_SCORE = 10;

    @Override
    public int h(final VirtualLocation3D origin, final VirtualLocation3D dest) {
        return (int) origin.distance(dest) * 10;
    }

    @Override
    public List<PathFindScore> getScores(final VirtualLocation3D point, final VirtualLocation3D dest) {
        final VirtualLocation3D south = point.clone().add(0, 0, 1); // south
        final VirtualLocation3D southEast = point.clone().add(1, 0, 1); // south-east or east-south, it means diagonals
        final VirtualLocation3D southWest = point.clone().add(-1, 0, 1); // south-west or west-south, it means diagonals
        final VirtualLocation3D west = point.clone().add(-1, 0, 0);
        final VirtualLocation3D northWest = point.clone().add(-1, 0, -1); // north-west or west north
        final VirtualLocation3D north = point.clone().add(0, 0, -1);
        final VirtualLocation3D northEast = point.clone().add(1, 0, -1);
        final VirtualLocation3D east = point.clone().add(1, 0, 0);
        return Arrays.asList(
            PathFindScore.of(DEFAULT_SCORE, h(south, dest), south, Directions.SOUTH),
//            PathFindScore.of(DIAGONAL_SCORE, h(southEast, dest), southEast, Directions.SOUTH_EAST),
//            PathFindScore.of(DIAGONAL_SCORE, h(southWest, dest), southWest, Directions.SOUTH_WEST),
            PathFindScore.of(DEFAULT_SCORE, h(west, dest), west, Directions.WEST),
//            PathFindScore.of(DIAGONAL_SCORE, h(northWest, dest), northWest, Directions.NORTH_WEST),
            PathFindScore.of(DEFAULT_SCORE, h(north, dest), north, Directions.NORTH),
//            PathFindScore.of(DIAGONAL_SCORE, h(northEast, dest), northEast, Directions.NORTH_EAST),
            PathFindScore.of(DEFAULT_SCORE, h(east, dest), east, Directions.EAST));
    }
}
