package net.vadamdev.viapi.tools.database;

/**
 * @author Vadamdev
 * @since 25/01/22
 */
public enum TLSProtocol {
    DEFAULT,
    TLSv1_1("TLSv1.1"),
    TLSv1_2("TLSv1.2"),
    TLSv1_3("TLSv1.3");

    private final String argument;

    TLSProtocol() {
        this.argument = "";
    }

    TLSProtocol(String version) {
        this.argument = "?enabledTLSProtocols=" + version;
    }

    public String getArgument() {
        return argument;
    }
}
