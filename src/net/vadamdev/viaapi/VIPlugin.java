package net.vadamdev.viaapi;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class VIPlugin extends JavaPlugin {
    /**
     * @author VadamDev
     * @since 09.10.2020
     */

    public void registerCommand(Command cmd) { MinecraftServer.getServer().server.getCommandMap().register(cmd.getName(), this.getName(), cmd); }

    public void saveResource(String resourcePath, String outoutpath){
        if(resourcePath == null || resourcePath.isEmpty()) throw new IllegalArgumentException("ResourcePath cannot be null or empty");

        InputStream in = getResource(resourcePath);
        if(in == null) throw new IllegalArgumentException("The resource `" + resourcePath + "` cannot be found in plugin jar");

        if(!getDataFolder().exists() && !getDataFolder().mkdir()) getLogger().severe("Faild to make directory");

        File outFile = new File(getDataFolder(), outoutpath);

        try {
            if(!outFile.exists()){
                getLogger().info("The `" + resourcePath + "` was not found, creation in progress...");

                OutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                int n;

                while (((n = in.read(buf)) >= 0)) out.write(buf, 0,n);

                out.close();
                in.close();

                if(!outFile.exists()){
                    getLogger().severe("Unable to copy file.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
