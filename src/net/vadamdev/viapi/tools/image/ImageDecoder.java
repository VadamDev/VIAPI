package net.vadamdev.viapi.tools.image;

import net.vadamdev.viapi.tools.image.particle.ParticleImage;
import org.bukkit.Color;

import java.awt.image.BufferedImage;

/**
 * @author VadamDev
 * @since 02/09/2023
 */
public final class ImageDecoder {
    private ImageDecoder() {}

    public static ParticleImage decodeToParticleImage(BufferedImage image) {
        return decodeToParticleImage(image, 0.15f);
    }

    public static ParticleImage decodeToParticleImage(BufferedImage image, float spacing) {
        final int width = image.getWidth();
        final int height = image.getHeight();

        if(width > 64 || height > 64)
            throw new UnsupportedOperationException("Image width or height cannot exceed 64");

        final Color[][] pixels = new Color[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if(image.getRGB(x, y) == 0)
                    continue;

                final int rgb = image.getRGB(x, y);

                pixels[x][y] = Color.fromRGB(
                        (rgb >> 16) & 0xFF,
                        (rgb >> 8) & 0xFF,
                        rgb & 0xFF);
            }
        }

        return new ParticleImage(pixels, spacing, width, height);
    }
}
