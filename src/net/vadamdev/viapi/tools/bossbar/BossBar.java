package net.vadamdev.viapi.tools.bossbar;

import org.bukkit.entity.Player;

import java.util.Set;

public interface BossBar {
    void addPlayer(Player player);
    void removePlayer(Player player);
    void removeAll();

    void setTitle(String title);
    String getTitle();

    void setProgress(float progress);
    float getProgress();

    Set<Player> getPlayers();

    enum Type {
        WITHER, ENDER_DRAGON;
    }
}
