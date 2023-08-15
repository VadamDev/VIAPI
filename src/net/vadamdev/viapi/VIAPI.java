package net.vadamdev.viapi;

import fr.minuskube.inv.InventoryManager;
import net.vadamdev.viapi.internal.VIAPIPlugin;

import java.util.Map;

/**
 * @author VadamDev
 * @since 04/08/2023
 */
public interface VIAPI {
    InventoryManager getInventoryManager();

    Map<String, APIVersion> getDependsMap();

    static VIAPI get() {
        return VIAPIPlugin.instance;
    }
}
