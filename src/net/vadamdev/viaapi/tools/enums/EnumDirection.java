package net.vadamdev.viaapi.tools.enums;

/**
 * @author VadamDev
 * @since 09.10.2020
 */
public enum EnumDirection {
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

    @Deprecated
    public static EnumDirection getDirection(float yaw) {
        return values()[Math.round(yaw / 45f) & 0x7];
    }
}
