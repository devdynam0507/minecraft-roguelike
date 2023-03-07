package com.aldar.roguelike;

import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import com.aldar.roguelike.world.VoidWorldGenerator;

public class RoguelikePlugin extends JavaPlugin {

    private World roguelikeWorld;

    @Override
    public void onEnable() {
        this.roguelikeWorld = VoidWorldGenerator.generateEmptyWorld("roguelike_world", this);
    }
}