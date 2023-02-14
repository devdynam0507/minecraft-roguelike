package com.aldar.roguelike.map;

import java.util.List;
import java.util.Map;

import com.aldar.roguelike.map.data.GridMetadata;
import com.aldar.roguelike.map.data.RoomMetadata;
import com.aldar.roguelike.map.type.RoomType;
import com.aldar.roguelike.pathfind.AStarPathFindImpl;
import com.aldar.roguelike.pathfind.AbstractPathFindContext;
import com.aldar.roguelike.pathfind.Directions;
import com.aldar.roguelike.pathfind.Path;
import com.aldar.roguelike.pathfind.PathFindPostProcessor;
import com.aldar.roguelike.pathfind.PathFindScore;
import com.aldar.roguelike.pathfind.location.VirtualLocation3D;
import com.aldar.roguelike.utils.Pair;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GraphBasedRoadGenerator implements RoadGeneratorStrategy<
        Pair<List<RoomMetadata>, Map<Integer, List<RoomMetadata>>>> {

    // TODO: 1. 연결할 두 방을 구한다.
    // TODO: 2. 두 방의 경로를 구하고 Virtual Grid 에 세팅한다.
    // TODO: 2-1. 꺾이는 길까지 구분하여 Virtual Grid 에 세팅한다.
    private final GridMetadata gridMetadata;
    private final AbstractPathFindContext<PathFindScore, AStarPathFindImpl> pathFindContext;
    private final PathFindPostProcessor pathFindPostProcessor;

    @Override
    public VirtualGrid generate(
            final Pair<List<RoomMetadata>, Map<Integer, List<RoomMetadata>>> generatedRoomGraphPair) {
        final VirtualGrid virtualGrid = new VirtualGrid(gridMetadata);
        return generate(virtualGrid, generatedRoomGraphPair);
    }

    @Override
    public VirtualGrid generate(final VirtualGrid virtualGrid,
                                final Pair<List<RoomMetadata>, Map<Integer, List<RoomMetadata>>> generatedRooms) {
        final List<RoomMetadata> rooms = generatedRooms.getLeft();
        final Map<Integer, List<RoomMetadata>> graph = generatedRooms.getRight();
        connectRoadRecursive(virtualGrid, graph, rooms.get(0));
        return virtualGrid;
    }

    private RoomType pickCornerRoadTypeRandom(final Directions directions) {
        final int random = (int) (Math.random() * 1);
        if (directions == Directions.SOUTH_EAST) {
            final RoomType[] southEastDirections =
                    new RoomType[] { RoomType.ROAD_CORNER_EAST_SOUTH, RoomType.ROAD_CORNER_SOUTH_EAST };
            return southEastDirections[random];
        }
        else {
            final RoomType[] southWestDirections =
                    new RoomType[] { RoomType.ROAD_CORNER_SOUTH_WEST, RoomType.ROAD_CORNER_WEST_SOUTH };
            return southWestDirections[random];
        }
    }

    private boolean isNorthSouth(final Directions directions) {
        return directions == Directions.NORTH || directions == Directions.SOUTH;
    }

    private boolean isEastWest(final Directions directions) {
        return directions == Directions.EAST || directions == Directions.WEST;
    }

    private RoomType getRoadType(final List<PathFindScore> pathFindScores, final int currentIndex) {
        if (currentIndex + 1 >= pathFindScores.size()) {
            return null;
        }
        final Directions currentDirections = pathFindScores.get(currentIndex).getDirections();
        final Directions nextDirections = pathFindScores.get(currentIndex + 1).getDirections();
        if (currentDirections == nextDirections) {
            if (isNorthSouth(currentDirections)) {
                return RoomType.ROAD_NORTH_SOUTH;
            }
            return RoomType.ROAD_EAST_WEST;
        }
        if (isEastWest(currentDirections) && isNorthSouth(nextDirections)) {
            if (currentDirections == Directions.WEST) {
                return RoomType.ROAD_CORNER_WEST_SOUTH;
            }
            return RoomType.ROAD_CORNER_EAST_SOUTH;
        }
        else if (isNorthSouth(currentDirections) && isEastWest(nextDirections)) {
            if (nextDirections == Directions.EAST) {
                return RoomType.ROAD_CORNER_SOUTH_EAST;
            }
            else {
                return RoomType.ROAD_CORNER_SOUTH_WEST;
            }
        }
        return null;
    }

    public void connectRoadRecursive(
            final VirtualGrid virtualGrid,
            final Map<Integer, List<RoomMetadata>> graph,
            final RoomMetadata room) {
        final List<RoomMetadata> rooms = graph.get(room.getIndex());
        for (final RoomMetadata _room : rooms) {
            final List<PathFindScore> path = pathFindContext.pathfinding(
                    VirtualLocation3D.of(room.getX(), room.getY(), room.getZ()),
                    VirtualLocation3D.of(_room.getX(), _room.getY(), _room.getZ()));
            final List<Path> paths = pathFindPostProcessor.postProcess(path);
            for (final Path _path : paths) {
                virtualGrid.setItem(_path.getLocation().getX(), _path.getLocation().getZ(), _path.getRoomType());
            }
            connectRoadRecursive(virtualGrid, graph, _room);
        }
    }
}