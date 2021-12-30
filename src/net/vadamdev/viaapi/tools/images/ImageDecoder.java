package net.vadamdev.viaapi.tools.images;

import net.vadamdev.viaapi.tools.images.particle.ParticleImage;
import net.vadamdev.viaapi.tools.images.particle.PixelParticle;

import java.awt.image.BufferedImage;

public class ImageDecoder {
    /**
     * @author VadamDev
     * @since 22.07.2021
     */

    private static final float PARTICLE_SPACE = 8;

    @Deprecated
    public static ParticleImage decode(BufferedImage image) {
        return decodeToParticleImage(image);
    }

    public static ParticleImage decodeToParticleImage(BufferedImage image) {
        PixelParticle[] buffer = new PixelParticle[image.getWidth() * image.getHeight()];

        int i = 0;
        for(int x = 0; x < image.getWidth(); x++) {
            for(int y = 0; y < image.getHeight(); y++) {
                if(image.getRGB(x, y) == 0) continue;

                buffer[i] = new PixelParticle(x / PARTICLE_SPACE - image.getWidth() / PARTICLE_SPACE / 2, -(y / PARTICLE_SPACE - image.getHeight() / PARTICLE_SPACE / 2), image.getRGB(x, y));
                i++;
            }
        }

        return new ParticleImage(buffer);
    }
}
