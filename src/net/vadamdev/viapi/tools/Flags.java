package net.vadamdev.viapi.tools;

import net.vadamdev.viapi.internal.VIAPIPlugin;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author VadamDev
 * @since 04/08/2022
 */
public final class Flags {
    private static final Map<String, List<String>> flags = new HashMap<>();

    private Flags() {}

    public static void addTemporaryFlag(String key, String flag, long ticks) {
        addFlag(key, flag);
        Bukkit.getScheduler().runTaskLaterAsynchronously(VIAPIPlugin.instance, () -> removeFlag(key, flag), ticks);
    }

    public static void addFlag(String key, String flag) {
        flags.computeIfAbsent(key, k -> new ArrayList<>()).add(flag);
    }

    public static boolean hasFlag(String key, String flag) {
        return flags.containsKey(key) && flags.get(key).contains(flag);
    }

    public static void removeFlag(String key, String flag) {
        if(!flags.containsKey(key))
            return;

        flags.get(key).remove(flag);
    }
}
