package net.vadamdev.viaapi.tools.builders;

import net.vadamdev.viaapi.VIAPI;
import org.bukkit.Color;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class RGBGenerator extends BukkitRunnable {
    private int r, g, b;

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
        if(r < 255 && b <= 0) r += 15;
        else if(b < 255 && g <= 0) b += 15;
        else if (r > 0) {
            r -= 15;
            return;
        }

        if(b >= 255 && r <= 0 && g < 255) g += 15;
        else if(b > 0 && r <= 0 && g >= 255) b -= 15;
        else if(b <= 0 && r < 255 && g >= 255) r += 15;
        else if(g > 0 && r >= 255) g -= 15;
    }

    public Color getLastRGB() {
        return Color.fromRGB(r, g, b);
    }
}
