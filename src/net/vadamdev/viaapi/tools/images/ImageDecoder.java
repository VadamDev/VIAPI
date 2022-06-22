package net.vadamdev.viaapi.tools.images;

import net.vadamdev.viaapi.tools.images.map.ImageMapRenderer;
import net.vadamdev.viaapi.tools.images.map.MapPoster;
import net.vadamdev.viaapi.tools.images.map.MapUtils;
import net.vadamdev.viaapi.tools.images.particle.ParticleImage;
import net.vadamdev.viaapi.tools.images.particle.PixelParticle;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.map.MapView;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author VadamDev
 * @since 22.07.2021
 */
public final class ImageDecoder {
    private static final float PARTICLE_SPACE = 8;

    public static MapPoster decodeToMapPoster(BufferedImage image, World world) {
        int columns = image.getWidth() / 128;
        int rows = image.getHeight() / 128;

        List<Short> mapIds = new ArrayList<>();

        MapView map;
        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < columns; c++) {
                map = Bukkit.createMap(world);
                MapUtils.clearMapRenderers(map);
                map.setScale(MapView.Scale.FARTHEST);
                map.addRenderer(new ImageMapRenderer(image.getSubimage(c * 128, r * 128, 128, 128)));
                mapIds.add(map.getId());
            }
        }

        return new MapPoster(image, mapIds, world);
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
