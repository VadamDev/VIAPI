package net.vadamdev.viaapi.tools.database;

public class DatabaseCredential {
    /**
     * @author VadamDev
     * @since 13.07.2021
     */

    private final String host, database, user, password;
    private final int port;

    private boolean autoReconnect;
    private TLSProtocol tlsProtocol;

    public DatabaseCredential(String host, int port, String database, String user, String password) {
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
        this.port = port;

        this.autoReconnect = true;
        this.tlsProtocol = TLSProtocol.DEFAULT;
    }

    public DatabaseCredential setTLSProtocol(TLSProtocol tlsProtocol) {
        this.tlsProtocol = tlsProtocol;
        return this;
    }

    public DatabaseCredential setAutoReconnect(boolean autoReconnect) {
        this.autoReconnect = autoReconnect;
        return this;
    }

    public String toURL() {
        StringBuilder url = new StringBuilder("jdbc:mysql://" + host + ":" + port + "/" + database);

        if(!tlsProtocol.equals(TLSProtocol.DEFAULT)) url.append("?enabledTLSProtocols=" + tlsProtocol.getArgument());
        if(autoReconnect) url.append("?autoReconnect=true");

        return url.toString();
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
