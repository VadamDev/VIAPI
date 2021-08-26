package net.vadamdev.viaapi.tools.images.particle;

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
        this.height = -height;

        this.space = space;
    }

    @Deprecated
    public void render(Location location, double angle) {
        render(location, angle, 0, 0, 0);
    }

    public void render(Location location, double offsetX, double offsetY, double offsetZ, double angle) {
        for (PixelParticle pixelParticle : pixelParticles) {
            if(pixelParticle == null) continue;
            pixelParticle.render(location, offsetX, offsetY, offsetZ, angle);
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
