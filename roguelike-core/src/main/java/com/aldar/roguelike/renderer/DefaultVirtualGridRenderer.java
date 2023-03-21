package com.aldar.roguelike.renderer;

import java.io.File;
import static java.util.Objects.requireNonNull;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
            throw new IllegalArgumentException("VirtualGrid width isn't bigger then area width");
        }
        if (height > area.getHeight()) {
            throw new IllegalArgumentException("VirtualGrid width cannot bigger then area width");
        }
        final VirtualLocation3D start = area.getStart();
        final VirtualLocation3D end = area.getEnd();
        Location startpoint = null;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                final RoomType item = virtualGrid.getItem(x, y);
                if (item == null || item == RoomType.NONE) {
                    continue;
                }
                final int _x = start.getX() + (x * gridMetadata.getGridBlockWidth());
                final int _z = end.getZ() + (y * gridMetadata.getGridBlockHeight());
                // 실제 마인크래프트 좌표로 정규화 합니다.
                final Location normalize =
                        VirtualLocation3D.of(_x, start.getY(), _z)
                                         .toMinecraftLocationWithNormalize(RoguelikePlugin.getRoguelikeWorld(),
                                                                           gridMetadata.getGridBlockWidth());
                final Location location = new Location(RoguelikePlugin.getRoguelikeWorld(), normalize.getX(),
                                                       80, normalize.getZ());
                if (startpoint == null) {
                    startpoint = location;
                }
                // 방 타입에 따른 스케메틱 정보 가져오기
                final Schematic schematic = schematicStrategy.getSchematic(item);
                // 비동기로 스케메틱 렌더링
                Bukkit.getScheduler().runTaskAsynchronously(
                    RoguelikePlugin.getPlugin(),
                    () -> WorldEditUtils.pasteSchematicAsync(schematic, location));
            }
        }
        player.teleport(requireNonNull(startpoint));

        return Pair.of(area, virtualGrid);
    }
}
