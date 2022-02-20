package net.vadamdev.viaapi.tools.images.particle;

import org.bukkit.Location;

/**
 * @author VadamDev
 * @since 22.07.2021
 */
public class ParticleImage {
    private final PixelParticle[] buffer;

    public ParticleImage(PixelParticle[] buffer) {
        this.buffer = buffer;
    }

    public void render(Location location, double offsetX, double offsetY, double offsetZ, double angle) {
        for (PixelParticle pixelParticle : buffer) {
            if(pixelParticle != null) pixelParticle.render(location, offsetX, offsetY, offsetZ, angle);
        }
    }
}
