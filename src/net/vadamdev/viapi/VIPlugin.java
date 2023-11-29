package net.vadamdev.viapi;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.command.Command;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

/**
 * @author VadamDev & Estxbxn
 * @since 05/08/2023
 */
public class VIPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        final APIVersion apiVersion = getAPIVersion();
        VIAPI.Provider.get().getDependsMap().put(getName(), apiVersion);

        if(!apiVersion.isLatest() && !apiVersion.equals(APIVersion.UNKNOWN))
            getLogger().warning("I'm using the " + apiVersion.name() + " of the VIAPI which is not the latest version !");
        else if(apiVersion.isLatest())
            getLogger().info("I'm using the latest version of the VIAPI ! (" + apiVersion.name() + ")");
        else
            getLogger().warning("I'm using an UNKNOWN version of the VIAPI ! The plugin authors should specify what version they're using by overriding the getAPIVersion method");
    }

    public void registerCommand(Command command) {
        MinecraftServer.getServer().server.getCommandMap().register(command.getName(), getName(), command);
    }

    public void registerCommands(Command... commands) {
        for (Command command : commands)
            registerCommand(command);
    }

    public void registerListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    public void registerListeners(Listener... listeners) {
        for (Listener listener : listeners)
            registerListener(listener);
    }

    public void saveResource(String ioPath) {
        saveResource(ioPath, ioPath);
    }

    public void saveResource(String resourcePath, String outputPath){
        if(resourcePath == null || resourcePath.isEmpty())
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");

        final InputStream in = getResource(resourcePath);
        if(in == null)
            throw new IllegalArgumentException("The resource " + resourcePath + " cannot be found in plugin jar");

        final File dataFolder = getDataFolder();
        if(!dataFolder.exists() && !dataFolder.mkdirs()) {
            getLogger().severe("Failed to create plugin's data folder");
            return;
        }

        try {
            final File outFile = new File(dataFolder, outputPath);

            if(!outFile.exists()){
                getLogger().info("The " + resourcePath + " was not found, creation in progress...");

                OutputStream out = new FileOutputStream(outFile);

                byte[] buf = new byte[1024];
                int length;
                while ((length = in.read(buf)) != -1)
                    out.write(buf, 0, length);

                in.close();
                out.close();

                if(!outFile.exists())
                    getLogger().severe("Unable to copy the file.");
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public APIVersion getAPIVersion() {
        return APIVersion.UNKNOWN;
    }
}
