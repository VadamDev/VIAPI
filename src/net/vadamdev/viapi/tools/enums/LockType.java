package net.vadamdev.viapi.tools.enums;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.vadamdev.viapi.tools.utils.Reflection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArmorStand;
import org.bukkit.entity.ArmorStand;

/**
 * @author VadamDev
 *
 * TODO: https://www.reddit.com/r/Minecraft/comments/2czu1p/armorstand_disabledslots_flag_system/
 */
public enum LockType {
    HELMET(1052688),
    CHESTPLATE(526344),
    LEGGINGS(263172),
    BOOTS(131586),
    MELEE(65793),
    ALL(2039583);

    private final int value;

    LockType(int value) {
        this.value = value;
    }

    public void apply(ArmorStand armorStand) {
        apply(((CraftArmorStand) armorStand).getHandle());
    }

    public void apply(EntityArmorStand entityArmorStand) {
        Reflection.setField(Reflection.getField(entityArmorStand.getClass(), "bi"), entityArmorStand, value);
    }
}
