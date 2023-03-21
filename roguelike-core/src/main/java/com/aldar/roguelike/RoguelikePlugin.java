package com.aldar.roguelike;

import java.io.File;
import java.util.Objects;

import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.aldar.roguelike.command.RoguelikeCoreCommand;
import com.aldar.roguelike.world.VoidWorldGenerator;

public class RoguelikePlugin extends JavaPlugin {

    private static Plugin plugin;
    private static World roguelikeWorld;
    private static File resourceDirectory;
    public static File[] schematics;

    @Override
    public void onEnable() {
        plugin = this;
        roguelikeWorld = VoidWorldGenerator.generateEmptyWorld("roguelike_world", this);
        Objects.requireNonNull(getCommand("rog")).setExecutor(new RoguelikeCoreCommand());
        initializeResource();
    }

    private void initializeResource() {
        final String parent = getFile().getParent();
        resourceDirectory = new File(parent + "/roguelike-core");
        if (!resourceDirectory.exists()) {
            resourceDirectory.mkdir();
        }
        schematics = resourceDirectory.listFiles();
        System.out.println(schematics[0].getPath());
    }

    public static World getRoguelikeWorld() {
        return roguelikeWorld;
    }

    public static Plugin getPlugin() {
        return plugin;
    }
}