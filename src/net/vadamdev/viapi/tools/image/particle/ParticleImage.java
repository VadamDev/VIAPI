package net.vadamdev.viapi.tools.image.particle;

import net.vadamdev.viapi.tools.math.VectorUtils;
import net.vadamdev.viapi.tools.particle.ParticleEffect;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * @author VadamDev
 * @since 02/09/2023
 */
public class ParticleImage {
    protected final Color[][] pixels;
    protected final float spacing;

    private final float xOffset, yOffset;

    public ParticleImage(Color[][] pixels, float spacing, int width, int height) {
        this.pixels = pixels;
        this.spacing = spacing;

        this.xOffset = spacing * (width / 2f);
        this.yOffset = spacing * (height / 2f);
    }

    public void render(Location location, float xOffset, float yOffset, float zOffset, double angle) {
        float oX = 0, oY = 0;

        for (final Color[] pixel : pixels) {
            for (final Color color : pixel) {
                if (color != null) {
                    ParticleEffect.REDSTONE.display(
                            new ParticleEffect.OrdinaryColor(color),
                            location.clone().add(VectorUtils.rotateAroundAxisY(new Vector(oX + xOffset - this.xOffset, -oY + yOffset + this.yOffset, zOffset), angle)),
                            32
                    );
                }

                oY += spacing;
            }

            oX += spacing;
            oY = 0;
        }
    }

    public float getSpacing() {
        return spacing;
    }
}
