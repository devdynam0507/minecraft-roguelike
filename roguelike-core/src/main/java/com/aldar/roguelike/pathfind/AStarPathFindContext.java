package com.aldar.roguelike.pathfind;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.aldar.roguelike.pathfind.location.VirtualLocation3D;

public class AStarPathFindContext extends AbstractPathFindContext<PathFindScore, AStarPathFindImpl> {

    public AStarPathFindContext(final AStarPathFindImpl pathFind) {
        super(pathFind);
    }

    public List<PathFindScore> pathfinding(final VirtualLocation3D origin, final VirtualLocation3D dest) {
        final List<PathFindScore> results = new ArrayList<>();
        final Stack<PathFindScore> pathFindScores = new Stack<>();
        final PathFind<PathFindScore> pathFind = super.getPathFind();
        PathFindScore selectMinScore = null;
        int minScoreValue = Integer.MAX_VALUE;
        do {
            List<PathFindScore> findScores;
            if (selectMinScore == null) {
                findScores = pathFind.getScores(origin, dest);
            }
            else {
                findScores = pathFind.getScores(selectMinScore.getLocation(), dest);
            }
            for (final PathFindScore score : findScores) {
                final int f = score.getScore();
                if (minScoreValue > f) {
                    minScoreValue = f;
                    selectMinScore = score;
                }
            }
            results.add(selectMinScore);
            pathFindScores.add(selectMinScore);
        } while (selectMinScore == null || !(selectMinScore.getLocation().distance(dest) <= 0.0));

//        while (!pathFindScores.empty()) {
//            final PathFindScore score1 = pathFindScores.pop();
//        }
        return results;
    }
}