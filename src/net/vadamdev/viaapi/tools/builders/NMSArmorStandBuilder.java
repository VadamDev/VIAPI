package net.vadamdev.viaapi.tools.builders;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.Vector3f;
import net.vadamdev.viaapi.tools.enums.LockType;
import net.vadamdev.viaapi.tools.packet.Reflection;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;

/**
 * @author VadamDev
 * @since 09/04/2022
 */
public class NMSArmorStandBuilder {
    private final EntityArmorStand armorStand;
    private Location loc;

    public NMSArmorStandBuilder(Location loc) {
        this.armorStand = new EntityArmorStand(((CraftWorld) loc.getWorld()).getHandle());
        this.loc = loc;
    }

    /*
    Equipement
     */

    public NMSArmorStandBuilder setHelmet(ItemStack items) {
        armorStand.setEquipment(1, CraftItemStack.asNMSCopy(items));
        return this;
    }

    public NMSArmorStandBuilder setChestplate(ItemStack items){
        armorStand.setEquipment(2, CraftItemStack.asNMSCopy(items));
        return this;
    }

    public NMSArmorStandBuilder setLeggings(ItemStack items){
        armorStand.setEquipment(3, CraftItemStack.asNMSCopy(items));
        return this;
    }

    public NMSArmorStandBuilder setBoots(ItemStack items){
        armorStand.setEquipment(4, CraftItemStack.asNMSCopy(items));
        return this;
    }

    public NMSArmorStandBuilder setItemInHand(ItemStack items){
        armorStand.setEquipment(0, CraftItemStack.asNMSCopy(items));
        return this;
    }

    /*
        Parts Poses
     */

    public NMSArmorStandBuilder setBodyPose(Vector3f angle){
        armorStand.setBodyPose(angle);
        return this;
    }

    public NMSArmorStandBuilder setLeftArmPose(Vector3f angle){
        armorStand.setLeftArmPose(angle);
        return this;
    }

    public NMSArmorStandBuilder setRightArmPose(Vector3f angle){
        armorStand.setRightArmPose(angle);
        return this;
    }

    public NMSArmorStandBuilder setLeftLegPose(Vector3f angle){
        armorStand.setLeftLegPose(angle);
        return this;
    }

    public NMSArmorStandBuilder setRightLegPose(Vector3f angle){
        armorStand.setRightLegPose(angle);
        return this;
    }

    public NMSArmorStandBuilder setHeadPose(Vector3f angle){
        armorStand.setHeadPose(angle);
        return this;
    }

    public NMSArmorStandBuilder setRotation(float yaw, float pitch) {
        try {
            Reflection.getMethod(armorStand.getClass().getSuperclass().getSuperclass(), "setYawPitch", Float.class, Float.class).invoke(armorStand, yaw, pitch);
        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return this;
    }

    /*
    Setters
     */
    public NMSArmorStandBuilder setBasePlate(boolean basePlate){
        armorStand.setBasePlate(basePlate);
        return this;
    }

    public NMSArmorStandBuilder setGravity(boolean gravity){
        armorStand.setGravity(gravity);
        return this;
    }

    public NMSArmorStandBuilder setVisible(boolean visible) {
        armorStand.setInvisible(!visible);
        return this;
    }

    public NMSArmorStandBuilder setArms(boolean arms){
        armorStand.setArms(arms);
        return this;
    }

    public NMSArmorStandBuilder setSmall(boolean small) {
        armorStand.setSmall(small);
        return this;
    }

    public NMSArmorStandBuilder setCustomName(String name){
        armorStand.setCustomName(name);
        armorStand.setCustomNameVisible(true);
        return this;
    }

    public NMSArmorStandBuilder setCustomNameVisible(boolean b){
        armorStand.setCustomNameVisible(b);
        return this;
    }

    public NMSArmorStandBuilder setLocation(Location loc) {
        armorStand.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        this.loc = loc;
        return this;
    }

    public NMSArmorStandBuilder lockSlot(LockType lockType) {
        lockType.setToArmorStand(armorStand);
        return this;
    }

    /*
    Getters
     */

    public Location getLoc() {
        return loc;
    }

    public EntityArmorStand toArmorStandEntity() {
        return armorStand;
    }
}
