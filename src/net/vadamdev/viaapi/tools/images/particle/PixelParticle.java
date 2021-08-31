package net.vadamdev.viaapi.tools.images.particle;

import net.vadamdev.viaapi.tools.math.VectorUtils;
import net.vadamdev.viaapi.tools.particle.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.awt.*;

public class PixelParticle {
    /**
     * @author VadamDev
     * @since 22.07.2021
     */

    private double x, y, width, height;
    private float space;
    private Color color;

    public PixelParticle(double x, double y, int rgb, double width, double height, float space) {
        this.x = x;
        this.y = -y;

        this.color = new Color(rgb);

        this.width = width;
        this.height = height;
        this.space = space;
    }

    @Deprecated
    public void render(Location locBase, double angle) {
        render(locBase, angle, 0, 0, 0);
    }

    public void render(Location locBase, double offsetX, double offsetY, double offsetZ, double angle) {
        Vector v = new Vector(x + -(width / space / 2) + offsetX, y + (height / space / 2) + offsetY, offsetZ);
        v = VectorUtils.rotateAroundAxisY(v, angle);
        ParticleEffect.REDSTONE.display(new ParticleEffect.OrdinaryColor(color.getRed(), color.getGreen(), color.getBlue()), VectorUtils.combineVector(v, locBase));
    }
}