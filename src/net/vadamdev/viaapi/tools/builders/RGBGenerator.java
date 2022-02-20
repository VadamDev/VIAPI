package net.vadamdev.viaapi.tools.builders;

import net.vadamdev.viaapi.VIAPI;
import org.bukkit.Color;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author VadamDev
 * @since 31.07.2021
 */
public class RGBGenerator extends BukkitRunnable {
    private int r, g, b;
    private final int n;

    public RGBGenerator() {
        this(15);
    }

    public RGBGenerator(int n) {
        this.n = n;
    }

    public void start(int delay, int period) {
        start(VIAPI.get(), delay, period);
    }

    public void start(Plugin plugin, int delay, int period) {
        this.runTaskTimerAsynchronously(plugin, delay, period);
    }

    public void cancel() {
        super.cancel();
    }

    @Override
    public void run() {
        if(r < 255 && b <= 0) r += n;
        else if(b < 255 && g <= 0) b += n;
        else if (r > 0) {
            r -= n;
            return;
        }

        if(b >= 255 && r <= 0 && g < 255) g += n;
        else if(b > 0 && r <= 0 && g >= 255) b -= n;
        else if(b <= 0 && r < 255 && g >= 255) r += n;
        else if(g > 0 && r >= 255) g -= n;
    }

    public Color getLastRGB() {
        return Color.fromRGB(r, g, b);
    }
}
