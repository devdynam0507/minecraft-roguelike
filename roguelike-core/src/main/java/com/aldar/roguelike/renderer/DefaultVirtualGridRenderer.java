package com.aldar.roguelike.renderer;

import java.io.File;
import static java.util.Objects.requireNonNull;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.aldar.roguelike.RoguelikePlugin;
import com.aldar.roguelike.map.VirtualGrid;
import com.aldar.roguelike.map.data.GridMetadata;
import com.aldar.roguelike.map.type.RoomType;
import com.aldar.roguelike.pathfind.location.VirtualLocation3D;
import com.aldar.roguelike.pool.RoguelikeMapArea;
import com.aldar.roguelike.pool.RoguelikeMapQueue;
import com.aldar.roguelike.renderer.schematics.Schematic;
import com.aldar.roguelike.renderer.schematics.SchematicStrategy;
import com.aldar.roguelike.utils.Pair;
import com.aldar.roguelike.utils.WorldEditUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultVirtualGridRenderer implements VirtualGridRenderer {

    private final RoguelikeMapQueue mapQueue;
    private final SchematicStrategy schematicStrategy;

    @Override
    public Pair<RoguelikeMapArea, VirtualGrid> render(
            final GridMetadata gridMetadata, final VirtualGrid virtualGrid, final RoguelikeMapArea area, Player player) {
        requireNonNull(area, "RoguelikeMapArea cannot be null");
        if (gridMetadata == null || virtualGrid == null) {
            mapQueue.returnArea(area);
            throw new NullPointerException("GridMetadata or VirtualGrid cannot be null");
        }
        final int width = virtualGrid.width();
        final int height = virtualGrid.height();
        if (width > area.getWidth()) {
            throw new IllegalArgumentException("VirtualGrid width cannot bigger then area width");
        }
        if (height > area.getHeight()) {
            throw new IllegalArgumentException("VirtualGrid width cannot bigger then area width");
        }
        final VirtualLocation3D start = area.getStart();
        final VirtualLocation3D end = area.getEnd();
        final World roguelikeWorld = RoguelikePlugin.getRoguelikeWorld();
        final int gridWidth = gridMetadata.getGridBlockWidth();
        final int gridHeight = gridMetadata.getGridBlockHeight();
        Location startPoint = null;
        for (int z = 0; z < height; z++) {
            final int _z = end.getZ() + ((z + 1) * gridHeight / 2);
            for (int x = 0; x < width; x++) {
                final RoomType item = virtualGrid.getItem(x, z);
                if (item == null || item == RoomType.NONE) {
                    continue;
                }
                final int _x = start.getX() + ((x + 1) * gridWidth / 2);
                final Location location = new Location(roguelikeWorld, _x, 40, _z);
                if (startPoint == null) {
                    startPoint = location;
                }

                // 방 타입에 따른 스케메틱 정보 가져오기
                final Schematic schematic = schematicStrategy.getSchematic(item);
                if (schematic == null) {
                    System.out.println("Continued. " + item);
                    continue;
                }
                // 비동기로 스케메틱 렌더링
                // TODO: 방 문 뚫기
                Bukkit.getScheduler().runTaskLaterAsynchronously(
                    RoguelikePlugin.getPlugin(),
                    () -> {
                        WorldEditUtils.pasteSchematicAsync(schematic, location);
                    },
                    2000L
                );
            }
        }
        player.teleport(requireNonNull(startPoint));

        return Pair.of(area, virtualGrid);
    }
}
