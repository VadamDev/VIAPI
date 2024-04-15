package net.vadamdev.viapi.internal.bossbar;

import net.vadamdev.viapi.tools.bossbar.BossBar;
import net.vadamdev.viapi.tools.bossbar.BossBarAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BossBarManager implements BossBarAPI, Listener {
    private final Map<Player, List<BossBarImpl>> bossBars;

    public BossBarManager() {
        this.bossBars = new HashMap<>();
    }

    protected void registerPlayer(Player player, BossBarImpl bossBar) {
        bossBars.computeIfAbsent(player, k -> new ArrayList<>()).add(bossBar);
    }

    protected void removePlayer(Player player, BossBarImpl bossBar) {
        bossBars.get(player).remove(bossBar);
    }

    /*
       Impl
     */

    @Override
    public BossBar createBossBar(String title, BossBar.Type type) {
        return new BossBarImpl(title, type);
    }

    /*
       Events
     */

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();

        if(bossBars.containsKey(player))
            bossBars.get(player).forEach(bossBar -> bossBar.handlePlayerMove(player, event.getFrom(), event.getTo()));
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        final Player player = event.getPlayer();

        if(bossBars.containsKey(player))
            bossBars.get(player).forEach(bossBar -> bossBar.handlePlayerMove(player, event.getFrom(), event.getTo()));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        final Player player = event.getPlayer();

        if(bossBars.containsKey(player))
            bossBars.get(player).forEach(bossBar -> bossBar.handlePlayerMove(player, player.getLocation(), event.getRespawnLocation()));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        if(bossBars.containsKey(player)) {
            new ArrayList<>(bossBars.get(player)).forEach(bossBar -> bossBar.removePlayer(player));
            bossBars.remove(player);
        }
    }
}
