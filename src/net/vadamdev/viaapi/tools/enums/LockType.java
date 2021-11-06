package net.vadamdev.viaapi.tools.enums;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.vadamdev.viaapi.tools.packet.Reflection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArmorStand;
import org.bukkit.entity.ArmorStand;

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

    public void setToArmorStand(ArmorStand armorStand) {
        EntityArmorStand entityArmorStand = ((CraftArmorStand) armorStand).getHandle();
        Reflection.setField(Reflection.getField(entityArmorStand.getClass(), "bi"), entityArmorStand, value);
    }
}
