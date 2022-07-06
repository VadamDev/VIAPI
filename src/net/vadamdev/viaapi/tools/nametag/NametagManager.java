package net.vadamdev.viaapi.tools.nametag;

import net.vadamdev.viaapi.VIAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author VadamDev
 * @since 02/07/2022
 */
public class NametagManager implements Listener {
    protected static final Map<String, NameTag> nametags = new HashMap<>();

    public NametagManager() {
        new BukkitRunnable() {
            @Override
            public void run() {
                nametags.values().forEach(nametag -> {
                    if(!nametag.shouldShowNametag() && !nametag.isHidden()) {
                        nametag.hideCustomNametag();
                        nametag.setHidden(true);
                    }else if(nametag.shouldShowNametag() && nametag.isHidden()) {
                        nametag.showCustomNametag();
                        nametag.setHidden(false);
                    }
                });
            }
        }.runTaskTimerAsynchronously(VIAPI.get(), 0, 10);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        nametags.values().forEach(nametag -> NameTagEditor.applyNameTag(nametag, event.getPlayer()));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if(nametags.containsKey(player.getName()))
            NameTagEditor.removeCustomNametag(player);
    }
}
