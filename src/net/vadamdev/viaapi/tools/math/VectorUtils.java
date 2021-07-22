package net.vadamdev.viaapi.tools.math;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class VectorUtils {
    /**
     * @author VadamDev
     * @since 15.07.2021
     */

    public static Vector rotateAroundAxisY(Vector v, double angle) {
        double x, z, cos, sin;
        cos = MathL.cos(angle);
        sin = MathL.sin(angle);
        x = v.getX() * cos + v.getZ() * sin;
        z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }

    public static Location combineVector(Vector vector, Location location) {
        return location.clone().add(vector.getX(), vector.getY(), vector.getZ());
    }
}
