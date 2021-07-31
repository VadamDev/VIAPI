package net.vadamdev.viaapi.tools.images;

import net.vadamdev.viaapi.tools.images.particle.ParticleImage;
import net.vadamdev.viaapi.tools.images.particle.PixelParticle;
import net.vadamdev.viaapi.tools.utils.FileUtil;

import javax.xml.parsers.SAXParser;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageDecoder {
    /**
     * @author VadamDev
     * @since 22.07.2021
     */

    private static final int PARTICLE_SPACE = 6;

    public static ParticleImage decode(BufferedImage image) {
        PixelParticle[] pixelParticles = new PixelParticle[image.getWidth() * image.getHeight()];

        int i = 0;
        for(double x = 0; x < image.getWidth(); x++) {
            for(double y = 0; y < image.getHeight(); y++) {
                if(image.getRGB((int) x, (int) y) == 0) continue;

                pixelParticles[i] = new PixelParticle(x / PARTICLE_SPACE, y / PARTICLE_SPACE, image.getRGB((int) x, (int) y));
                i++;
            }
        }

        return new ParticleImage(pixelParticles, image.getWidth(), image.getHeight(), PARTICLE_SPACE);
    }
}
