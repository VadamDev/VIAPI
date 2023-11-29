package net.vadamdev.viapi.tools.image;

import net.vadamdev.viapi.tools.image.particle.ParticleImage;
import net.vadamdev.viapi.tools.image.particle.ParticleVideo;
import org.bukkit.Color;

import java.awt.image.BufferedImage;

/**
 * @author VadamDev
 * @since 02/09/2023
 */
public final class ImageDecoder {
    private static final float DEFAULT_SPACING = 0.15f;

    private ImageDecoder() {}

    public static ParticleImage decodeToParticleImage(BufferedImage image) {
        return decodeToParticleImage(image, DEFAULT_SPACING);
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

    public static  ParticleVideo decodeParticleVideo(BufferedImage image, int frameTime) {
        return decodeToParticleVideo(image, frameTime, DEFAULT_SPACING);
    }

    public static ParticleVideo decodeToParticleVideo(BufferedImage image, int frameTime, float spacing) {
        final int width = image.getWidth();
        final int height = image.getHeight();

        if(height > 64)
            throw new UnsupportedOperationException("Image height cannot exceed 64");

        if(width < height)
            throw new UnsupportedOperationException("Width cannot be greater than height");

        if(width % height != 0)
            throw new UnsupportedOperationException("Width must be a power of height");

        final int imageSize = width / height;

        final ParticleImage[] images = new ParticleImage[imageSize];
        for (int i = 0; i < imageSize; i++)
            images[i] = decodeToParticleImage(image.getSubimage(i * height, 0, height, height), spacing);

        return new ParticleVideo(images, frameTime);
    }
}
