package net.vadamdev.viapi;

import fr.minuskube.inv.InventoryManager;
import net.vadamdev.viapi.tools.bossbar.BossBarAPI;
import net.vadamdev.viapi.tools.nametag.NametagAPI;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * @author VadamDev
 * @since 04/08/2023
 */
public interface VIAPI {
    InventoryManager getInventoryManager();

    @Nullable
    NametagAPI getNameTagAPI();

    @Nullable
    BossBarAPI getBossBarAPI();

    Map<String, APIVersion> getDependsMap();

    final class Provider {
        private static VIAPI api;

        private Provider() {}

        @Nonnull
        public static VIAPI get() {
            return api;
        }

        public static void set(@Nonnull VIAPI api) {
            Provider.api = api;
        }
    }
}
