package net.vadamdev.viaapi.tools.enums;

/**
 * @author VadamDev
 * @since 09.10.2020
 */
public enum EnumDirection {
    NORTH(-180),
    EAST(-90),
    SOUTH(0),
    WEST(90),
    NORTH_EAST,
    NORTH_WEST,
    SOUTH_EAST,
    SOUTH_WEST,
    FLOOR;

    private final float yaw;

    EnumDirection(float yaw) {
        this.yaw = yaw;
    }

    EnumDirection() {
        this.yaw = 0;
    }

    public EnumDirection getOpposite() {
        switch(this) {
            case NORTH:
                return EnumDirection.SOUTH;
            case EAST:
                return EnumDirection.WEST;
            case SOUTH:
                return EnumDirection.NORTH;
            case WEST:
                return EnumDirection.EAST;
            default:
                return NORTH;
        }
    }

    public float getYaw() {
        return yaw;
    }

    public static EnumDirection getCardinalDirection(float yaw) {
        double rotation = (yaw - 180) % 360;
        if (rotation < 0) rotation += 360.0;

        if (0 <= rotation && rotation < 67.5)
            return EnumDirection.NORTH;
        else if (67.5 <= rotation && rotation < 157.5)
            return EnumDirection.EAST;
        else if (157.5 <= rotation && rotation < 247.5)
            return EnumDirection.SOUTH;
        else if (247.5 <= rotation && rotation < 337.5)
            return EnumDirection.WEST;
        else if (337.5 <= rotation && rotation < 360.0)
            return EnumDirection.NORTH;

        return null;
    }

    public static EnumDirection getPreciseDirection(float yaw) {
        double rot = yaw - 180;
        if (rot < 0) rot += 360.0;

        if (22.5 <= rot && rot < 67.5)
            return EnumDirection.NORTH_EAST;
        else if (67.5 <= rot && rot < 112.5)
            return EnumDirection.EAST;
        else if (112.5 <= rot && rot < 157.5)
            return EnumDirection.SOUTH_EAST;
        else if (157.5 <= rot && rot < 202.5)
            return EnumDirection.SOUTH;
        else if (202.5 <= rot && rot < 247.5)
            return EnumDirection.SOUTH_WEST;
        else if (247.5 <= rot && rot < 292.5)
            return EnumDirection.WEST;
        else if (292.5 <= rot && rot < 337.5)
            return EnumDirection.NORTH_WEST;
        else
            return EnumDirection.NORTH;
    }
}
