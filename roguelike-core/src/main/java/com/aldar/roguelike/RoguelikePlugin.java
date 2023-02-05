package com.aldar.roguelike;

import org.bukkit.plugin.java.JavaPlugin;

import com.aldar.roguelike.world.VoidWorldGenerator;

public class RoguelikePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        VoidWorldGenerator.generateEmptyWorld("roguelike_world", this);
    }
}