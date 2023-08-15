package net.vadamdev.viapi.tools;

import org.bukkit.Color;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author VadamDev
 * @since 31/07/2021
 */
public class RGBGenerator extends BukkitRunnable {
    private final int n;
    private int r, g, b;

    public RGBGenerator() {
        this(15);
    }

    public RGBGenerator(int n) {
        this.n = n;
    }

    public void start(Plugin plugin, int delay, int period) {
        runTaskTimerAsynchronously(plugin, delay, period);
    }

    @Override
    public void run() {
        if(r < 255 && b <= 0)
            r += n;
        else if(b < 255 && g <= 0)
            b += n;
        else if (r > 0) {
            r -= n;
            return;
        }

        if(b >= 255 && r <= 0 && g < 255)
            g += n;
        else if(b > 0 && r <= 0 && g >= 255)
            b -= n;
        else if(b <= 0 && r < 255 && g >= 255)
            r += n;
        else if(g > 0 && r >= 255)
            g -= n;
    }

    public Color getLastRGB() {
        return Color.fromRGB(r, g, b);
    }
}
