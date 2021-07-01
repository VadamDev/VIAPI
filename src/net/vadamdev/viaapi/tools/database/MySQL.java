package net.vadamdev.viaapi.tools.database;

import org.bukkit.Bukkit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class MySQL {
    /**
     * @author VadamDev
     * @since 12.10.2020
     */

    public Connection c;
    public String usedTab, pluginName;

    public void connect(String host, int port, String database, String user, String password, String usedTab, boolean autoReconnect) {
        this.pluginName = "[" + getPluginName() + "]";

        if(!isConnected()) {
            try {
                c = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database +"?autoReconnect=" + autoReconnect, user, password);

                if(getPluginName() != null) {
                    System.out.println(pluginName + " : Successful connected to the Database !");
                }else {
                    Bukkit.getConsoleSender().sendMessage("[VIAPI] §f: Successful connected to the Database !");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        this.usedTab = usedTab;
    }

    public void connect(String host, int port, String database, String user, String password, String usedTab) {
        connect(host, port, database, user, password, usedTab, false);
    }

    public void connect(DatabaseUser user) { connect(user, false); }

    public void connect(DatabaseUser user, boolean autoReconnect) {
        connect(user.getHost(), user.getPort(), user.getDatabase(), user.getUser(), user.getPassword(), user.getUsedTab(), autoReconnect);
    }

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

    public void trim(String column) {
        try {
            PreparedStatement q = c.prepareStatement("UPDATE " + usedTab + " SET " + column + " = TRIM(CHAR(9) FROM TRIM(0))");
            q.executeUpdate();
            q.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void disconnect() {
        if(isConnected()) {
            try {
                c.close();

                if(getPluginName() != null) {
                    System.out.println(pluginName + " : Deconnection to the database done !");
                }else {
                    Bukkit.getConsoleSender().sendMessage("[VIAPI] §f: Deconnection to the database done !");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract String getPluginName();

    private boolean isConnected() {
        try {
            if(c == null || c.isClosed() || c.isValid(5)) return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
}
