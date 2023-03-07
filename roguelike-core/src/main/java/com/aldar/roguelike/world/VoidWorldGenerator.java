package com.aldar.roguelike.world;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.plugin.Plugin;

public class VoidWorldGenerator {

    public static World generateEmptyWorld(final String worldName, final Plugin plugin) {
       final WorldCreator worldCreator = new WorldCreator(worldName);
       worldCreator.type(WorldType.FLAT);
       worldCreator.generateStructures(false);
       final World createdWorld = worldCreator.createWorld();
       if (createdWorld != null) {
           plugin.getServer().getWorlds().add(createdWorld);
       }
       return createdWorld;
    }
}
