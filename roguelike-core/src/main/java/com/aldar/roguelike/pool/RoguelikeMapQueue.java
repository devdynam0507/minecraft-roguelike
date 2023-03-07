package com.aldar.roguelike.pool;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.aldar.roguelike.pathfind.location.VirtualLocation3D;

public class RoguelikeMapQueue {

    private final Queue<RoguelikeMapArea> mapQueue = new ConcurrentLinkedQueue<>();
    private final int poolSize;

    public RoguelikeMapQueue(final int initialPoolSize, final int width, final int height) {
        this.poolSize = initialPoolSize;
        initialize(width, height);
    }

    private void initialize(final int width, final int height) {
        int x = 0, z = 0;
        for (int i = 0; i < poolSize; i++) {
            final VirtualLocation3D start = VirtualLocation3D.of(x, 20, z);
            final VirtualLocation3D end = VirtualLocation3D.of(x + width, 20, z + height);
            mapQueue.offer(new RoguelikeMapArea(i + 1, width, height, start, end));
            x += width + 1;
            z += width + 1;
        }
    }

    public RoguelikeMapArea get() {
        return mapQueue.poll();
    }

    public void returnArea(final RoguelikeMapArea area) {
        mapQueue.offer(area);
    }
}
