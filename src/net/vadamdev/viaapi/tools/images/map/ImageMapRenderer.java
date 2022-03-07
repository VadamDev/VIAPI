package net.vadamdev.viaapi.tools.images.map;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.awt.image.BufferedImage;

/**
 * @author VadamDev
 * @since 25.01.22
 */
public final class ImageMapRenderer extends MapRenderer {
    private boolean shouldRender;
    private final BufferedImage image;

    public ImageMapRenderer(BufferedImage image) {
        this.image = image;
        this.shouldRender = true;
    }

    @Override
    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
        if(shouldRender) {
            mapCanvas.drawImage(0, 0, image);
            shouldRender = false;
        }
    }
}
