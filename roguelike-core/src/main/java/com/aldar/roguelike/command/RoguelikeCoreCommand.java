package com.aldar.roguelike.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import com.aldar.roguelike.RoguelikePlugin;
import com.aldar.roguelike.map.Graph;
import com.aldar.roguelike.map.GraphBasedRoadGenerator;
import com.aldar.roguelike.map.GraphBasedRoomGenerator;
import com.aldar.roguelike.map.RoadGeneratorStrategy;
import com.aldar.roguelike.map.RoomGeneratorStrategy;
import com.aldar.roguelike.map.VirtualGrid;
import com.aldar.roguelike.map.data.GridMetadata;
import com.aldar.roguelike.map.data.RoomGenerationOption;
import com.aldar.roguelike.map.data.RoomMetadata;
import com.aldar.roguelike.map.type.RoomType;
import com.aldar.roguelike.pathfind.AStarPathFindContext;
import com.aldar.roguelike.pathfind.AStarPathFindImpl;
import com.aldar.roguelike.pathfind.PathFindPostProcessor;
import com.aldar.roguelike.pool.RoguelikeMapQueue;
import com.aldar.roguelike.renderer.DefaultVirtualGridRenderer;
import com.aldar.roguelike.renderer.VirtualGridRenderer;
import com.aldar.roguelike.renderer.schematics.DefaultSchematicStrategy;

public class RoguelikeCoreCommand implements CommandExecutor {

    final GridMetadata gridMetadata = GridMetadata.of(16, 16, 32, 32);
    final RoomGeneratorStrategy<Graph> roomGenerator =
            new GraphBasedRoomGenerator();
    final AStarPathFindContext pathFindContext = new AStarPathFindContext(new AStarPathFindImpl());
    final PathFindPostProcessor postProcessor = new PathFindPostProcessor();
    RoadGeneratorStrategy<Graph> roadGeneratorStrategy = new GraphBasedRoadGenerator(
            gridMetadata, pathFindContext, postProcessor);

    @Override
    public boolean onCommand(@NotNull final CommandSender sender,
                             @NotNull final Command command,
                             @NotNull final String label,
                             @NotNull final String[] args) {
        final RoomGenerationOption roomGenerationOption =
                new RoomGenerationOption(14,
                                         10,
                                         10,
                                         10,
                                         10);
        final Graph rooms = roomGenerator.generate(roomGenerationOption, gridMetadata);
        final VirtualGrid virtualGrid = new VirtualGrid(gridMetadata);
        for (final RoomMetadata roomMetadata : rooms.getGraphPair().getLeft()) {
            List<RoomMetadata> roomMetadata1 = rooms.getGraphPair().getRight().get(roomMetadata.getIndex());
            for (final RoomMetadata roomMetadata2 : roomMetadata1) {
                virtualGrid.setItem(roomMetadata2.getX(), roomMetadata2.getZ(), RoomType.ROOM);
            }
            virtualGrid.setItem(roomMetadata.getX(), roomMetadata.getZ(), RoomType.ROOM);
        }
        final VirtualGrid roadVG = roadGeneratorStrategy.generate(virtualGrid, rooms);
        final RoguelikeMapQueue queue = new RoguelikeMapQueue(10, 32, 32);
        final VirtualGridRenderer renderer =
                new DefaultVirtualGridRenderer(queue, new DefaultSchematicStrategy(RoguelikePlugin.schematics));
        roadVG.print();
        renderer.render(gridMetadata, roadVG, queue.get(), (Player) sender);
        return false;
    }
}
