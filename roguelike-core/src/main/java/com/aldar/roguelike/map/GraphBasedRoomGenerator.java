package com.aldar.roguelike.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aldar.roguelike.map.data.GridMetadata;
import com.aldar.roguelike.map.data.RoomGenerationOption;
import com.aldar.roguelike.map.data.RoomMetadata;
import com.aldar.roguelike.utils.Pair;

public class GraphBasedRoomGenerator implements RoomGeneratorStrategy<Graph> {

    private static final int FIXED_Y = 50;

    // TODO: 1. n x m 격자에서 x 만큼의 맵을 랜덤 배치 한다. O
    // TODO: 2. 모든 방은 그래프로 연결한다. O
    // TODO: 3. 방 입구의 면을 결정한다.
    // TODO: 4. 트리를 순회하면 방 입구 면 끼리 길을 연결하는데 이 떄 A* 알고리즘을 이용하여 연결한다. O
    // TODO: 4-1. 이 때 대각선 이동은 불가능하다. O

    @Override
    public Graph generate(
            final RoomGenerationOption roomGenerationOption, final GridMetadata gridMetadata) {
        final List<RoomMetadata> rooms = new ArrayList<>();
        final Map<Integer, List<RoomMetadata>> graph = new HashMap<>();
        final int startX = gridMetadata.getWidth() / 2;
        final int startY = 0;
        graph.put(0, new ArrayList<>());
        rooms.add(RoomMetadata.of(startX, FIXED_Y, startY, 0, 16, 16));
        int y = startY;
        for (int i = 1; i <= roomGenerationOption.getRoomAmount() / 2; i++) {
            if (!graph.containsKey(i)) {
                graph.put(i, new ArrayList<>());
            }
            int newY = y + 2;
            final int HEIGHT = gridMetadata.getHeight();
            // y 의 범위는 0 ~ 2 사이로 띄운다.
            // top - down 방식으로 진행된다.
            // grid 높이 범위를 벗어나는 경우
            if (newY >= HEIGHT) {
                newY = y;
            }
            y = newY;
            int newX = -1;
            final List<RoomMetadata> previousGraph = graph.get(i - 1);
            for (int j = 0; j < 2; j++) {
                int _newX;
                do {
                    _newX = (int) (Math.random() * gridMetadata.getWidth());
                } while (newX == _newX);
                newX = _newX;
                final RoomMetadata newRoom =
                        RoomMetadata.of(newX, FIXED_Y, newY, i, 16, 16);
                previousGraph.add(newRoom);
                rooms.add(newRoom);
            }
        }
        return Graph.to(Pair.of(rooms, graph));
    }
}