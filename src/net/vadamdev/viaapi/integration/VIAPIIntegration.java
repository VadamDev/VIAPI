package net.vadamdev.viaapi.integration;

import net.vadamdev.viaapi.VIAPI;
import org.bukkit.command.Command;
import org.bukkit.event.Listener;

import java.io.*;
import java.util.logging.Logger;

/**
 * @author VadamDev
 */
public abstract class VIAPIIntegration {
    public void onEnable() {}
    public void onDisable() {}

    public abstract String getName();
    public abstract String getAuthor();
    public abstract String getVersion();

    public void registerListener(Listener listener) {
        VIAPI.get().registerListener(listener);
    }

    public void registerCommand(Command cmd) {
        VIAPI.get().registerCommand(cmd);
    }

    public File getDataFolder() {
        File file = new File(VIAPI.get().getIntegrationManager().getIntegrationsFolder(), getName());
        if(!file.exists()) file.mkdirs();
        return file;
    }

    public void saveResource(Class<?> source, String resourcePath, String outoutpath){
        if(resourcePath == null || resourcePath.isEmpty()) throw new IllegalArgumentException("ResourcePath cannot be null or empty");

        InputStream in = source.getResourceAsStream("/" + resourcePath);
        if(in == null) throw new IllegalArgumentException("The resource `" + resourcePath + "` cannot be found in plugin jar");

        if(!getDataFolder().exists() && !getDataFolder().mkdir()) VIAPI.get().getLogger().warning("Failed to make directory");

        File outFile = new File(getDataFolder(), outoutpath);

        try {
            if(!outFile.exists()){
                VIAPI.get().getLogger().info("The `" + resourcePath + "` was not found, creation in progress...");

                OutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                int n;

                while (((n = in.read(buf)) >= 0)) out.write(buf, 0,n);

                out.close();
                in.close();

                if(!outFile.exists()) System.out.println("Unable to copy file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Logger getLogger() {
        return VIAPI.get().getLogger();
    }
}
