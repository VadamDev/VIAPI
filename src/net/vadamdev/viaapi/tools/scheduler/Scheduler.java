package net.vadamdev.viaapi.tools.scheduler;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class Scheduler {
    /**
     * @author VadamDev
     * @since 14.01.2021
     */

    public void runTaskLater(Plugin plugin, Consumer<Runnable> r, long delay) {
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() { r.accept(this); }
        }, delay);
    }

    public void runTaskLaterAsynchronously(Plugin plugin, Consumer<Runnable> r, long delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
            @Override
            public void run() { r.accept(this); }
        }, delay);
    }

    public void runTaskTimer(Plugin plugin, Consumer<Runnable> r, long delay, long period) {
        Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() { r.accept(this); }
        }, delay, period);
    }

    public void runTaskTimerAsynchronously(Plugin plugin, Consumer<Runnable> r, long delay, long period) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
            @Override
            public void run() { r.accept(this); }
        }, delay, period);
    }
}
