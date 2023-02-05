package com.aldar.roguelike.map;

import java.util.Collection;
import java.util.TreeSet;

import com.aldar.roguelike.map.data.GridMetadata;
import com.aldar.roguelike.map.data.RoomGenerationOption;
import com.aldar.roguelike.map.data.RoomMetadata;

public class TreeBasedRoomGenerator implements RoomGeneratorStrategy<TreeSet<RoomMetadata>> {

    private static final int FIXED_Y = 50;

    // TODO: 1. n x m 격자에서 x 만큼의 맵을 랜덤 배치 한다. O
    // TODO: 2. 모든 방은 트리로 연결한다. O
    // TODO: 3. 방 입구의 면을 결정한다.
    // TODO: 4. 트리를 순회하면 방 입구 면 끼리 길을 연결하는데 이 떄 A* 알고리즘을 이용하여 연결한다.
    // TODO: 4-1. 이 때 대각선 이동은 불가능하다.

    @Override
    public TreeSet<RoomMetadata> generate(
            final RoomGenerationOption roomGenerationOption, final GridMetadata gridMetadata) {
        final TreeSet<RoomMetadata> tree = new TreeSet<>();
        // 1. 스타트 포인트를 지정한다.
        final int startX = gridMetadata.getWidth() / 2;
        final int startY = 0;
        tree.add(RoomMetadata.of(startX, FIXED_Y, startY, 0, 16, 16));

        // 재귀 함수로 랜덤 맵 배치
        generateRandomRoomsRecursive(
                tree,
                startY,
                gridMetadata,
                1,
                roomGenerationOption.getRoomAmount() - 1
        );
        return tree;
    }

    private void generateRandomRoomsRecursive(
            final TreeSet<RoomMetadata> tree,
            final int y,
            final GridMetadata gridMetadata,
            final int phase,
            final int roomAmount) {
        if (tree.size() >= roomAmount) {
            return;
        }
        int newY = y + 2;
        final int HEIGHT = gridMetadata.getHeight();
        // y 의 범위는 0 ~ 2 사이로 띄운다.
        // top - down 방식으로 진행된다.
        // grid 높이 범위를 벗어나는 경우
        if (newY >= HEIGHT) {
            newY = y;
        }
        final int newX = (int) (Math.random() * gridMetadata.getWidth());
        tree.add(RoomMetadata.of(newX, FIXED_Y, newY, phase, 16, 16));
        generateRandomRoomsRecursive(tree, newY, gridMetadata, phase + 1, roomAmount);
    }
}