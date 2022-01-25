package net.vadamdev.viaapi.tools.images.map;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author VadamDev
 * @since 25.01.22
 */
public class MapPoster {
    private final BufferedImage image;
    private final List<Short> mapIds;
    private final World world;

    public MapPoster(BufferedImage image, List<Short> mapIds, World world) {
        this.image = image;
        this.mapIds = mapIds;
        this.world = world;
    }

    public List<ItemStack> getAsItemStackList() {
        List<ItemStack> itemStacks = new ArrayList<>();
        mapIds.forEach(mapId -> itemStacks.add(new ItemStack(Material.MAP, 1, mapId)));
        return itemStacks;
    }

    public BufferedImage getImage() {
        return image;
    }

    public List<Short> getMapIds() {
        return mapIds;
    }

    public World getWorld() {
        return world;
    }
}
