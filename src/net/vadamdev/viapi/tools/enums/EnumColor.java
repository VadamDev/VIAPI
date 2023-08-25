package net.vadamdev.viapi.tools.enums;

import net.vadamdev.viapi.tools.builders.ItemBuilder;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author VadamDev
 */
public enum EnumColor {
    WHITE(DyeColor.WHITE),
    ORANGE(DyeColor.ORANGE),
    MAGENTA(DyeColor.MAGENTA),
    LIGHT_BLUE(DyeColor.LIGHT_BLUE),
    YELLOW(DyeColor.YELLOW),
    LIME(DyeColor.LIME),
    PINK(DyeColor.PINK),
    GRAY(DyeColor.GRAY),
    SILVER(DyeColor.SILVER),
    CYAN(DyeColor.CYAN),
    PURPLE(DyeColor.PURPLE),
    BLUE(DyeColor.BLUE),
    BROWN(DyeColor.BROWN),
    GREEN(DyeColor.GREEN),
    RED(DyeColor.RED),
    BLACK(DyeColor.BLACK),
    ALL;

    private final DyeColor dyeColor;

    EnumColor() {
        this(DyeColor.WHITE);
    }

    EnumColor(DyeColor dyeColor) {
        this.dyeColor = dyeColor;
    }

    public DyeColor toDyeColor() {
        return dyeColor;
    }

    public short toDyeMeta() {
        return dyeColor.getDyeData();
    }

    public ItemStack createRepresentativeItem() {
        if(equals(ALL))
            return ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzgzZmQzZDExOTUzOWEyNDI1ZjdkYzczMzNkNDJmYWQ2OTRlNjJmNWY0Mzg4MjM1MjQ3MzE5ZDU5NjNkNTY3NyJ9fX0=")
                    .setName("§8» §4M§cu§6l§et§2i§ac§bo§3l§1o§9r")
                    .build();

        return new ItemStack(Material.INK_SACK, 1, toDyeMeta());
    }

    public static EnumColor of(DyeColor dyeColor) {
        for (EnumColor value : values())
            if(value.toDyeColor().equals(dyeColor))
                return value;

        return null;
    }
}
