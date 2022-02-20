package net.vadamdev.viaapi.tools.database.hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.vadamdev.viaapi.tools.database.DatabaseCredential;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author VadamDev
 * @since 13.07.2021
 */
public abstract class HikariDatabase {
    private HikariDataSource hikariDataSource;
    private final int maxPoolSize;
    public Connection c;
    public String defaultUsedTab, usedTab, pluginName;

    public HikariDatabase(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
        this.pluginName = "[" + getPluginName() + "]";
    }

    /*
    Connection / Disconnection
     */

    public void connect(DatabaseCredential credential, String defaultUsedTab) {
        connect(credential, new HikariInfo(60000L, 30000L, 0, 10000L), defaultUsedTab);
    }

    public void connect(DatabaseCredential credential, HikariInfo hikariInfo, String defaultUsedTab) {
        setupHikariCP(credential, hikariInfo);
        this.c = getConnection();

        if(isConnected()) {
            if(getPluginName() != null) Bukkit.getConsoleSender().sendMessage(pluginName + " : Successful connected to the Database with Hikari!");
            else Bukkit.getConsoleSender().sendMessage("[VIAPI] §f: Successful connected to the Database with Hikari!");
        }

        this.defaultUsedTab = defaultUsedTab;
        this.usedTab = defaultUsedTab;
    }

    public void disconnect() {
        if(isConnected()) {
            hikariDataSource.close();

            if(getPluginName() != null) Bukkit.getConsoleSender().sendMessage(pluginName + " : Successful disconnected to the Database !");
            else Bukkit.getConsoleSender().sendMessage("[VIAPI] §f: Successful disconnected to the Database !");
        }
    }

    /*
    SQL Default Methods
     */

    public Object get(String objectNameEntry, String objectNameExit, String column) {
        try {
            PreparedStatement q = c.prepareStatement("SELECT " + column + " FROM " + usedTab + " WHERE " + objectNameEntry + " = ?");
            q.setString(1, objectNameExit);

            Object ob = null;
            ResultSet rs = q.executeQuery();

            while(rs.next()) ob = rs.getObject(column);

            q.close();

            return ob;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void set(String objectNameEntry, String objectNameExit, Object object, String column) {
        try {
            PreparedStatement q = c.prepareStatement("UPDATE " + usedTab + " SET " + column + " = ? WHERE " + objectNameEntry + " = ?");
            q.setObject(1, object);
            q.setString(2, objectNameExit);
            q.executeUpdate();
            q.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Object> takeAll(String column) {
        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM " + usedTab);

            List<Object> list = new ArrayList<>();

            while(rs.next()) list.add(rs.getObject(column));

            rs.close();
            s.close();

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
    Abstract Methods & utility methods
     */

    public void useDefaultTab() {
        setUsedTab(defaultUsedTab);
    }

    public void setUsedTab(String usedTab) {
        this.usedTab = usedTab;
    }

    public abstract String getPluginName();

    /*
    Private Methods
     */

    private boolean isConnected() {
        return hikariDataSource != null;
    }

    public Connection getConnection() {
        if(!isConnected()) return null;

        try {
            return hikariDataSource.getConnection();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    private void setupHikariCP(DatabaseCredential credential, HikariInfo hikariInfo) {
        HikariConfig config = new HikariConfig();

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
