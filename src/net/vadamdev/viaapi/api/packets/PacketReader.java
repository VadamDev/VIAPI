package net.vadamdev.viaapi.api.packets;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class PacketReader {
    /**
     * @author VadamDev
     * @since 21.12.2020
     */

    public Player player;
    public Channel channel;

    private Packet<?> lastPacket;

    public PacketReader(Player player) {
        this.player = player;
    }

    public void inject() {
        EntityPlayer craftPlayer = ((CraftPlayer) player).getHandle();
        channel = craftPlayer.playerConnection.networkManager.channel;

        channel.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<Packet<?>>() {
            @Override
            protected void decode(ChannelHandlerContext channelHandlerContext, Packet<?> packet, List<Object> list) {
                list.add(packet);
                lastPacket = packet;
            }
        });
    }

    public void uninject() {
        if(channel.pipeline().get("PacketInjector") != null) channel.pipeline().remove("PacketInjector");
    }

    public Packet<?> getLastPacket() {
        return lastPacket;
    }
}
