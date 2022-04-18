package net.vadamdev.viaapi.tools.images.particle;

import net.vadamdev.viaapi.tools.math.VectorUtils;
import net.vadamdev.viaapi.tools.particle.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.awt.*;

/**
 * @author VadamDev
 * @since 22.07.2021
 */
public class PixelParticle {
    private final double x, y;
    private final Color color;

    public PixelParticle(double x, double y, int rgb) {
        this(x, y, new Color(rgb));
    }

    public PixelParticle(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void render(Location locBase, double offsetX, double offsetY, double offsetZ, double angle) {
        ParticleEffect.REDSTONE.display(new ParticleEffect.OrdinaryColor(color.getRed(), color.getGreen(), color.getBlue()), VectorUtils.combineVector(VectorUtils.rotateAroundAxisY(new Vector(x + offsetX, y + offsetY, offsetZ), angle), locBase), 32);
    }
}