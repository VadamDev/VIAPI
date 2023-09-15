package net.vadamdev.viapi.tools.builders;

import net.minecraft.server.v1_8_R3.EntitySkeleton;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

/**
 * @author VadamDev
 * @since 04/08/2023
 */
public class NMSWitherSkeletonBuilder {
    private final EntitySkeleton witherSkeleton;

    public NMSWitherSkeletonBuilder(Location loc) {
        this.witherSkeleton = new EntitySkeleton(((CraftWorld) loc.getWorld()).getHandle());
        this.witherSkeleton.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        this.witherSkeleton.getDataWatcher().watch(13, (byte) 1);
    }

    /*
       Equipement
     */

    public NMSWitherSkeletonBuilder setHelmet(ItemStack itemStack) {
        witherSkeleton.setEquipment(4, CraftItemStack.asNMSCopy(itemStack));
        return this;
    }

    public NMSWitherSkeletonBuilder setChestplate(ItemStack itemStack){
        witherSkeleton.setEquipment(3, CraftItemStack.asNMSCopy(itemStack));
        return this;
    }

    public NMSWitherSkeletonBuilder setLeggings(ItemStack itemStack){
        witherSkeleton.setEquipment(2, CraftItemStack.asNMSCopy(itemStack));
        return this;
    }

    public NMSWitherSkeletonBuilder setBoots(ItemStack itemStack){
        witherSkeleton.setEquipment(1, CraftItemStack.asNMSCopy(itemStack));
        return this;
    }

    public NMSWitherSkeletonBuilder setItemInHand(ItemStack itemStack){
        witherSkeleton.setEquipment(0, CraftItemStack.asNMSCopy(itemStack));
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
        return this;
    }

    /*
       Getters
     */

    public EntitySkeleton build() {
        return witherSkeleton;
    }
}
