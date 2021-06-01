package net.vadamdev.viaapi;

import net.vadamdev.viaapi.api.inv.InventoryManager;
import net.vadamdev.viaapi.api.packets.PacketManager;
import net.vadamdev.viaapi.plugin.listeners.PacketListener;
import net.vadamdev.viaapi.plugin.listeners.PlayerJoin;
import net.vadamdev.viaapi.tools.bungeecord.BungeeUtils;
import net.vadamdev.viaapi.tools.scheduler.Scheduler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class VIAPI extends JavaPlugin {
    /**
     * @author VadamDev & Implements
     * @since 09.10.2020
     */

    private static VIAPI viapi;

    private static PacketManager packetManager;
    private static Scheduler scheduler;
    private static InventoryManager invManager;

    @Override
    public void onEnable() {
        viapi = this;

        scheduler = new Scheduler();

        saveDefaultConfig();

        if(getConfig().getBoolean("bungeecord")) BungeeUtils.registerOutgoingPluginChannel(this, "BungeeCord");

        //registerListeners();

        invManager = new InventoryManager(this);
        invManager.init();
    }

    private void registerListeners(){
        Arrays.asList(
                new PlayerJoin(),
                new PacketListener()
        ).forEach(e -> this.getServer().getPluginManager().registerEvents(e, this));
    }

    public static VIAPI get() { return viapi; }
    public static PacketManager getPacketManager() { return packetManager; }
    public static InventoryManager getInvManager() { return invManager; }
    public static Scheduler getScheduler() { return scheduler; }
}
