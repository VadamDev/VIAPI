package net.vadamdev.viaapi.tools.math;

import org.bukkit.Location;

import java.util.Random;

public class MathUtils {
    /**
     * @author VadamDev
     * @since 03.11.2020
     */

    public static Location rlap(Location loc, int r, boolean negativeY) { return rlap(loc, r, r, negativeY); }

    public static Location rlap(Location loc, int r, int maxY, boolean negativeY) {
        Random rdm = new Random();

        double x = rdm.nextInt(r);
        double y = rdm.nextInt(maxY);
        double z = Math.sqrt(Math.pow(r, 2) - Math.pow(x, 2));

        if(rdm.nextBoolean()) x = oabs(x);
        if(negativeY && rdm.nextBoolean()) y = oabs(y);
        if(rdm.nextBoolean()) z = oabs(z);

        return loc.clone().add(x, y, z);
    }

    /**
     * @return returns true in percentage percent of cases
     */
    public static boolean percentageLuck(int percentage) {
        float rdm = (MathF.random() * 9) + 1;
        return rdm <= percentage;
    }

    /**
     * @return returns true in percentage percent of cases
     */
    public static boolean percentageLuck(float percentage) {
        float rdm = (MathF.random() * 9) + 0.1f;
        return rdm <= percentage;
    }

    /**
     * @return The opposite of Math.abs
     */
    public static double oabs(double a) {
        return -Math.abs(a);
    }

    /**
     * Transform a fraction in a percentage
     *
     * @return percentage
     */
    public static int percentage(double max, double d) {
        return (int) Math.round(d * 100 / max);
    }
}
