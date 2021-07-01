package net.vadamdev.viaapi;

import net.vadamdev.viaapi.api.inv.InventoryManager;
import net.vadamdev.viaapi.tools.bungeecord.BungeeUtils;
import net.vadamdev.viaapi.tools.scheduler.Scheduler;
import org.bukkit.plugin.java.JavaPlugin;

public class VIAPI extends JavaPlugin {
    /**
     * @author VadamDev & Implements
     * @since 09.10.2020
     */

    private static VIAPI viapi;

    private static Scheduler scheduler;
    private static InventoryManager invManager;

    @Override
    public void onEnable() {
        viapi = this;

        scheduler = new Scheduler();

        saveDefaultConfig();

        if(getConfig().getBoolean("bungeecord")) BungeeUtils.registerOutgoingPluginChannel(this, "BungeeCord");

        invManager = new InventoryManager(this);
        invManager.init();
    }

    public static VIAPI get() { return viapi; }
    public static InventoryManager getInvManager() { return invManager; }
    public static Scheduler getScheduler() { return scheduler; }
}
