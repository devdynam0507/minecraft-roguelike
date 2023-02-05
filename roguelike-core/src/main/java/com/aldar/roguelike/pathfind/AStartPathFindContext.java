package com.aldar.roguelike.pathfind;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AStartPathFindContext {

    private final PathFind<PathFindScore> pathFind;

    public List<PathFindScore> pathfinding(final Location origin, final Location dest) {
        final List<PathFindScore> results = new ArrayList<>();
        PathFindScore selectMinScore = null;
        int minScoreValue = Integer.MAX_VALUE;
        do {
            final List<PathFindScore> findScores = pathFind.getScores(origin, dest);
            for (final PathFindScore score : findScores) {
                final int f = score.getScore();
                if (minScoreValue > f) {
                    minScoreValue = f;
                    selectMinScore = score;
                }
            }
            results.add(selectMinScore);
        } while (selectMinScore == null || !(selectMinScore.getLocation().distance(dest) <= 1.0));

        return results;
    }

    private boolean isDiagonals(final Directions directions) {
        return directions == Directions.NORTH_EAST ||
               directions == Directions.NORTH_WEST ||
               directions == Directions.SOUTH_EAST ||
               directions == Directions.SOUTH_WEST;
    }
}