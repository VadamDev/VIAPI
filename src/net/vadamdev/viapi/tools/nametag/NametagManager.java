package net.vadamdev.viapi.tools.nametag;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author VadamDev
 * @since 25/08/2023
 */
public class NametagManager implements NametagEditor, Listener {
    private final Map<String, NameTag> nametags;

    private final ScheduledExecutorService executor;

    public NametagManager() {
        this.nametags = new HashMap<>();

        this.executor = Executors.newSingleThreadScheduledExecutor();
        this.executor.scheduleAtFixedRate(this::update, 0, 500, TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        executor.shutdownNow();
    }

    /*
       Impl
     */

    @Override
    public void setCustomNametag(Player player, String prefix, String suffix) {
        if(prefix.length() >= 16 || suffix.length() >= 16)
            throw new InvalidParameterException("Prefix or suffix length can't exceed 15 !");

        final NameTag nameTag = new NameTag(player, prefix, suffix);
        if(nameTag.shouldShowNametag())
            nameTag.show();

        nametags.put(player.getName(), nameTag);
    }

    @Override
    public void updateCustomNametag(Player player, String prefix, String suffix) {
        if(!hasCustomNametag(player))
            return;

        if(prefix.length() >= 16 || suffix.length() >= 16)
            throw new InvalidParameterException("Prefix or suffix length can't exceed 15 !");

        nametags.get(player.getName()).updateTag(prefix, suffix);
    }

    @Override
    public void resetNametag(Player player) {
        if(!hasCustomNametag(player))
            return;

        final String playerName = player.getName();

        nametags.get(playerName).hide();
        nametags.remove(playerName);
    }

    @Override
    public boolean hasCustomNametag(Player player) {
        return nametags.containsKey(player.getName());
    }

    /*
       Events
     */

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        nametags.values().forEach(nametag -> nametag.show(player));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        final String playerName = event.getPlayer().getName();

        if(nametags.containsKey(playerName)) {
            nametags.get(playerName).hide();
            nametags.remove(playerName);
        }
    }

    /*
       Updater
     */

    public void update() {
        nametags.forEach((playerName, nametag) -> {
            if(!nametag.shouldShowNametag() && nametag.isVisible())
                nametag.hide();
            else if(nametag.shouldShowNametag() && !nametag.isVisible())
                nametag.show();
        });
    }
}
