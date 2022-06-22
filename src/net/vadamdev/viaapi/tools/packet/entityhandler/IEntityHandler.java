package net.vadamdev.viaapi.tools.packet.entityhandler;

import net.minecraft.server.v1_8_R3.ItemStack;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * @author VadamDev
 * @since 11/06/2022
 */
public interface IEntityHandler {
    /**
     * Spawn the entity with packet for a specified player
     * @param player
     */
    void spawn(Player player);

    /**
     * Delete the previously spawned entity
     * @param player
     */
    void delete(Player player);

    /**
     * Change the entity equipment without updating it to a player
     * @param slot
     * @param itemStack
     */
    default void updateLocalEquipment(int slot, ItemStack itemStack) {}

    /**
     * Change the entity equipment only to the specified player
     * @param player
     * @param slot
     * @param itemStack
     */
    default void updateEquipment(Player player, int slot, ItemStack itemStack) {}

    /**
     * Change the entity location without updating it to a player
     * @param location
     */
    void updateLocalPosition(Location location);

    /**
     * Change the entity location only to the specified player
     * @param player
     * @param location
     */
    void teleport(Player player, Location location);

    void setYaw(float yaw);

    /**
     * @return the location of the entity (updated with updateLocalPosition)
     */
    Location getLocation();
}
