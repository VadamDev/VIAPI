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
    SOUTH_EAST,
    SOUTH_WEST,
    FLOOR;

    public static EnumDirection getDirection(float yaw) {
        double rotation = yaw - 180;
        if (rotation < 0) rotation += 360.0;

        if (22.5 <= rotation && rotation < 67.5)
            return EnumDirection.NORTH_EAST;
        else if (67.5 <= rotation && rotation < 112.5)
            return EnumDirection.EAST;
        else if (112.5 <= rotation && rotation < 157.5)
            return EnumDirection.SOUTH_EAST;
        else if (157.5 <= rotation && rotation < 202.5)
            return EnumDirection.SOUTH;
        else if (202.5 <= rotation && rotation < 247.5)
            return EnumDirection.SOUTH_WEST;
        else if (247.5 <= rotation && rotation < 292.5)
            return EnumDirection.WEST;
        else if (292.5 <= rotation && rotation < 337.5)
            return EnumDirection.NORTH_WEST;
        else return EnumDirection.NORTH;
    }
}
