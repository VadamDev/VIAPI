package net.vadamdev.viaapi.tools.database.hikari;

/**
 * @author VadamDev
 * @since 26.08.2021
 */
public class HikariInfo {
    private final long maxLifeTime, idleTimeout, leakDetectionThreshold, connectionTimeout;

    public HikariInfo(long maxLifeTime, long idleTimeout, long leakDetectionThreshold, long connectionTimeout) {
        this.maxLifeTime = maxLifeTime;
        this.idleTimeout = idleTimeout;
        this.leakDetectionThreshold = leakDetectionThreshold;
        this.connectionTimeout = connectionTimeout;
    }

    public long getMaxLifeTime() {
        return maxLifeTime;
    }

    public long getIdleTimeout() {
        return idleTimeout;
    }

    public long getLeakDetectionThreshold() {
        return leakDetectionThreshold;
    }

    public long getConnectionTimeout() {
        return connectionTimeout;
    }
}
