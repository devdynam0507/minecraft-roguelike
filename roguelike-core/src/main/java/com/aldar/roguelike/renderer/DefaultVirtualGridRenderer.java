package com.aldar.roguelike.renderer;

import java.io.File;
import static java.util.Objects.requireNonNull;

import org.bukkit.Location;

import com.aldar.roguelike.RoguelikePlugin;
import com.aldar.roguelike.map.VirtualGrid;
import com.aldar.roguelike.map.data.GridMetadata;
import com.aldar.roguelike.map.type.RoomType;
import com.aldar.roguelike.pathfind.location.VirtualLocation3D;
import com.aldar.roguelike.pool.RoguelikeMapArea;
import com.aldar.roguelike.pool.RoguelikeMapQueue;
import com.aldar.roguelike.utils.Pair;
import com.aldar.roguelike.utils.WorldEditUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultVirtualGridRenderer implements VirtualGridRenderer {

    private final RoguelikeMapQueue mapQueue;

    @Override
    public Pair<RoguelikeMapArea, VirtualGrid> render(
            final GridMetadata gridMetadata, final VirtualGrid virtualGrid, final RoguelikeMapArea area) {
        requireNonNull(area, "RoguelikeMapArea cannot be null");
        if (gridMetadata == null || virtualGrid == null) {
            mapQueue.returnArea(area);
            throw new NullPointerException("GridMetadata or VirtualGrid cannot be null");
        }
        final int width = virtualGrid.width();
        final int height = virtualGrid.height();
        if (width > area.getWidth()) {
            throw new IllegalArgumentException("VirtualGrid width isn't bigger then area width");
        }
        if (height > area.getHeight()) {
            throw new IllegalArgumentException("VirtualGrid width cannot bigger then area width");
        }
        final VirtualLocation3D start = area.getStart();
        final VirtualLocation3D end = area.getEnd();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                final RoomType item = virtualGrid.getItem(x, y);
                if (item == null || item == RoomType.NONE) {
                    continue;
                }
                final int _x = start.getX() + x;
                final int _z = end.getZ() + y;
                // 실제 마인크래프트 좌표로 정규화 합니다.
                final VirtualLocation3D normalize =
                        VirtualLocation3D.of(_x, start.getY(), _z).normalize(gridMetadata.getGridBlockWidth());
                // TODO: RoomType에 따른 스케메틱을 로드해야합니다.
                final File schematicFile = new File("todo.file.schematic");
                final Location location = new Location(RoguelikePlugin.getRoguelikeWorld(), normalize.getX(),
                                                 normalize.getY(), normalize.getZ());
                WorldEditUtils.pasteSchematic(location, schematicFile);
            }
        }
        return Pair.of(area, virtualGrid);
    }
}
