package net.vadamdev.viapi.tools.packet;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Collections;

/**
 * @author VadamDev
 * @since 15/11/2023
 */
public interface INamedPacketEntity {
    void updateCustomName(Collection<Player> players);
    default void updateCustomName(Player player) {
        updateCustomName(Collections.singleton(player));
    }

    void setCustomNameLocal(String name);
    void setCustomNameVisibleLocal(boolean flag);
}
