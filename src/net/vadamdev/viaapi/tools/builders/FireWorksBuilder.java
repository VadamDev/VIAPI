package net.vadamdev.viaapi.tools.builders;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworksBuilder {
    /**
     * @author Implements, Edited By VadamDev
     * @since 12.10.2020
     */

    private final Firework fk;
    private final FireworkEffect.Builder fke;

    public FireworksBuilder(Location loc, FireworkEffect.Type type) {
        this.fk = (Firework) loc.getWorld().spawnEntity(loc , EntityType.FIREWORK);
        this.fke = FireworkEffect.builder().with(type);
    }

    public FireworksBuilder setColor(Color color){
        FireworkMeta fkm = fk.getFireworkMeta();
        fkm.addEffect(fke.withColor(color).build());
        fk.setFireworkMeta(fkm);
        return this;
    }

    public FireworksBuilder setPower(int power){
        FireworkMeta fkm = fk.getFireworkMeta();
        fkm.setPower(power);
        fk.setFireworkMeta(fkm);
        return this;
    }

    public FireworksBuilder setFlicker(boolean flicker){
        FireworkMeta fkm = fk.getFireworkMeta();
        fkm.addEffects(fke.withFlicker().flicker(flicker).build());
        fk.setFireworkMeta(fkm);
        return this;
    }

    public FireworksBuilder setTrail(boolean trail){
        FireworkMeta fkm = fk.getFireworkMeta();
        fkm.addEffects(fke.withTrail().trail(trail).build());
        fk.setFireworkMeta(fkm);
        return this;
    }

    public FireworksBuilder withFade(Color color){
        FireworkMeta fkm = fk.getFireworkMeta();
        fkm.addEffects(fke.withFade(color).build());
        fk.setFireworkMeta(fkm);
        return this;
    }

    public FireworksBuilder withFade(Color... color){
        FireworkMeta fkm = fk.getFireworkMeta();
        fkm.addEffects(fke.withFade(color).build());
        fk.setFireworkMeta(fkm);
        return this;
    }
}
