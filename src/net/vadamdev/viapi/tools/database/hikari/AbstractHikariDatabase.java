package net.vadamdev.viapi.tools.database.hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.vadamdev.viapi.tools.database.DatabaseCredential;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * @author VadamDev
 * @since 13/07/2021
 */
public abstract class AbstractHikariDatabase {
    public static final HikariInfo DEFAULT_INFO = new HikariInfo(60000L, 30000L, 0, 10000L);

    protected final Logger logger;
    private final int maxPoolSize;
    private HikariDataSource hikariDataSource;

    protected Connection connection;

    public AbstractHikariDatabase(Logger logger, int maxPoolSize) {
        this.logger = logger;
        this.maxPoolSize = maxPoolSize;
    }

    /*
       Connect / Disconnect
     */

    public void connect(DatabaseCredential credential, HikariInfo hikariInfo) {
        setupHikari(credential, hikariInfo);
        connection = getConnection();

        if(isConnected())
            logger.info("Successfully connected to the database with Hikari !");
    }

    public void disconnect() {
        if(!isConnected()) {
            logger.warning("Failed to disconnect from the database !");
            return;
        }

        hikariDataSource.close();

        if(hikariDataSource.isClosed())
            logger.info("Successfully disconnected from the database !");
    }

    /*
       Requests
     */

    public void executeUpdate(String query) {
        try {
            final Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public <T> T executeQuery(String query, Function<ResultSet, T> mapper) {
        T object = null;

        try {
            final Statement statement = connection.createStatement();
            final ResultSet result = statement.executeQuery(query);

            object = mapper.apply(result);

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return object;
    }

    public abstract void set(String a, String b, Object object, String column, String table);

    @Nullable
    public abstract Object get(String a, String b, String column, String table);

    public abstract List<Object> takeAll(String column, String table);

    /*
       Utility Methods
     */

    public boolean isConnected() {
        return hikariDataSource != null && !hikariDataSource.isClosed();
    }

    private Connection getConnection() {
        Connection dataSource = null;

        if(isConnected()) {
            try {
                dataSource = hikariDataSource.getConnection();
            }catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return dataSource;
    }

    private void setupHikari(DatabaseCredential credential, HikariInfo hikariInfo) {
        final HikariConfig config = new HikariConfig();

        config.setMaximumPoolSize(maxPoolSize);
        config.setJdbcUrl(credential.toURL());
        config.setUsername(credential.getUser());
        config.setPassword(credential.getPassword());
        config.setMaxLifetime(hikariInfo.getMaxLifeTime());
        config.setIdleTimeout(hikariInfo.getIdleTimeout());
        config.setLeakDetectionThreshold(hikariInfo.getLeakDetectionThreshold());
        config.setConnectionTimeout(hikariInfo.getConnectionTimeout());

        hikariDataSource = new HikariDataSource(config);
    }
}
