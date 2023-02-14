package com.aldar.roguelike.map;

import java.util.TreeSet;

import com.aldar.roguelike.map.data.GridMetadata;
import com.aldar.roguelike.map.data.RoomGenerationOption;
import com.aldar.roguelike.map.data.RoomMetadata;
import com.aldar.roguelike.map.type.RoomType;
import com.aldar.roguelike.pathfind.AStarPathFindContext;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TreeBasedRoomGeneratorContext {

    private final RoomGeneratorStrategy roomGeneratorStrategy;
    private final AStarPathFindContext pathFindContext;

    public void generate(final RoomGenerationOption roomGenerationOption, final GridMetadata gridMetadata) {
        // 랜덤 맵 생성
        final TreeSet<RoomMetadata> generatedRooms =
                (TreeSet<RoomMetadata>) roomGeneratorStrategy.generate(roomGenerationOption, gridMetadata);
        // 가상 그리드 생성
        final VirtualGrid virtualGrid = new VirtualGrid(gridMetadata);
        for (final RoomMetadata room : generatedRooms) {
            // 가상 그리드에 배치한다.
            virtualGrid.setItem(room.getX(), room.getZ(), RoomType.ROOM);
            // 실제 마인크래프트에서 렌더 시 기준 시작점에서 x, z 좌표가 얼마나 떨어져 있는지
            room.setMinecraftX((room.getX() + 1) * room.getRoomWidth());
            room.setMinecraftZ((room.getZ() + 1) * room.getRoomWidth());
        }
        RoomMetadata node = generatedRooms.first();
        RoomMetadata high;
        RoomMetadata lower;
        do {
            high = generatedRooms.higher(node);
            lower = generatedRooms.lower(node);

        } while(high != null || lower != null);
    }
}
