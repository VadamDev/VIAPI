package net.vadamdev.viaapi.tools.builders;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

public class ArmorStandBuilder {
    /**
     * @author Implements, edited by VadamDev
     * @since 15.07.2021
     */

    private ArmorStand armorStand;
    private Location loc;

    public ArmorStandBuilder(Location loc) {
        this.loc = loc;
        this.armorStand = loc.getWorld().spawn(loc, ArmorStand.class);
    }

    /*
    Equipement
     */

    public ArmorStandBuilder setHelmet(ItemStack items){
        armorStand.setHelmet(items);
        return this;
    }

    public ArmorStandBuilder setChestplate(ItemStack items){
        armorStand.setChestplate(items);
        return this;
    }

    public ArmorStandBuilder setLeggings(ItemStack items){
        armorStand.setLeggings(items);
        return this;
    }

    public ArmorStandBuilder setBoots(ItemStack items){
        armorStand.setBoots(items);
        return this;
    }

    public ArmorStandBuilder setItemInHand(ItemStack items){
        armorStand.setItemInHand(items);
        return this;
    }

    /*
        Parts Poses
     */

    public ArmorStandBuilder setBodyPose(EulerAngle eulerAngle){
        armorStand.setBodyPose(eulerAngle);
        return this;
    }

    public ArmorStandBuilder setLeftArmPose(EulerAngle eulerAngle){
        armorStand.setLeftArmPose(eulerAngle);
        return this;
    }

    public ArmorStandBuilder setRightArmPose(EulerAngle eulerAngle){
        armorStand.setRightArmPose(eulerAngle);
        return this;
    }

    public ArmorStandBuilder setLeftLegPose(EulerAngle eulerAngle){
        armorStand.setLeftLegPose(eulerAngle);
        return this;
    }

    public ArmorStandBuilder setRightLegPose(EulerAngle eulerAngle){
        armorStand.setRightLegPose(eulerAngle);
        return this;
    }

    public ArmorStandBuilder setHeadPose(EulerAngle eulerAngle){
        armorStand.setHeadPose(eulerAngle);
        return this;
    }

    /*
    Setters
     */

    public ArmorStandBuilder setBasePlate(boolean basePlate){
        armorStand.setBasePlate(basePlate);
        return this;
    }

    public ArmorStandBuilder setGravity(boolean gravity){
        armorStand.setGravity(gravity);
        return this;
    }

    public ArmorStandBuilder setVisible(boolean visible){
        armorStand.setVisible(visible);
        return this;
    }

    public ArmorStandBuilder setArms(boolean arms){
        armorStand.setArms(arms);
        return this;
    }

    public ArmorStandBuilder setSmall(boolean small){
        armorStand.setSmall(small);
        return this;
    }

    public ArmorStandBuilder setCustomName(String name){
        armorStand.setCustomName(name);
        armorStand.setCustomNameVisible(true);
        return this;
    }

    public ArmorStandBuilder setLocation(Location loc){
        armorStand.teleport(loc);
        return this;
    }

    /*

     */

    public void delete(){
        armorStand.remove();
    }

    /*
    Getters
     */

    public Location getLoc() {
        return loc;
    }

    public ArmorStand toArmorStandEntity() {
        return armorStand;
    }
}
