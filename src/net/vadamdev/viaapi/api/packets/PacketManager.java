package net.vadamdev.viaapi.api.packets;

import net.minecraft.server.v1_8_R3.Packet;
import net.vadamdev.viaapi.VIAPI;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class PacketManager {
    private Map<Player, PacketReader> packetReaderMap = new HashMap<>();

    public void injectPlayer(Player player) {
        if(VIAPI.get().getConfig().getBoolean("packetreader")) {
            PacketReader reader = new PacketReader(player);
            reader.inject();
            packetReaderMap.put(player, reader);
        }
    }

    public void uninjectPlayer(Player player) {
        if(VIAPI.get().getConfig().getBoolean("packetreader")) {
            packetReaderMap.get(player).uninject();
            packetReaderMap.remove(player);
        }
    }

    public void listenPacket(Player player, PacketType packetType, Consumer<Packet<?>> packet) {
        Packet<?> p = packetReaderMap.get(player).getLastPacket();
        if(p.getClass().getSimpleName().equals(packetType.getName())) packet.accept(p);
    }

    public void listenPacket(Player player, Consumer<Packet<?>> packet) {
        packet.accept(packetReaderMap.get(player).getLastPacket());
    }
}
