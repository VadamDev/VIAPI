package net.vadamdev.viapi.tools.nametag;

import org.bukkit.entity.Player;

/**
 * @author VadamDev
 * @since 25/08/2023
 */
public interface NametagEditor {
    void setCustomNametag(Player player, String prefix, String suffix);
    void updateCustomNametag(Player player, String prefix, String suffix);
    void resetNametag(Player player);
    boolean hasCustomNametag(Player player);

    default void setPrefix(Player player, String prefix) {
        setCustomNametag(player, prefix, "");
    }

    default void setSuffix(Player player, String suffix) {
        setCustomNametag(player, "", suffix);
    }
}
