package net.vadamdev.viaapi.tools.database.hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.vadamdev.viaapi.tools.database.DatabaseCredential;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class HikariDatabase {
    /**
     * @author VadamDev
     * @since 13.07.2021
     */

    private HikariDataSource hikariDataSource;
    private int maxPoolSize;
    public Connection c;
    public String usedTab, pluginName;

    public HikariDatabase(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
        this.pluginName = "[" + getPluginName() + "]";
    }

    /*
    Connection / Disconnection
     */

    public void connect(DatabaseCredential credential, String defaultUsedTab) {
        setupHikariCP(credential);
        this.c = getConnection();

        if(isConnected()) {
            if(getPluginName() != null) Bukkit.getConsoleSender().sendMessage(pluginName + " : Successful connected to the Database with Hikari!");
            else Bukkit.getConsoleSender().sendMessage("[VIAPI] §f: Successful connected to the Database with Hikari!");
        }

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

    private void setupHikariCP(DatabaseCredential credential) {
        HikariConfig config = new HikariConfig();

        config.setMaximumPoolSize(maxPoolSize);
        config.setJdbcUrl(credential.toURL());
        config.setUsername(credential.getUser());
        config.setPassword(credential.getPassword());
        config.setMaxLifetime(600000L);
        config.setIdleTimeout(300000L);
        config.setLeakDetectionThreshold(300000L);
        config.setConnectionTimeout(10000L);

        this.hikariDataSource = new HikariDataSource(config);
    }
}
