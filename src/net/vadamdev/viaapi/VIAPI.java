package net.vadamdev.viaapi;

import net.vadamdev.viaapi.api.inv.InventoryManager;
import net.vadamdev.viaapi.startup.VIAPICommand;
import net.vadamdev.viaapi.tools.bungeecord.BungeeUtils;
import net.vadamdev.viaapi.tools.scheduler.Scheduler;

public class VIAPI extends VIPlugin {
    /**
     * @author VadamDev & Implements
     * @since 09.10.2020
     */

    private static VIAPI api;

    private static Scheduler scheduler;
    private static InventoryManager invManager;

    @Override
    public void onEnable() {
        api = this;

        saveDefaultConfig();

        registerCommand(new VIAPICommand());

        setupTools();
    }

    private void setupTools() {
        scheduler = new Scheduler();

        if(getConfig().getBoolean("bungeecord")) BungeeUtils.registerOutgoingPluginChannel(this, "BungeeCord");

        invManager = new InventoryManager(this).init();
    }

    public static VIAPI get() { return api; }
    public static InventoryManager getInvManager() { return invManager; }
    public static Scheduler getScheduler() { return scheduler; }
}
