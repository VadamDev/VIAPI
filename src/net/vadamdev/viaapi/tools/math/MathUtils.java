package net.vadamdev.viaapi.tools.math;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Random;

public class MathUtils {
    /**
     * @author VadamDev
     * @since 03.11.2020
     */

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

    public static int percentage(double max, double d) {
        return (int) Math.round(d * 100 / max);
    }

    public static Location rlap(Location loc, int r, int maxY, boolean negativeY) {
        Random rdm = new Random();

        double x = rdm.nextInt(r);
        double y = rdm.nextInt(maxY);
        double z = Math.sqrt(Math.pow(r, 2) - Math.pow(x, 2));

        if(rdm.nextBoolean()) oabs(x);
        if(rdm.nextBoolean() && negativeY) oabs(y);
        if(rdm.nextBoolean()) oabs(z);

        Location nLoc = loc.clone();
        nLoc.setX(nLoc.getX() + x);
        nLoc.setY(nLoc.getY() + y);
        nLoc.setZ(nLoc.getZ() + z);

        return nLoc;
    }

    public static boolean percentageLuck(int percentage) {
        float rdm = (MathF.random() * (10 - 1)) + 1;
        return rdm < percentage + 1;
    }

    public static boolean percentageLuck(float percentage) {
        float rdm = (MathF.random() * (10 - 1)) + 1;
        return rdm < percentage + 0.1;
    }

    public static Location rlap(Location loc, int r, boolean negativeY) { return rlap(loc, r, r, negativeY); }

    public static double oabs(double a) { return (a < 0) ? a : -a; }
}
