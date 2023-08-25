package net.vadamdev.viapi.tools.utils;

/**
 * @author VadamDev
 * @since 25/08/2023
 */
public class TimeDelta {
    private long before, now;

    public TimeDelta() {
        this.before = 0;
        this.now = 0;
    }

    public void start() {
        before = System.nanoTime();
    }

    public void stop() {
        now = System.nanoTime();
    }

    public void quickPrintMs() {
        System.out.println("Took: " + getDeltaMs() + " ms");
    }

    public double getDeltaMs() {
        return getDeltaNs() / 1000000D;
    }

    public double getDeltaNs() {
        final double delta = before - now;

        before = 0;
        now = 0;

        return delta;
    }
}
