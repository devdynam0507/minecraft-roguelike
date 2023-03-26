package com.aldar.roguelike.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

import org.bukkit.Location;

import com.aldar.roguelike.renderer.schematics.Schematic;
import com.fastasyncworldedit.core.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;

public class WorldEditUtils {

    public static void pasteSchematicAsync(final Schematic schematic, final Location location) {
        final EditSession editSession =
                pasteSchematicAsync(location, schematic.getFile(), schematic.getRotate());
        BlockVector3 minimumPoint = Objects.requireNonNull(editSession).getMinimumPoint();
        int minX = minimumPoint.getBlockX();
        int minZ = minimumPoint.getBlockZ();
        BlockVector3 maximumPoint = editSession.getMaximumPoint();
        int maxX = maximumPoint.getBlockX();
        int maxZ = maximumPoint.getBlockZ();
        schematic.setEditSession(editSession);
        schematic.setCenter(new Location(
            location.getWorld(), minX + ((maxX - minZ) / 2.0), 0, minZ + ((maxZ - minZ) / 2.0)
        ));
    }

    public static EditSession pasteSchematicAsync(final Location location, final File schematicFile, final int rotate) {
        final BlockVector3 blockVector3 =
                BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        try(Clipboard clipboard = FaweAPI.load(schematicFile)) {
            final AffineTransform transform = new AffineTransform()
                    .translate(blockVector3)
                    .rotateY(rotate);
            final World world = FaweAPI.getWorld(location.getWorld().getName());
            return clipboard.paste(world, blockVector3, true, false, transform);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void pasteSchematic(final Location location, final File schematicFile) {
        if (location.getWorld() == null) {
            throw new IllegalArgumentException("Location::world cannot be null");
        }
        final ClipboardFormat clipboardFormat = ClipboardFormats.findByFile(schematicFile);
        if (clipboardFormat == null) {
            throw new IllegalArgumentException("Schematic file cannot be null");
        }
        try (final ClipboardReader clipboardReader =
                     clipboardFormat.getReader(new FileInputStream(schematicFile))){
            final BlockVector3 blockVector3 =
                    BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ());
            final World worldEditWorld = BukkitAdapter.adapt(location.getWorld());
            final EditSession editSession = WorldEdit.getInstance()
                                                     .newEditSessionBuilder()
                                                     .world(worldEditWorld)
                                                     .build();
            final Clipboard clipboard = clipboardReader.read();
            final Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(blockVector3)
                    .ignoreAirBlocks(true)
                    .build();
            Operations.complete(operation);
            editSession.close();
        } catch (IOException | WorldEditException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
}
