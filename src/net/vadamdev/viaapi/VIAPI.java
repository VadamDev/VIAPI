package net.vadamdev.viaapi;

import net.vadamdev.viaapi.api.inv.InventoryManager;
import net.vadamdev.viaapi.integration.IntegrationManager;
import net.vadamdev.viaapi.startup.APIVersion;
import net.vadamdev.viaapi.startup.VIAPICommand;
import net.vadamdev.viaapi.tools.bungeecord.BungeeUtils;
import net.vadamdev.viaapi.tools.scheduler.Scheduler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author VadamDev & Implements
 * @since 09.10.2020
 */
public class VIAPI extends VIPlugin {
    private static VIAPI api;

    private IntegrationManager integrationManager;

    private static Scheduler scheduler;
    private static InventoryManager invManager;

    private final Map<String, APIVersion> dependsMap = new HashMap<>();

    @Override
    public void onEnable() {
        api = this;

        saveDefaultConfig();

        registerCommand(new VIAPICommand());

        setupTools();

        integrationManager = new IntegrationManager();
        try { integrationManager.loadAllIntegrations(); }catch (ExecutionException | InterruptedException e) { e.printStackTrace(); }
    }

    @Override
    public void onDisable() {
        integrationManager.unloadAllIntegrations();
    }

    private void setupTools() {
        scheduler = new Scheduler();

        if(getConfig().getBoolean("bungeecord")) BungeeUtils.registerOutgoingPluginChannel(this, "BungeeCord");

        invManager = new InventoryManager(this).init();
    }

    public void registerListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    public static VIAPI get() { return api; }
    public static InventoryManager getInvManager() { return invManager; }
    public static Scheduler getScheduler() { return scheduler; }

    public IntegrationManager getIntegrationManager() {
        return integrationManager;
    }

    public Map<String, APIVersion> getDependsMap() {
        return dependsMap;
    }
}
