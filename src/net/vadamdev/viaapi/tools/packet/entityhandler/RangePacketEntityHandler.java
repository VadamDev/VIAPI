package net.vadamdev.viaapi.tools.packet.entityhandler;

import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.vadamdev.viaapi.VIAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author VadamDev
 * @since 10/05/2022
 */
public final class RangePacketEntityHandler {
    private final IEntityHandler entityHandler;

    private final int viewingRadius, updateDelay;
    private final List<Player> viewers;

    private PacketEntityHandlerUpdater updater;

    public RangePacketEntityHandler(IEntityHandler entityHandler, int viewingRadius, int updateDelay) {
        this.entityHandler = entityHandler;

        this.viewingRadius = viewingRadius;
        this.updateDelay = updateDelay;

        this.viewers = new ArrayList<>();
    }

    public RangePacketEntityHandler(EntityLiving entity, int viewingRadius, int updateDelay) {
        this(new PacketEntityHandler(entity), viewingRadius, updateDelay);
    }

    /**
     * Start the updater that check if a player is able to see the packet entity
     */
    public void spawn() {
        updater = new PacketEntityHandlerUpdater(updateDelay);
    }

    /**
     * Stop the updater and delete the packet entity for every viewer
     */
    public void delete() {
        updater.cancel();

        viewers.parallelStream()
                .filter(Player::isOnline)
                .forEach(entityHandler::delete);

        viewers.clear();
    }

    /**
     * Change the entity equipment for every viewer and change the local equipment
     * @param slot
     * @param itemStack
     */
    public void updateEquipmentForViewers(int slot, ItemStack itemStack) {
        entityHandler.updateLocalEquipment(slot, itemStack);
        viewers.forEach(player -> entityHandler.updateEquipment(player, slot, itemStack));
    }

    /**
     * Teleport the entity for every viewer and change the local location
     * @param location
     */
    public void teleportForViewers(Location location) {
        entityHandler.updateLocalPosition(location);
        viewers.forEach(p -> entityHandler.teleport(p, location));
    }

    /**
     * @param player
     * @return true if the player is viewing (/ closeEnough) the packet entity
     */
    public boolean isViewing(Player player) {
        return viewers.contains(player);
    }

    private boolean isCloseEnough(Player player) {
        return player.getLocation().distanceSquared(entityHandler.getLocation()) <= viewingRadius * viewingRadius && player.getWorld().equals(entityHandler.getLocation().getWorld());
    }

    private class PacketEntityHandlerUpdater extends BukkitRunnable {
        public PacketEntityHandlerUpdater(int period) {
            runTaskTimerAsynchronously(VIAPI.get(), 0, period);
        }

        @Override
        public void run() {
            new ArrayList<>(viewers).parallelStream()
                    .filter(player -> !player.isOnline())
                    .forEach(viewers::remove);

            Bukkit.getOnlinePlayers().parallelStream()
                    .filter(p -> !RangePacketEntityHandler.this.isViewing(p))
                    .filter(RangePacketEntityHandler.this::isCloseEnough)
                    .forEach(player -> {
                        entityHandler.spawn(player);
                        viewers.add(player);
                    });

            Bukkit.getOnlinePlayers().parallelStream()
                    .filter(RangePacketEntityHandler.this::isViewing)
                    .filter(p -> !RangePacketEntityHandler.this.isCloseEnough(p))
                    .forEach(player -> {
                        entityHandler.delete(player);
                        viewers.remove(player);
                    });
        }
    }
}
