package net.vadamdev.viaapi.tools.nametag;

import org.bukkit.entity.Player;

/**
 * @author VadamDev
 * @since 01/07/2022
 */
public class NameTagEditor {
    public static void setPrefix(Player player, String prefix) {
        changeNametag(player, prefix, "");
    }

    public static void setSuffix(Player player, String suffix) {
        changeNametag(player, "", suffix);
    }

    public static void changeNametag(Player player, String prefix, String suffix) {
        NameTag nameTag = new NameTag(player, prefix, suffix);

        if(nameTag.shouldShowNametag() && !nameTag.isHidden())
            nameTag.showCustomNametag();

        NametagManager.nametags.put(player.getName(), nameTag);
    }

    public static void removeCustomNametag(Player player) {
        String playerName = player.getName();

        if(!NametagManager.nametags.containsKey(playerName))
            return;

        NametagManager.nametags.get(playerName).hideCustomNametag();

        NametagManager.nametags.remove(playerName);
    }

    protected static void applyNameTag(NameTag nameTag, Player viewer) {
        nameTag.applyNameTag(viewer);
    }
}
