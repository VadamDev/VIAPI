package net.vadamdev.viapi.tools.builders;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftFirework;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

/**
 * @author Implements & VadamDev
 * @since 12/10/2020
 */
public class FireworksBuilder {
    private final Firework firework;
    private final FireworkEffect.Builder builder;

    public FireworksBuilder(Location loc, FireworkEffect.Type type) {
        this.firework = (Firework) loc.getWorld().spawnEntity(loc , EntityType.FIREWORK);
        this.builder = FireworkEffect.builder().with(type);
    }

    public FireworksBuilder setColor(Color color) {
        FireworkMeta fkm = firework.getFireworkMeta();
        fkm.addEffect(builder.withColor(color).build());
        firework.setFireworkMeta(fkm);
        return this;
    }

    public FireworksBuilder setPower(int power) {
        FireworkMeta fkm = firework.getFireworkMeta();
        fkm.setPower(power);
        firework.setFireworkMeta(fkm);
        return this;
    }

    public FireworksBuilder setFlicker(boolean flicker) {
        FireworkMeta fkm = firework.getFireworkMeta();
        fkm.addEffects(builder.withFlicker().flicker(flicker).build());
        firework.setFireworkMeta(fkm);
        return this;
    }

    public FireworksBuilder setTrail(boolean trail) {
        FireworkMeta fkm = firework.getFireworkMeta();
        fkm.addEffects(builder.withTrail().trail(trail).build());
        firework.setFireworkMeta(fkm);
        return this;
    }

    public FireworksBuilder withFade(Color color) {
        FireworkMeta fkm = firework.getFireworkMeta();
        fkm.addEffects(builder.withFade(color).build());
        firework.setFireworkMeta(fkm);
        return this;
    }

    public FireworksBuilder withFade(Color... color) {
        FireworkMeta fkm = firework.getFireworkMeta();
        fkm.addEffects(builder.withFade(color).build());
        firework.setFireworkMeta(fkm);
        return this;
    }

    public void detonate() {
        ((CraftFirework) firework).getHandle().expectedLifespan = 1;
    }

    public Firework build() {
        return firework;
    }
}
