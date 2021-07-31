package net.vadamdev.viaapi.tools.images.particle;

import net.vadamdev.viaapi.tools.enums.EnumDirection;
import org.bukkit.Location;

public class ParticleImage {
    /**
     * @author VadamDev
     * @since 22.07.2021
     */

    private PixelParticle[] pixelParticles;
    private double width, height;
    private int space;

    public ParticleImage(PixelParticle[] pixelParticles, int width, int height, int space) {
        this.pixelParticles = pixelParticles;

        this.width = width;
        this.height = height;

        this.space = space;
    }

    public void render(Location location) {
        Location loc = location.clone().add(-(width / 2 / space), height / 2 / space, 0);

        for (PixelParticle pixelParticle : pixelParticles) {
            if(pixelParticle == null) continue;
            pixelParticle.render(loc);
        }
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public int getSpace() {
        return space;
    }
}
