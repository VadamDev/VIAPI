package net.vadamdev.viaapi.tools.images.particle;

import net.vadamdev.viaapi.tools.particle.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.List;

public class PixelParticle {
    /**
     * @author VadamDev
     * @since 22.07.2021
     */

    private int x, y;
    private Color color;

    public PixelParticle(int x, int y, int rgb) {
        this.x = x;
        this.y = -y;

        this.color = new Color(rgb);
    }

    public void render(Location locBase) {
        ParticleEffect.REDSTONE.display(new ParticleEffect.OrdinaryColor(color.getRed(), color.getGreen(), color.getBlue()), locBase.clone().add(x, y, 0), (List<Player>) Bukkit.getOnlinePlayers());
    }
}