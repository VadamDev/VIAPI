package net.vadamdev.viaapi.tools.enums;

import org.bukkit.block.BlockFace;

public enum EnumDirection {
    /**
     * @author VadamDev
     * @since 09.10.2020
     */

    NORTH,
    EAST,
    SOUTH,
    WEST,
    NORTH_EAST,
    NORTH_WEST,
    SUD_EAST,
    SUD_WEST,
    FLOOR,
    OTHER;

    public static EnumDirection getDirection(float yaw) {
        return values()[Math.round(yaw / 45f) & 0x7];
    }
}
