package net.vadamdev.viaapi.tools.database.mysql;

import net.vadamdev.viaapi.tools.database.DatabaseCredential;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public abstract class MySQL {
    /**
     * @author VadamDev
     * @since 12.10.2020
     */

    public Connection c;
    public String usedTab, pluginName;

    public MySQL() {
        this.pluginName = "[" + getPluginName() + "]";
    }

    /*
    Connection / Disconnection
     */

    public void connect(DatabaseCredential credential, String usedTab, boolean autoReconnect) {
        if(!isConnected()) {
            try {
                c = DriverManager.getConnection(credential.toURL() + "?autoReconnect=" + autoReconnect, credential.getUser(), credential.getPassword());

                if(getPluginName() != null) Bukkit.getConsoleSender().sendMessage(pluginName + " : Successful connected to the Database with DriverManager!");
                else Bukkit.getConsoleSender().sendMessage("[VIAPI] §f: Successful connected to the Database with DriverManager !");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            this.usedTab = usedTab;
        }
    }

    public void disconnect() {
        if(isConnected()) {
            try {
                c.close();

                if(getPluginName() != null) Bukkit.getConsoleSender().sendMessage(pluginName + " : Successful disconnected to the Database !");
                else Bukkit.getConsoleSender().sendMessage("[VIAPI] §f: Successful disconnected to the Database !");
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
        try {
            return !(c == null || c.isClosed());
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
