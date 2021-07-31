package net.vadamdev.viaapi.tools.math;

import net.vadamdev.viaapi.tools.enums.EnumDirection;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class VectorUtils {
    /**
     * @author VadamDev
     * @since 15.07.2021
     */

    public static Vector rotateAroundAxisY(Vector v, double angle) {
        double cos = MathL.cos(angle);
        double sin = MathL.sin(angle);

        double x = v.getX() * cos + v.getZ() * sin;
        double z = v.getX() * -sin + v.getZ() * cos;

        return v.setX(x).setZ(z);
    }

    public static Location combineVector(Vector vector, Location location) {
        return location.clone().add(vector.getX(), vector.getY(), vector.getZ());
    }

    public static Vector randomVector() {
        Vector v = new Vector();
        v.setX(Math.random() - Math.random());
        v.setY(Math.random());
        v.setZ(Math.random() - Math.random());
        return v;
    }

    public static Vector randomVector(int multiply) {
        Vector v = new Vector();
        v.setX(Math.random() - Math.random()).multiply(multiply);
        v.setY(Math.random()).multiply(multiply);
        v.setZ(Math.random() - Math.random()).multiply(multiply);
        return v;
    }
}
