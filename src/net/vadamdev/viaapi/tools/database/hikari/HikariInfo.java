package net.vadamdev.viaapi.tools.database.hikari;

public class HikariInfo {
    /**
     * @author VadamDev
     * @since 26.08.2021
     */

    private long maxLifeTime, idleTimeout, leakDetectionThreshold, connectionTimeout;

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
