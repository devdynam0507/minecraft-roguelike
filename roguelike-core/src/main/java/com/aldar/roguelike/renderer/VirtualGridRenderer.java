package com.aldar.roguelike.renderer;

import org.bukkit.entity.Player;

import com.aldar.roguelike.map.VirtualGrid;
import com.aldar.roguelike.map.data.GridMetadata;
import com.aldar.roguelike.pool.RoguelikeMapArea;
import com.aldar.roguelike.utils.Pair;

public interface VirtualGridRenderer {

    Pair<RoguelikeMapArea, VirtualGrid> render(
            final GridMetadata gridMetadata, final VirtualGrid virtualGrid, final RoguelikeMapArea area, Player player);
}
