package net.vadamdev.viapi.tools.builders;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.vadamdev.viapi.tools.enums.EnumPart;
import net.vadamdev.viapi.tools.enums.LockType;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArmorStand;
import org.bukkit.entity.ArmorStand;

import java.lang.reflect.Field;

/**
 * @author VadamDev
 * @since 24/08/2023
 */
public class ArmorStandLocker {
    private static final ArmorStandLocker ALL_LOCKED = new AllLockedArmorStandLocker();
    public static ArmorStandLocker lockAll() {
        return ALL_LOCKED;
    }

    private int value;

    public ArmorStandLocker(int value) {
        this.value = value;
    }

    public ArmorStandLocker() {
        this(0);
    }

    public ArmorStandLocker addParameter(EnumPart part, LockType... lockTypes) {
        if(value == 2039583)
            throw new UnsupportedOperationException("Can't add parameter when everything is already locked");

        final int partIndex = getPartIndex(part);

        int tempValue = 0;
        for (LockType lockType : lockTypes) {
            if(lockType.equals(LockType.ALL)) {
                tempValue = (1 << partIndex) + (1 << (partIndex + 8)) + (1 << (partIndex + 16));
                break;
            }

            tempValue += (1 << (partIndex + lockType.getValue()));
        }

        value += tempValue;

        return this;
    }

    public void apply(EntityArmorStand armorStand) {
        try {
            final Field bi = armorStand.getClass().getDeclaredField("bi");
            bi.setAccessible(true);
            bi.set(armorStand, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void apply(ArmorStand armorStand) {
        apply(((CraftArmorStand) armorStand).getHandle());
    }

    private int getPartIndex(EnumPart part) {
        switch(part) {
            case BOOTS:
                return 1;
            case LEGGINGS:
                return 2;
            case CHESTPLATE:
                return 3;
            case HELMET:
                return 4;
            default:
                return 0;
        }
    }

    private static final class AllLockedArmorStandLocker extends ArmorStandLocker {
        private AllLockedArmorStandLocker() {
            super(2039583);
        }

        @Override
        public ArmorStandLocker addParameter(EnumPart part, LockType... lockTypes) {
            throw new UnsupportedOperationException("Can't add parameter when everything is already locked");
        }
    }
}
