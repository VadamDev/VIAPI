package net.vadamdev.viaapi.tools.math;

public class EulerAngle {
    /**
     * @author VadamDev
     * @since 15.07.2021
     */

    private final double x;
    private final double y;
    private final double z;

    public EulerAngle(double x, double y, double z) {
        this.x = Math.toRadians(x);
        this.y = Math.toRadians(y);
        this.z = Math.toRadians(z);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public EulerAngle setX(double x) {
        return new EulerAngle(Math.toRadians(x), y, z);
    }

    public EulerAngle setY(double y) {
        return new EulerAngle(x, Math.toRadians(y), z);
    }

    public EulerAngle setZ(double z) {
        return new EulerAngle(x, y, Math.toRadians(z));
    }

    public EulerAngle add(double x, double y, double z) {
        return new EulerAngle(this.x + Math.toRadians(x), this.y + Math.toRadians(y), this.z + Math.toRadians(z));
    }

    public EulerAngle subtract(double x, double y, double z) {
        return this.add(-Math.toRadians(x), -Math.toRadians(y), -Math.toRadians(z));
    }

    public org.bukkit.util.EulerAngle toBukkitEulerAngle() {
        return new org.bukkit.util.EulerAngle(x, y, z);
    }
}
