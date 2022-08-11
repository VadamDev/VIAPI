package net.vadamdev.viaapi.tools.entitystructure.impl;

import net.vadamdev.viaapi.VIAPI;
import net.vadamdev.viaapi.tools.entitystructure.IEntityStructure;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author VadamDev
 * @since 11/08/2022
 */
public class RangeEntityStructure {
    private final IEntityStructure entityStructure;

    private final int viewingRadius, updateDelay;
    private final List<Player> viewers;

    private RangeEntityStructure.PacketEntityHandlerUpdater updater;

    public RangeEntityStructure(IEntityStructure entityStructure, int viewingRadius, int updateDelay) {
        this.entityStructure = entityStructure;

        this.viewingRadius = viewingRadius;
        this.updateDelay = updateDelay;

        this.viewers = new ArrayList<>();
    }

    /**
     * Start the updater that check if a player is able to see the packet entity
     */
    public void spawn() {
        updater = new RangeEntityStructure.PacketEntityHandlerUpdater(updateDelay);
    }

    /**
     * Stop the updater and delete the packet entity for every viewer
     */
    public void delete() {
        updater.cancel();

        List<Player> players = viewers.parallelStream()
                .filter(Player::isOnline)
                .collect(Collectors.toList());
        entityStructure.delete(toPlayerArray(players));

        viewers.clear();
    }

    public void updateLocalPosition(Location location) {
        entityStructure.updateLocalPosition(location);
    }

    public void updateLocationAndRotationForViewers() {
        entityStructure.updateLocationAndRotation(toPlayerArray(viewers));
    }

    public void updateLocalRotationAroundAxisX(double angle) {
        entityStructure.updateLocalRotationAroundAxisX(angle);
    }

    public void updateLocalRotationAroundAxisY(double angle) {
        entityStructure.updateLocalRotationAroundAxisY(angle);
    }

    public void updateLocalRotationAroundAxisZ(double angle) {
        entityStructure.updateLocalRotationAroundAxisZ(angle);
    }

    public void updateLocalRotation(double angleX, double angleY, double angleZ) {
        entityStructure.updateLocalRotation(angleX, angleY, angleZ);
    }

    /**
     * @param player
     * @return true if the player is viewing (/ closeEnough) the packet entity
     */
    public boolean isViewing(Player player) {
        return viewers.contains(player);
    }

    private boolean isCloseEnough(Player player) {
        return player.getLocation().distanceSquared(entityStructure.getLocation()) <= viewingRadius * viewingRadius && player.getWorld().equals(entityStructure.getLocation().getWorld());
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

            List<Player> toAdd = Bukkit.getOnlinePlayers().parallelStream()
                    .filter(p -> !RangeEntityStructure.this.isViewing(p))
                    .filter(RangeEntityStructure.this::isCloseEnough)
                    .collect(Collectors.toList());
            entityStructure.spawn(toPlayerArray(toAdd));
            viewers.addAll(toAdd);

            List<Player> toRemove = Bukkit.getOnlinePlayers().parallelStream()
                    .filter(RangeEntityStructure.this::isViewing)
                    .filter(p -> !RangeEntityStructure.this.isCloseEnough(p))
                    .collect(Collectors.toList());
            toRemove.forEach(viewers::remove);
            entityStructure.delete(toPlayerArray(toRemove));
        }
    }

    private Player[] toPlayerArray(List<Player> playerList) {
        Player[] array = new Player[playerList.size()];
        playerList.toArray(array);
        return array;
    }
}
