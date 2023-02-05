package com.aldar.roguelike.map;

import com.aldar.roguelike.map.data.GridMetadata;
import com.aldar.roguelike.map.data.RoomGenerationOption;

/**
 * 논리적인 랜덤 맵 생성 전략 인터페이스 입니다.
 * 이 인터페이스를 구현하여 랜덤 맵을 생성합니다.
 * */
public interface RoomGeneratorStrategy<T> {

    T generate(final RoomGenerationOption roomGenerationOption, final GridMetadata gridMetadata);
}
