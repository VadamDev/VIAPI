package net.vadamdev.viaapi.tools.database;

public class DatabaseUser {
    /**
     * @author VadamDev
     * @since 11.10.2020
     */

    private String host, database, user, password, usedTab;
    private int port;

    public DatabaseUser(String host, int port, String database, String user, String password, String usedTab) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
        this.usedTab = usedTab;
    }

    public String getHost() {
        return host;
    }

    public String getDatabase() {
        return database;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getUsedTab() {
        return usedTab;
    }

    public int getPort() {
        return port;
    }
}
