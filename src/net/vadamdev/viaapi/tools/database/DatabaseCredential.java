package net.vadamdev.viaapi.tools.database;

public class DatabaseCredential {
    /**
     * @author VadamDev
     * @since 13.07.2021
     */

    private String host, database, user, password;
    private int port;

    public DatabaseCredential(String host, int port, String database, String user, String password) {
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
        this.port = port;
    }

    public String toURL() {
        return "jdbc:mysql://" + host + ":" + port + "/" + database;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
