package com.aldar.roguelike.pool;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RoguelikeMapQueueTest {

    final RoguelikeMapQueue queue = new RoguelikeMapQueue(15, 30, 20);

    @Test
    void poolThreadSafeTest() throws InterruptedException {
        final List<RoguelikeMapArea> areas = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            new Thread(() -> areas.add(queue.get())).start();
        }
        Thread.sleep(1500);
        areas.sort(Comparator.comparingInt(RoguelikeMapArea::getIndex));
        for (int i = 1; i <= 10; i++) {
            Assertions.assertEquals(areas.get(i - 1).getIndex(), i);
        }
    }
}