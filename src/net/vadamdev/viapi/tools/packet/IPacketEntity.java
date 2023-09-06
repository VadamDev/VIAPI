package net.vadamdev.viapi.tools.packet;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Collections;

/**
 * @author VadamDev
 * @since 02/09/2023
 */
public interface IPacketEntity {
    void spawn(Collection<Player> players);
    default void spawn(Player player) {
        spawn(Collections.singleton(player));
    }

    void delete(Collection<Player> players);
    default void delete(Player player) {
        delete(Collections.singleton(player));
    }

    void teleportLocal(Location location);

    void teleport(Location location, Collection<Player> players);
    default void teleport(Location location, Player player) {
        teleport(location, Collections.singleton(player));
    }

    default void updateLocation(Collection<Player> players) {
        teleport(getLocalLocation(), players);
    }

    default void updateLocation(Player player) {
        updateLocation(Collections.singleton(player));
    }

    Location getLocalLocation();
}
