package net.vadamdev.viapi.tools.math;

import org.bukkit.util.EulerAngle;

/**
 * @author VadamDev
 * @since 15/07/2021
 */
public class DegreesEulerAngle {
    private final double x, y, z;

    public DegreesEulerAngle(double x, double y, double z) {
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

    public DegreesEulerAngle setX(double x) {
        return new DegreesEulerAngle(Math.toRadians(x), y, z);
    }

    public DegreesEulerAngle setY(double y) {
        return new DegreesEulerAngle(x, Math.toRadians(y), z);
    }

    public DegreesEulerAngle setZ(double z) {
        return new DegreesEulerAngle(x, y, Math.toRadians(z));
    }

    public DegreesEulerAngle add(double x, double y, double z) {
        return new DegreesEulerAngle(this.x + Math.toRadians(x), this.y + Math.toRadians(y), this.z + Math.toRadians(z));
    }

    public DegreesEulerAngle subtract(double x, double y, double z) {
        return this.add(-Math.toRadians(x), -Math.toRadians(y), -Math.toRadians(z));
    }

    public EulerAngle toBukkitEulerAngle() {
        return new EulerAngle(x, y, z);
    }
}
