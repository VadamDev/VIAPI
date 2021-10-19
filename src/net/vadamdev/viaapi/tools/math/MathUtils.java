package net.vadamdev.viaapi.tools.math;

import org.bukkit.Location;

import java.util.Random;

public class MathUtils {
    /**
     * @author VadamDev
     * @since 03.11.2020
     */

    public static int percentage(double max, double d) {
        return (int) Math.round(d * 100 / max);
    }

    public static Location rlap(Location loc, int r, int maxY, boolean negativeY) {
        Random rdm = new Random();

        double x = rdm.nextInt(r);
        double y = rdm.nextInt(maxY);
        double z = Math.sqrt(Math.pow(r, 2) - Math.pow(x, 2));

        if(rdm.nextBoolean()) oabs(x);
        if(negativeY && rdm.nextBoolean()) oabs(y);
        if(rdm.nextBoolean()) oabs(z);

        Location nLoc = loc.clone();
        nLoc.setX(nLoc.getX() + x);
        nLoc.setY(nLoc.getY() + y);
        nLoc.setZ(nLoc.getZ() + z);

        return nLoc;
    }

    public static boolean percentageLuck(int percentage) {
        float rdm = (MathF.random() * (10 - 1)) + 1;
        return rdm <= percentage;
    }

    public static boolean percentageLuck(float percentage) {
        float rdm = (MathF.random() * (10 - 1)) + 1;
        return rdm <= percentage;
    }

    public static Location rlap(Location loc, int r, boolean negativeY) { return rlap(loc, r, r, negativeY); }

    public static double oabs(double a) { return (a < 0) ? a : -a; }
}
