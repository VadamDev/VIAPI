package net.vadamdev.viaapi.tools.math;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class VectorUtils {
    /**
     * @author VadamDev
     * @since 09.11.2021
     */

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
       Combiner
     */

    public static Location combineVector(Vector vector, Location location) {
        return location.clone().add(vector.getX(), vector.getY(), vector.getZ());
    }

    /*
       Vector Randomizer
     */

    public static Vector randomVector() {
        Vector v = new Vector();
        v.setX(Math.random() - Math.random());
        v.setY(Math.random());
        v.setZ(Math.random() - Math.random());
        return v;
    }

    public static Vector randomVector(float multiply) {
        Vector v = new Vector();
        v.setX(Math.random() - Math.random()).multiply(multiply);
        v.setY(Math.random()).multiply(multiply);
        v.setZ(Math.random() - Math.random()).multiply(multiply);
        return v;
    }
}
