package net.vadamdev.viapi.internal;

import fr.minuskube.inv.InventoryManager;
import net.vadamdev.viapi.APIVersion;
import net.vadamdev.viapi.VIAPI;
import net.vadamdev.viapi.VIPlugin;
import net.vadamdev.viapi.tools.nametag.NametagEditor;
import net.vadamdev.viapi.tools.nametag.NametagManager;
import net.vadamdev.viapi.tools.utils.BungeeUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.spigotmc.SpigotConfig;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class VIAPIPlugin extends VIPlugin implements VIAPI {
    public static VIAPIPlugin instance;

    private InventoryManager inventoryManager;
    private NametagManager nametagManager;

    private final Map<String, APIVersion> dependsMap = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        final FileConfiguration config = getConfig();

        if(config.getBoolean("nametag")) {
            nametagManager = new NametagManager();
            registerListener(nametagManager);
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
    public NametagEditor getNametagEditor() {
        return nametagManager;
    }

    @Override
    public Map<String, APIVersion> getDependsMap() {
        return dependsMap;
    }
}
