package net.vadamdev.viaapi.tools.packet;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Titles {
    /**
     * @author VadamDev
     * @since 22.12.2020
     */

    public static void sendTitle(Player player, String title, String subtitle, int ticks){
        IChatBaseComponent basetitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
        IChatBaseComponent basesubtitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");

        PacketPlayOutTitle titlepacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, basetitle);
        PacketPlayOutTitle subtitlepacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, basesubtitle);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(titlepacket);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(subtitlepacket);

        sendTime(player, ticks);
    }

    public static void sendTitle(Player player, String title, int ticks){
        IChatBaseComponent basetitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");

        PacketPlayOutTitle titlepacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, basetitle);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(titlepacket);

        sendTime(player, ticks);
    }

    public static void sendActionBar(Player player, String message) {
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc,(byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(ppoc);
    }

    private static void sendTime(Player player, int ticks){
        PacketPlayOutTitle titlepacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, 20, ticks, 20);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(titlepacket);
    }
}
