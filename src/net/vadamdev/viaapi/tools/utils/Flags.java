package net.vadamdev.viaapi.tools.utils;

import net.vadamdev.viaapi.VIAPI;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author VadamDev
 * @since 16.02.2022
 */
public final class Flags {
    private static final Map<String, List<String>> flags = new HashMap<>();

    public static void addTemporaryFlag(Player player, String flag, long ticks) {
        if(flags.containsKey(player.getName())) {
            List<String> fs = flags.get(player.getName());
            fs.add(flag);
            flags.replace(player.getName(), fs);

            VIAPI.getScheduler().runTaskLaterAsynchronously(VIAPI.get(), r -> removeFlag(player, flag), ticks);

            return;
        }

        List<String> fs = new ArrayList<>();
        fs.add(flag);
        flags.put(player.getName(), fs);

        VIAPI.getScheduler().runTaskLaterAsynchronously(VIAPI.get(), r -> removeFlag(player, flag), ticks);
    }

    public static void addFlag(Player player, String flag) {
        if(flags.containsKey(player.getName())) {
            List<String> fs = flags.get(player.getName());
            fs.add(flag);
            flags.replace(player.getName(), fs);
            return;
        }

        List<String> fs = new ArrayList<>();
        fs.add(flag);
        flags.put(player.getName(), fs);
    }

    public static boolean hasFlag(Player player, String flag) {
        if(!flags.containsKey(player.getName())) return false;
        return flags.get(player.getName()).contains(flag);
    }

    public static void removeFlag(Player player, String flag) {
        if(flags.containsKey(player.getName())) {
            List<String> fs = flags.get(player.getName());
            fs.remove(flag);
            flags.replace(player.getName(), fs);
        }
    }
}
