package net.vadamdev.viapi.tools.math;

import org.bukkit.Location;

import java.util.Random;

/**
 * @author VadamDev
 * @since 03/11/2020
 */
public class MathUtils {
    private static final Random random = new Random();

    /**
     * Generate a random location in a circle
     * @param center of the circle
     * @param radius of the circle
     * @return
     */
    public static Location rlap(Location center, double radius) {
        double t = 2 * Math.PI * Math.random();
        double r = Math.sqrt(Math.random()) * radius;
        return center.clone().add(r * Math.cos(t), 0, r * Math.sin(t));
    }

    /**
     * Generate a random location on a ring
     * @param center of the ring
     * @param radius of the ring
     * @return
     */
    public static Location rlop(Location center, double radius) {
        double t = 2 * Math.PI * Math.random();
        double r = Math.sqrt(radius);
        return center.clone().add(r * Math.cos(t), 0, r * Math.sin(t));
    }

    /**
     * @return returns true in percentage percent of cases
     */
    public static boolean percentageLuck(int percentage) {
        return random.nextInt(101) <= percentage;
    }

    /**
     * Transform a fraction in a percentage
     * @return percentage
     */
    public static int percentage(double max, double d) {
        return (int) Math.round(d * 100 / max);
    }
}
