package net.vadamdev.viapi.tools.raycast;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * @author VadamDev
 * @since 29/01/2024
 */
public class RaycastResult {
    private final Location hitLocation;
    private final Block block;
    private final List<Entity> entities;

    public RaycastResult(@Nullable Location hitLocation, @Nullable Block block, @Nullable List<Entity> entities) {
        this.hitLocation = hitLocation;
        this.block = block;
        this.entities = entities != null ? entities : Collections.emptyList();
    }

    @Nullable
    public Location getHitLocation() {
        return hitLocation;
    }

    @Nullable
    public Block getBlock() {
        return block;
    }

    @Nullable
    public List<Entity> getEntities() {
        return entities;
    }

    public boolean isEmpty() {
        return hitLocation == null && block == null && entities.isEmpty();
    }
}
