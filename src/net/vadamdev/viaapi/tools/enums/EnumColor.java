package net.vadamdev.viaapi.tools.enums;

import net.vadamdev.viaapi.tools.builders.ItemBuilder;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

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
    ALL(DyeColor.WHITE);

    private DyeColor dyeColor;

    EnumColor(DyeColor dyeColor) {
        this.dyeColor = dyeColor;
    }

    public DyeColor toDyeColor() {
        return dyeColor;
    }

    public short toDyeMeta() {
        return dyeColor.getDyeData();
    }

    public ItemStack getItem() {
        if(this.equals(ALL)) return ItemBuilder.setCustomTextureHead("§8» §4M§cu§6l§et§2i§ac§bo§3l§1o§9r", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzgzZmQzZDExOTUzOWEyNDI1ZjdkYzczMzNkNDJmYWQ2OTRlNjJmNWY0Mzg4MjM1MjQ3MzE5ZDU5NjNkNTY3NyJ9fX0=");
        return new ItemBuilder(Material.INK_SACK, 1, toDyeMeta()).toItemStack();
    }

    public static EnumColor of(DyeColor dyeColor) {
        for (EnumColor value : values()) if(value.toDyeColor().equals(dyeColor)) return value;
        return null;
    }
}
