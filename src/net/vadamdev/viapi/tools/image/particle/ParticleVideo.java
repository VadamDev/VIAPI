package net.vadamdev.viapi.tools.image.particle;

import net.vadamdev.viapi.internal.VIAPIPlugin;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author VadamDev
 * @since 18/11/2023
 */
public class ParticleVideo implements Cloneable {
    private final ParticleImage[] images;
    private final int frameTime;

    private Updater updater;

    public ParticleVideo(ParticleImage[] images, int frameTime) {
        this.images = images;
        this.frameTime = frameTime;
    }

    public void start(Location location, double angle) {
        if(updater != null)
            return;

        updater = new Updater(location, angle);
    }

    public void updateRotation(double angle) {
        if(updater == null)
            return;

        updater.updateRotation(angle);
    }

    public void stop() {
        if(updater == null)
            return;

        updater.cancel();
        updater = null;
    }

    private class Updater extends BukkitRunnable {
        private final Location location;
        private double angle;

        private int i, frame;

        private Updater(Location location, double angle) {
            this.location = location;
            this.angle = angle;

            runTaskTimerAsynchronously(VIAPIPlugin.instance, 0, 2);
        }

        @Override
        public void run() {
            images[frame].render(location, 0, 0, 0, angle);

            if(i > frameTime) {
                frame++;
                i = 0;

                if(frame >= images.length)
                    frame = 0;
            }

            i++;
        }

        private void updateRotation(double angle) {
            this.angle = angle;
        }
    }
}
