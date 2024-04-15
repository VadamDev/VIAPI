package net.vadamdev.viapi.internal;

import fr.minuskube.inv.InventoryManager;
import net.vadamdev.viapi.APIVersion;
import net.vadamdev.viapi.VIAPI;
import net.vadamdev.viapi.VIPlugin;
import net.vadamdev.viapi.internal.listeners.EggListener;
import net.vadamdev.viapi.internal.bossbar.BossBarManager;
import net.vadamdev.viapi.internal.nametag.NametagManager;
import net.vadamdev.viapi.tools.bossbar.BossBarAPI;
import net.vadamdev.viapi.tools.nametag.NametagAPI;
import net.vadamdev.viapi.tools.utils.BungeeUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.spigotmc.SpigotConfig;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class VIAPIPlugin extends VIPlugin implements VIAPI {
    public static VIAPIPlugin instance;

    private NametagManager nametagManager;
    private BossBarManager bossBarManager;
    private InventoryManager inventoryManager;

    private final Map<String, APIVersion> dependsMap;

    public VIAPIPlugin() {
        this.dependsMap = new HashMap<>();
    }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        final FileConfiguration config = getConfig();

        if(config.getBoolean("nametag-api")) {
            nametagManager = new NametagManager();
            registerListener(nametagManager);
        }

        if(config.getBoolean("bossbar-api")) {
            bossBarManager = new BossBarManager();
            registerListener(bossBarManager);
        }

        if(config.getBoolean("egg-replacer"))
            registerListener(new EggListener());

        registerCommand(new VIAPICommand());

        if(SpigotConfig.bungee)
            BungeeUtils.registerOutgoingPluginChannel(this, "BungeeCord");

        inventoryManager = new InventoryManager(this);

        VIAPI.Provider.set(this);
    }

    @Override
    public void onDisable() {
        if(nametagManager != null)
            nametagManager.shutdown();
    }

    @Override
    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    @Nullable
    @Override
    public NametagAPI getNameTagAPI() {
        return nametagManager;
    }

    @Nullable
    @Override
    public BossBarAPI getBossBarAPI() {
        return bossBarManager;
    }

    @Override
    public Map<String, APIVersion> getDependsMap() {
        return dependsMap;
    }
}
