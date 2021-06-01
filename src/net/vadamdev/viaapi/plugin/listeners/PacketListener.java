package net.vadamdev.viaapi.plugin.listeners;

import net.vadamdev.viaapi.tools.packet.Reflection;
import org.bukkit.event.Listener;

public class PacketListener implements Listener {
    /*@EventHandler
    public void onPacketRead(PacketReadEvent event) {
        Packet<?> packet = event.getPacket();
        Player player = event.getPlayer();

        if(packet.getClass().getSimpleName().equalsIgnoreCase(Reflection.PacketType.PLAY_IN_USE_ENTITY.getName())) {
            int id = (int) getValue(packet, "a");

            if(!Constants.npcIds.contains(id)) return;

            if(getValue(packet, "action").toString().equalsIgnoreCase("ATTACK")) {
                Bukkit.getServer().getPluginManager().callEvent(new FakePlayerInterractEvent(id, player, EntityFakePlayer.NPCAction.ATTACK));
            }else if(getValue(packet, "action").toString().equalsIgnoreCase("INTERACT")) {
                Bukkit.getServer().getPluginManager().callEvent(new FakePlayerInterractEvent(id, player, EntityFakePlayer.NPCAction.INTERRACT));
            }
        }
    }
*/
    public static Object getValue(Object instance, String fieldName) {
        try {
            return Reflection.getValue(instance, fieldName);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
