package net.vadamdev.viapi.tools.packet.handler;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Set;

/**
 * @author VadamDev
 * @since 02/09/2023
 */
public interface IPacketEntityHandler {
    void spawn();
    void delete();

    boolean isViewing(Player player);
    Set<Player> getViewers();

    Location getLocation();
}
