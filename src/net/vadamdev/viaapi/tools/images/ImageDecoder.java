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
        for(double x = 0; x < image.getWidth(); x++) {
            for(double y = 0; y < image.getHeight(); y++) {
                if(image.getRGB((int) x, (int) y) == 0) continue;

                double ppX = x / PARTICLE_SPACE - image.getWidth() / PARTICLE_SPACE / 2;
                double ppY = y / PARTICLE_SPACE - image.getHeight() / PARTICLE_SPACE / 2;

                buffer[i] = new PixelParticle(ppX, -ppY, image.getRGB((int) x, (int) y));
                i++;
            }
        }

        return new ParticleImage(buffer);
    }
}
