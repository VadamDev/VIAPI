package net.vadamdev.viaapi.tools.images.particle;

import net.vadamdev.viaapi.tools.enums.EnumDirection;
import net.vadamdev.viaapi.tools.math.VectorUtils;
import net.vadamdev.viaapi.tools.particle.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.awt.*;
import java.util.List;

public class PixelParticle {
    /**
     * @author VadamDev
     * @since 22.07.2021
     */

    private double x, y;
    private Color color;

    public PixelParticle(double x, double y, int rgb) {
        this.x = x;
        this.y = -y;

        this.color = new Color(rgb);
    }

    public void render(Location locBase) {
        if (Bukkit.getOnlinePlayers().isEmpty()) return;

        Vector v = new Vector(x, y, 0);

        ParticleEffect.REDSTONE.display(new ParticleEffect.OrdinaryColor(color.getRed(), color.getGreen(), color.getBlue()), VectorUtils.combineVector(v, locBase), (List<Player>) Bukkit.getOnlinePlayers());
    }
}