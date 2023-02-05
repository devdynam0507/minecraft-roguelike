package com.aldar.roguelike.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Pair<L, R> {

    private final L left;
    private final R right;

    public static <L, R> Pair<L, R> of(final L left, final R right) {
        return new Pair<>(left, right);
    }
}
