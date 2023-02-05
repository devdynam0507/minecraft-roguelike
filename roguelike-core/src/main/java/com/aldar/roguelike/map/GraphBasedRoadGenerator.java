package com.aldar.roguelike.map;

import java.util.List;
import java.util.Map;

import com.aldar.roguelike.map.data.GridMetadata;
import com.aldar.roguelike.map.data.RoomMetadata;
import com.aldar.roguelike.pathfind.PathFind;
import com.aldar.roguelike.pathfind.PathFindScore;
import com.aldar.roguelike.utils.Pair;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GraphBasedRoadGenerator implements RoadGeneratorStrategy<
        Pair<List<RoomMetadata>, Map<Integer, List<RoomMetadata>>>> {

    // TODO: 1. 연결할 두 방을 구한다.
    // TODO: 2. 두 방의 경로를 구하고 Virtual Grid 에 세팅한다.
    // TODO: 2-1. 꺾이는 길까지 구분하여 Viretual Grid 에 세팅한다.
    private final GridMetadata gridMetadata;
    private final PathFind<PathFindScore> pathFind;

    @Override
    public VirtualGrid generate(
            final Pair<List<RoomMetadata>, Map<Integer, List<RoomMetadata>>> generatedRoomGraphPair) {
        final VirtualGrid virtualGrid = new VirtualGrid(gridMetadata);
        final List<RoomMetadata> rooms = generatedRoomGraphPair.getLeft();
        final Map<Integer, List<RoomMetadata>> graph = generatedRoomGraphPair.getRight();
        return null;
    }

    public void connectRoadRecursive(
            final VirtualGrid virtualGrid,
            final Map<Integer, List<RoomMetadata>> graph) {

    }
}
