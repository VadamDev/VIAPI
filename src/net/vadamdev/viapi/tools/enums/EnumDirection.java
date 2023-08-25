package net.vadamdev.viapi.tools.enums;

/**
 * @author VadamDev
 * @since 04/08/2022
 */
public enum EnumDirection {
    UP,
    NORTH(-180),
    EAST(-90),
    SOUTH(0),
    WEST(90),
    NORTH_EAST(0),
    NORTH_WEST(0),
    SOUTH_EAST(0),
    SOUTH_WEST(0),
    DOWN;

    private final float yaw;

    EnumDirection() {
        this(0);
    }

    EnumDirection(float yaw) {
        this.yaw = yaw;
    }

    public EnumDirection getOpposite() {
        switch(this) {
            case UP:
                return DOWN;
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            case EAST:
                return WEST;
            case WEST:
                return EAST;
            case NORTH_EAST:
                return SOUTH_WEST;
            case NORTH_WEST:
                return SOUTH_EAST;
            case SOUTH_EAST:
                return NORTH_WEST;
            case SOUTH_WEST:
                return NORTH_EAST;
            case DOWN:
                return UP;
            default:
                return null;
        }
    }

    public float getYaw() {
        return yaw;
    }

    public static EnumDirection getCardinalDirection(float yaw) {
        double rot = (yaw - 180) % 360;

        if (rot < 0)
            rot += 360;

        if (0 <= rot && rot < 67.5)
            return EnumDirection.NORTH;
        else if (67.5 <= rot && rot < 157.5)
            return EnumDirection.EAST;
        else if (157.5 <= rot && rot < 247.5)
            return EnumDirection.SOUTH;
        else if (247.5 <= rot && rot < 337.5)
            return EnumDirection.WEST;
        else if (337.5 <= rot && rot < 360.0)
            return EnumDirection.NORTH;

        return null;
    }

    public static EnumDirection getPreciseDirection(float yaw) {
        double rot = yaw - 180;

        if (rot < 0)
            rot += 360;

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
