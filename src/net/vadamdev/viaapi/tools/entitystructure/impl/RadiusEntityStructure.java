package net.vadamdev.viaapi.tools.entitystructure.impl;

import net.vadamdev.viaapi.tools.entitystructure.IEntityStructure;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author VadamDev
 * @since 11/08/2022
 */
public class RadiusEntityStructure {
    private final IEntityStructure entityStructure;
    private final List<Player> viewers;

    public RadiusEntityStructure(IEntityStructure entityStructure) {
        this.entityStructure = entityStructure;
        this.viewers = new ArrayList<>();
    }

    /**
     * Spawn the packet entity for nearby players
     * @param center Basically the location of the packetentity
     * @param radius Visibility radius
     */
    public void spawnWithRadius(Location center, int radius) {
        List<Player> toAdd = Bukkit.getOnlinePlayers().parallelStream()
                .filter(p -> !viewers.contains(p))
                .filter(player -> player.getLocation().getWorld().equals(center.getWorld()))
                .filter(player -> player.getLocation().distanceSquared(center) <= radius * radius)
                .collect(Collectors.toList());

        entityStructure.spawn(toPlayerArray(toAdd));
        viewers.addAll(toAdd);
    }

    /**
     * Delete the packet entity for every viewer
     */
    public void delete() {
        List<Player> toRemove = viewers.stream().filter(Player::isOnline).collect(Collectors.toList());
        entityStructure.delete(toPlayerArray(toRemove));
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

    public List<Player> getViewers() {
        return viewers;
    }

    private Player[] toPlayerArray(List<Player> playerList) {
        Player[] array = new Player[playerList.size()];
        playerList.toArray(array);
        return array;
    }
}
