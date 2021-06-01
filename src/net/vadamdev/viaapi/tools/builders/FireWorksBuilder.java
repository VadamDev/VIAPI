package net.vadamdev.viaapi.tools.builders;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireWorksBuilder {
    /**
     * @author Implements
     * @since 12.10.2020
     */

    private Firework fk;
    private Location loc;
    private FireworkEffect.Builder fke;

    public FireWorksBuilder(Location loc, FireworkEffect.Type type) {
        this.loc = loc;
        this.fk = (Firework) loc.getWorld().spawnEntity(loc , EntityType.FIREWORK);
        this.fke = FireworkEffect.builder();
        this.fke = fke.with(type);
    }

    public FireWorksBuilder setColor(Color color){
        FireworkMeta fkm = fk.getFireworkMeta();
        fkm.addEffect(fke.withColor(color).build());
        fk.setFireworkMeta(fkm);
        return this;
    }

    public FireWorksBuilder setPower(int power){
        FireworkMeta fkm = fk.getFireworkMeta();
        fkm.setPower(power);
        fk.setFireworkMeta(fkm);
        return this;
    }

    public FireWorksBuilder setFlicker(boolean flicker){
        FireworkMeta fkm = fk.getFireworkMeta();
        fkm.addEffects(fke.withFlicker().flicker(flicker).build());
        fk.setFireworkMeta(fkm);
        return this;
    }

    public FireWorksBuilder setTrail(boolean trail){
        FireworkMeta fkm = fk.getFireworkMeta();
        fkm.addEffects(fke.withTrail().trail(trail).build());
        fk.setFireworkMeta(fkm);
        return this;
    }

    public FireWorksBuilder withFade(Color color){
        FireworkMeta fkm = fk.getFireworkMeta();
        fkm.addEffects(fke.withFade(color).build());
        fk.setFireworkMeta(fkm);
        return this;
    }

    public FireWorksBuilder withFade(Color... color){
        FireworkMeta fkm = fk.getFireworkMeta();
        fkm.addEffects(fke.withFade(color).build());
        fk.setFireworkMeta(fkm);
        return this;
    }
}
