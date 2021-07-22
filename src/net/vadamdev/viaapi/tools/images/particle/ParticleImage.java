package net.vadamdev.viaapi.tools.images.particle;

import org.bukkit.Location;

public class ParticleImage {
    /**
     * @author VadamDev
     * @since 22.07.2021
     */

    private PixelParticle[] pixelParticles;
    private int width, height;
    private float space;

    public ParticleImage(PixelParticle[] pixelParticles, int width, int height, float space) {
        this.pixelParticles = pixelParticles;

        this.width = width;
        this.height = height;

        this.space = space;
    }

    public void render(Location location) {
        Location loc = location.clone().add(-(width / 2) / space, height / 2 / space, 0);

        for (PixelParticle pixelParticle : pixelParticles) {
            if(pixelParticle == null) continue;
            pixelParticle.render(loc);
        }
    }
}
