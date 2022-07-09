package net.vadamdev.viaapi.tools.builders;

import net.minecraft.server.v1_8_R3.EntitySkeleton;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

/**
 * @author VadamDev
 * @since 08/07/2022
 */
public class NMSWitherSkeletonBuilder {
    private final EntitySkeleton witherSkeleton;
    private Location loc;

    public NMSWitherSkeletonBuilder(Location loc) {
        this.witherSkeleton = new EntitySkeleton(((CraftWorld) loc.getWorld()).getHandle());
        this.witherSkeleton.setPosition(loc.getX(), loc.getY(), loc.getZ());
        this.witherSkeleton.getDataWatcher().watch(13, (byte) 1);
        this.loc = loc;
    }

    /*
    Equipement
     */

    public NMSWitherSkeletonBuilder setHelmet(ItemStack items) {
        witherSkeleton.setEquipment(4, CraftItemStack.asNMSCopy(items));
        return this;
    }

    public NMSWitherSkeletonBuilder setChestplate(ItemStack items){
        witherSkeleton.setEquipment(3, CraftItemStack.asNMSCopy(items));
        return this;
    }

    public NMSWitherSkeletonBuilder setLeggings(ItemStack items){
        witherSkeleton.setEquipment(2, CraftItemStack.asNMSCopy(items));
        return this;
    }

    public NMSWitherSkeletonBuilder setBoots(ItemStack items){
        witherSkeleton.setEquipment(1, CraftItemStack.asNMSCopy(items));
        return this;
    }

    public NMSWitherSkeletonBuilder setItemInHand(ItemStack items){
        witherSkeleton.setEquipment(0, CraftItemStack.asNMSCopy(items));
        return this;
    }

    /*
    Setters
     */

    public NMSWitherSkeletonBuilder setVisible(boolean visible) {
        witherSkeleton.setInvisible(!visible);
        return this;
    }

    public NMSWitherSkeletonBuilder setCustomName(String name){
        witherSkeleton.setCustomName(name);
        witherSkeleton.setCustomNameVisible(true);
        return this;
    }

    public NMSWitherSkeletonBuilder setCustomNameVisible(boolean b){
        witherSkeleton.setCustomNameVisible(b);
        return this;
    }

    public NMSWitherSkeletonBuilder setLocation(Location loc) {
        witherSkeleton.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        this.loc = loc;
        return this;
    }

    /*
    Getters
     */

    public Location getLoc() {
        return loc;
    }

    public EntitySkeleton toWitherSkeletonEntity() {
        return witherSkeleton;
    }
}
