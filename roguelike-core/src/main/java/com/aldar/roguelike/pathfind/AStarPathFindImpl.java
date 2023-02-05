package com.aldar.roguelike.pathfind;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;

public class AStarPathFindImpl implements AStarPathFind {

    private final static int DIAGONAL_SCORE = 14;
    private final static int DEFAULT_SCORE = 10;

    @Override
    public int h(final Location origin, final Location dest) {
        return (int) origin.distance(dest) * 10;
    }

    @Override
    public List<PathFindScore> getScores(final Location point, final Location dest) {
        final Location south = point.clone().add(0, 0, 1); // south
        final Location southEast = point.clone().add(1, 0, 1); // south-east or east-south, it means diagonals
        final Location southWest = point.clone().add(-1, 0, 1); // south-west or west-south, it means diagonals
        final Location west = point.clone().add(-1, 0, 0);
        final Location northWest = point.clone().add(-1, 0, -1); // north-west or west north
        final Location north = point.clone().add(0, 0, -1);
        final Location northEast = point.clone().add(1, 0, -1);
        final Location east = point.clone().add(1, 0, 0);
        return Arrays.asList(
            PathFindScore.of(DEFAULT_SCORE, h(south, dest), south, Directions.SOUTH),
            PathFindScore.of(DIAGONAL_SCORE, h(southEast, dest), southEast, Directions.SOUTH_EAST),
            PathFindScore.of(DIAGONAL_SCORE, h(southWest, dest), southWest, Directions.SOUTH_WEST),
            PathFindScore.of(DEFAULT_SCORE, h(west, dest), west, Directions.WEST),
            PathFindScore.of(DIAGONAL_SCORE, h(northWest, dest), northWest, Directions.NORTH_WEST),
            PathFindScore.of(DEFAULT_SCORE, h(north, dest), north, Directions.NORTH),
            PathFindScore.of(DIAGONAL_SCORE, h(northEast, dest), northEast, Directions.NORTH_EAST),
            PathFindScore.of(DEFAULT_SCORE, h(east, dest), east, Directions.EAST));
    }
}
