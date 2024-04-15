package net.vadamdev.viapi.tools.raycast;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author VadamDev
 * @since 29/01/2024
 */
public final class Raycast {
    private final float precision;
    private final double maxRange;
    private final boolean withBlocks, withEntities;

    private final Predicate<Block> blockFilter;
    private final Predicate<Entity> entityFilter;

    private final Function<Location, RaycastResult> custom;

    private Raycast(float precision, double maxRange, boolean withBlocks, boolean withEntities, Predicate<Block> blockFilter, Predicate<Entity> entityFilter, Function<Location, RaycastResult> custom) {
        this.precision = precision;
        this.maxRange = maxRange;
        this.withBlocks = withBlocks;
        this.withEntities = withEntities;
        this.blockFilter = blockFilter;
        this.entityFilter = entityFilter;

        this.custom = custom != null ? custom : (location -> null);
    }

    public RaycastResult cast(Location loc, Vector dir) {
        final Vector vector = loc.toVector();
        final Vector magic = vector.clone().add(dir.clone().multiply(maxRange)).subtract(vector).normalize().multiply(precision);

        final World world = loc.getWorld();

        for(double length = 0; length < maxRange; length += precision) {
            final Location newLoc = vector.toLocation(world);

            final RaycastResult customResult = custom.apply(newLoc);
            if(customResult != null)
                return customResult;

            if(withBlocks) {
                final Block block = newLoc.getBlock();

                if(blockFilter.test(block))
                    return new RaycastResult(newLoc, block, null);
            }

            if(withEntities) {
                final List<Entity> entities = world.getNearbyEntities(newLoc, 0.5, 0.5, 0.5).stream()
                        .filter(entityFilter)
                        .collect(Collectors.toList());

                if(!entities.isEmpty())
                    return new RaycastResult(newLoc, null, entities);
            }

            vector.add(magic);
        }

        return new RaycastResult(null, null, null);
    }

    public RaycastResult cast(LivingEntity entity) {
        return cast(entity.getEyeLocation(), entity.getEyeLocation().getDirection());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private float precision;
        private double maxRange;
        private boolean withBlocks, withEntities;

        private Predicate<Block> blockFilter;
        private Predicate<Entity> entityFilter;

        private Function<Location, RaycastResult> custom;

        private Builder() {
            this.precision = 0.25f;
            this.maxRange = 8;
        }

        public Builder withBlocks(Predicate<Block> filter) {
            this.withBlocks = true;
            this.blockFilter = filter;

            return this;
        }

        public Builder withBlocks() {
            return withBlocks(block -> !block.getType().equals(Material.AIR));
        }

        public Builder withEntities(Predicate<Entity> filter) {
            this.withEntities = true;
            this.entityFilter = filter;

            return this;
        }

        public Builder withEntities() {
            return withEntities(entity -> true);
        }

        public Builder custom(Function<Location, RaycastResult> custom) {
            this.custom = custom;
            return this;
        }

        public Builder maxRange(double maxRange) {
            this.maxRange = maxRange;
            return this;
        }

        public Builder precision(float precision) {
            this.precision = precision;
            return this;
        }

        public Raycast build() {
            if(!withBlocks && !withEntities && custom == null)
                throw new UnsupportedOperationException();

            return new Raycast(precision, maxRange, withBlocks, withEntities, blockFilter, entityFilter, custom);
        }
    }
}
