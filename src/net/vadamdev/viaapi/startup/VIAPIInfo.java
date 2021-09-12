package net.vadamdev.viaapi.startup;

public class VIAPIInfo {
    /**
     * @author VadamDev
     * @since 13.09.2021
     */

    private APIVersion apiVersion;
    private String pluginName;

    public VIAPIInfo(APIVersion apiVersion, String pluginName) {
        this.apiVersion = apiVersion;
        this.pluginName = pluginName;
    }

    public APIVersion getApiVersion() {
        return apiVersion;
    }

    public String getPluginName() {
        return pluginName;
    }
}
