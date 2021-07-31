package net.vadamdev.viaapi.tools.enums;

import org.bukkit.DyeColor;

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

    public static EnumColor of(DyeColor dyeColor) {
        for (EnumColor value : values()) if(value.toDyeColor().equals(dyeColor)) return value;
        return null;
    }
}
