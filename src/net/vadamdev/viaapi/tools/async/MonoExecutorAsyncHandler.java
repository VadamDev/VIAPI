package net.vadamdev.viaapi.tools.async;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author VadamDev
 * @since 07/03/2022
 */
public class MonoExecutorAsyncHandler {
    private final ScheduledExecutorService executorService;

    public MonoExecutorAsyncHandler(int threadPoolSize) {
        executorService = Executors.newScheduledThreadPool(threadPoolSize);
    }

    public void scheduleAtFixedRate(Runnable runnable, int period, TimeUnit timeUnit) {
        executorService.scheduleAtFixedRate(runnable, period, period, timeUnit);
    }

    public void scheduleWithFixedDelay(Runnable runnable, int period, TimeUnit timeUnit) {
        executorService.scheduleWithFixedDelay(runnable, period, period, timeUnit);
    }

    public void executeAfter(Runnable runnable, int delay, TimeUnit timeUnit) {
        executorService.schedule(runnable, delay, timeUnit);
    }

    public void execute(Runnable runnable) {
        executorService.execute(runnable);
    }

    public void stopService() {
        executorService.shutdownNow();
    }
}
