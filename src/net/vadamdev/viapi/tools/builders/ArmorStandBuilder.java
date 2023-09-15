package net.vadamdev.viapi.tools.builders;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.Vector3f;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

/**
 * @author VadamDev
 * @since 04/08/2023
 */
public final class ArmorStandBuilder {
    private ArmorStandBuilder() {}

    public static BukkitArmorStandBuilder bukkit(Location location) {
        return new BukkitArmorStandBuilder(location.getWorld().spawn(location, ArmorStand.class));
    }

    public static BukkitArmorStandBuilder bukkit(ArmorStand armorStand) {
        return new BukkitArmorStandBuilder(armorStand);
    }

    public static NMSArmorStandBuilder nms(Location location) {
        EntityArmorStand armorStand = new EntityArmorStand(((CraftWorld) location.getWorld()).getHandle());
        armorStand.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        return new NMSArmorStandBuilder(armorStand);
    }

    public static NMSArmorStandBuilder nms(EntityArmorStand entityArmorStand) {
        return new NMSArmorStandBuilder(entityArmorStand);
    }

    public static final class BukkitArmorStandBuilder {
        private final ArmorStand armorStand;

        private BukkitArmorStandBuilder(ArmorStand armorStand) {
            this.armorStand = armorStand;
        }

        /*
           Equipment
         */

        public BukkitArmorStandBuilder setHelmet(ItemStack itemStack) {
            armorStand.setHelmet(itemStack);
            return this;
        }

        public BukkitArmorStandBuilder setChestplate(ItemStack itemStack) {
            armorStand.setChestplate(itemStack);
            return this;
        }

        public BukkitArmorStandBuilder setLeggings(ItemStack itemStack) {
            armorStand.setLeggings(itemStack);
            return this;
        }

        public BukkitArmorStandBuilder setBoots(ItemStack itemStack) {
            armorStand.setBoots(itemStack);
            return this;
        }

        public BukkitArmorStandBuilder setItemInHand(ItemStack itemStack) {
            armorStand.setItemInHand(itemStack);
            return this;
        }

        /*
           Poses
         */

        public BukkitArmorStandBuilder setBodyPose(EulerAngle eulerAngle) {
            armorStand.setBodyPose(eulerAngle);
            return this;
        }

        public BukkitArmorStandBuilder setLeftArmPose(EulerAngle eulerAngle) {
            armorStand.setLeftArmPose(eulerAngle);
            return this;
        }

        public BukkitArmorStandBuilder setRightArmPose(EulerAngle eulerAngle) {
            armorStand.setRightArmPose(eulerAngle);
            return this;
        }

        public BukkitArmorStandBuilder setLeftLegPose(EulerAngle eulerAngle) {
            armorStand.setLeftLegPose(eulerAngle);
            return this;
        }

        public BukkitArmorStandBuilder setRightLegPose(EulerAngle eulerAngle) {
            armorStand.setRightLegPose(eulerAngle);
            return this;
        }

        public BukkitArmorStandBuilder setHeadPose(EulerAngle eulerAngle) {
            armorStand.setHeadPose(eulerAngle);
            return this;
        }

        public BukkitArmorStandBuilder setRotation(float yaw) {
            Location loc = armorStand.getLocation();
            loc.setYaw(yaw);
            armorStand.teleport(loc);
            return this;
        }

        /*
           Setters
         */

        public BukkitArmorStandBuilder setBasePlate(boolean basePlate) {
            armorStand.setBasePlate(basePlate);
            return this;
        }

        public BukkitArmorStandBuilder setGravity(boolean gravity) {
            armorStand.setGravity(gravity);
            return this;
        }

        public BukkitArmorStandBuilder setVisible(boolean visible) {
            armorStand.setVisible(visible);
            return this;
        }

        public BukkitArmorStandBuilder setArms(boolean arms) {
            armorStand.setArms(arms);
            return this;
        }

        public BukkitArmorStandBuilder setSmall(boolean small) {
            armorStand.setSmall(small);
            return this;
        }

        public BukkitArmorStandBuilder setMarker(boolean marker) {
            armorStand.setMarker(marker);
            return this;
        }

        public BukkitArmorStandBuilder setCustomName(String name) {
            armorStand.setCustomName(name);
            armorStand.setCustomNameVisible(true);
            return this;
        }

        public BukkitArmorStandBuilder setCustomNameVisible(boolean visible) {
            armorStand.setCustomNameVisible(visible);
            return this;
        }

        public BukkitArmorStandBuilder teleport(Location location){
            armorStand.teleport(location);
            return this;
        }

        public BukkitArmorStandBuilder lockSlot(ArmorStandLocker lockType) {
            lockType.apply(armorStand);
            return this;
        }

        public ArmorStand build() {
            return armorStand;
        }
    }

    public static final class NMSArmorStandBuilder {
        private final EntityArmorStand armorStand;

        private NMSArmorStandBuilder(EntityArmorStand armorStand) {
            this.armorStand = armorStand;
        }

        /*
           Equipment
         */

        public NMSArmorStandBuilder setHelmet(ItemStack itemStack) {
            armorStand.setEquipment(4, CraftItemStack.asNMSCopy(itemStack));
            return this;
        }

        public NMSArmorStandBuilder setChestplate(ItemStack itemStack) {
            armorStand.setEquipment(3, CraftItemStack.asNMSCopy(itemStack));
            return this;
        }

        public NMSArmorStandBuilder setLeggings(ItemStack itemStack) {
            armorStand.setEquipment(2, CraftItemStack.asNMSCopy(itemStack));
            return this;
        }

        public NMSArmorStandBuilder setBoots(ItemStack itemStack) {
            armorStand.setEquipment(1, CraftItemStack.asNMSCopy(itemStack));
            return this;
        }

        public NMSArmorStandBuilder setItemInHand(ItemStack itemStack) {
            armorStand.setEquipment(0, CraftItemStack.asNMSCopy(itemStack));
            return this;
        }

        /*
           Poses
         */

        public NMSArmorStandBuilder setBodyPose(Vector3f angle) {
            armorStand.setBodyPose(angle);
            return this;
        }

        public NMSArmorStandBuilder setLeftArmPose(Vector3f angle) {
            armorStand.setLeftArmPose(angle);
            return this;
        }

        public NMSArmorStandBuilder setRightArmPose(Vector3f angle) {
            armorStand.setRightArmPose(angle);
            return this;
        }

        public NMSArmorStandBuilder setLeftLegPose(Vector3f angle) {
            armorStand.setLeftLegPose(angle);
            return this;
        }

        public NMSArmorStandBuilder setRightLegPose(Vector3f angle) {
            armorStand.setRightLegPose(angle);
            return this;
        }

        public NMSArmorStandBuilder setHeadPose(Vector3f angle) {
            armorStand.setHeadPose(angle);
            return this;
        }

        public NMSArmorStandBuilder setRotation(float yaw, float pitch) {
            armorStand.setPositionRotation(armorStand.locX, armorStand.locY, armorStand.locZ, yaw, pitch);
            return this;
        }

        /*
           Setters
         */
        public NMSArmorStandBuilder setBasePlate(boolean basePlate) {
            armorStand.setBasePlate(!basePlate);
            return this;
        }

        public NMSArmorStandBuilder setVisible(boolean visible) {
            armorStand.setInvisible(!visible);
            return this;
        }

        public NMSArmorStandBuilder setArms(boolean arms) {
            armorStand.setArms(arms);
            return this;
        }

        public NMSArmorStandBuilder setSmall(boolean small) {
            armorStand.setSmall(small);
            return this;
        }

        public NMSArmorStandBuilder setAsMarker() {
            final NBTTagCompound tag = armorStand.getNBTTag() != null ? armorStand.getNBTTag() : new NBTTagCompound();

            armorStand.c(tag);
            tag.setInt("Marker", 1);
            armorStand.f(tag);

            return this;
        }

        public NMSArmorStandBuilder setCustomName(String name) {
            armorStand.setCustomName(name);
            armorStand.setCustomNameVisible(true);
            return this;
        }

        public NMSArmorStandBuilder setCustomNameVisible(boolean b) {
            armorStand.setCustomNameVisible(b);
            return this;
        }

        public NMSArmorStandBuilder setLocation(Location loc) {
            armorStand.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
            return this;
        }

        public NMSArmorStandBuilder lockSlot(ArmorStandLocker lockType) {
            lockType.apply(armorStand);
            return this;
        }

        public EntityArmorStand build() {
            return armorStand;
        }
    }
}
