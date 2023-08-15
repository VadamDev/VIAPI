package net.vadamdev.viapi.tools.math;

import org.bukkit.util.Vector;

/**
 * @author VadamDev
 * @since 09/11/2021
 */
public class VectorUtils {
    /*
       Vector Rotation
     */
    public static Vector rotateAroundAxisX(Vector v, double angle) {
        double cos = MathL.cos(angle);
        double sin = MathL.sin(angle);

        double y = v.getY() * cos - v.getZ() * sin;
        double z = v.getY() * sin + v.getZ() * cos;

        return v.setY(y).setZ(z);
    }

    public static Vector rotateAroundAxisY(Vector v, double angle) {
        double cos = MathL.cos(angle);
        double sin = MathL.sin(angle);

        double x = v.getX() * cos + v.getZ() * sin;
        double z = v.getX() * -sin + v.getZ() * cos;

        return v.setX(x).setZ(z);
    }

    public static Vector rotateAroundAxisZ(Vector v, double angle) {
        double cos = MathL.cos(angle);
        double sin = MathL.sin(angle);

        double x = v.getX() * cos - v.getY() * sin;
        double y = v.getX() * sin + v.getY() * cos;

        return v.setX(x).setY(y);
    }

    public static Vector rotateVector(Vector v, double angleX, double angleY, double angleZ) {
        v = rotateAroundAxisX(v, angleX);
        v = rotateAroundAxisY(v, angleY);
        v = rotateAroundAxisZ(v, angleZ);

        return v;
    }

    /*
       Vector Randomizer
     */

    public static Vector randomVector() {
        return new Vector(Math.random() - Math.random(), Math.random(), Math.random() - Math.random());
    }

    public static Vector randomVector(float multiplier) {
        return randomVector().multiply(multiplier);
    }
}
