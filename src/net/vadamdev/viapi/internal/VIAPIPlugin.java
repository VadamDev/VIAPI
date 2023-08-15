package net.vadamdev.viapi.internal;

import fr.minuskube.inv.InventoryManager;
import net.vadamdev.viapi.APIVersion;
import net.vadamdev.viapi.VIAPI;
import net.vadamdev.viapi.VIPlugin;
import net.vadamdev.viapi.tools.utils.BungeeUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.spigotmc.SpigotConfig;

import java.util.HashMap;
import java.util.Map;

public class VIAPIPlugin extends VIPlugin implements VIAPI {
    public static VIAPIPlugin instance;

    private InventoryManager inventoryManager;

    private final Map<String, APIVersion> dependsMap = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        final FileConfiguration config = getConfig();

        /*if(config.getBoolean("nametag"))
            registerListener(new NametagManager());*/

        if(config.getBoolean("egg-replacer"))
            registerListener(new EggListener());

        registerCommand(new VIAPICommand());

        if(SpigotConfig.bungee)
            BungeeUtils.registerOutgoingPluginChannel(this, "BungeeCord");

        inventoryManager = new InventoryManager(this);
    }

    @Override
    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    @Override
    public Map<String, APIVersion> getDependsMap() {
        return dependsMap;
    }
}
