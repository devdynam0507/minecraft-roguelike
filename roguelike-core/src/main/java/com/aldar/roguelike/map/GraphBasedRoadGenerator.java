package com.aldar.roguelike.map;

import java.util.List;
import java.util.Map;

import com.aldar.roguelike.map.data.GridMetadata;
import com.aldar.roguelike.map.data.RoomMetadata;
import com.aldar.roguelike.map.type.RoomType;
import com.aldar.roguelike.pathfind.AStarPathFindImpl;
import com.aldar.roguelike.pathfind.AbstractPathFindContext;
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

    public void connectRoadRecursive(
            final VirtualGrid virtualGrid,
            final Map<Integer, List<RoomMetadata>> graph,
            final RoomMetadata room) {
        final List<RoomMetadata> rooms = graph.get(room.getIndex());
        for (final RoomMetadata _room : rooms) {
            final List<PathFindScore> path = pathFindContext.pathfinding(
                    VirtualLocation3D.of(room.getX(), room.getY(), room.getZ()),
                    VirtualLocation3D.of(_room.getX(), _room.getY(), _room.getZ()));
            for (final PathFindScore score : path) {
                virtualGrid.setItem(
                        score.getLocation().getX(), score.getLocation().getZ(), RoomType.ROAD);
            }
            connectRoadRecursive(virtualGrid, graph, _room);
        }
    }
}