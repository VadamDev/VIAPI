package net.vadamdev.viapi.tools.utils;

import net.vadamdev.viapi.internal.VIAPIPlugin;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

public final class BungeeUtils {
    private BungeeUtils() {}

    public static void registerOutgoingPluginChannel(JavaPlugin plugin, String channel) {
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, channel);
    }

    public static void registerIncomingPluginChannel(JavaPlugin plugin, String channel, PluginMessageListener messageListener) {
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, channel, messageListener);
    }

    public static void sendPlayer(String serverName, Player player) {
        final ByteArrayOutputStream b = new ByteArrayOutputStream();
        final DataOutput out = new DataOutputStream(b);

        try {
            out.writeUTF("Connect");
            out.writeUTF(serverName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        player.sendPluginMessage(VIAPIPlugin.instance, "BungeeCord", b.toByteArray());
    }

    public static void kickPlayer(Player player, String s) {
        final ByteArrayOutputStream b = new ByteArrayOutputStream();
        final DataOutput out = new DataOutputStream(b);

        try {
            out.writeUTF("KickPlayer");
            out.writeUTF(player.getName());
            out.writeUTF(s);
        } catch (IOException e) {
            e.printStackTrace();
        }

        player.sendPluginMessage(VIAPIPlugin.instance, "BungeeCord", b.toByteArray());
    }
}
