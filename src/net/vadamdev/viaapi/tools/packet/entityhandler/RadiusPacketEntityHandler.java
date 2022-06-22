package net.vadamdev.viaapi.tools.packet.entityhandler;

import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author VadamDev
 * @since 20/06/2022
 */
public class RadiusPacketEntityHandler {
    private final IEntityHandler entityHandler;
    private final List<Player> viewers;

    public RadiusPacketEntityHandler(IEntityHandler entityHandler) {
        this.entityHandler = entityHandler;
        this.viewers = new ArrayList<>();
    }

    public RadiusPacketEntityHandler(EntityLiving entity) {
        this(new PacketEntityHandler(entity));
    }

    public void spawnWithRadius(Location center, int radius) {
        Bukkit.getOnlinePlayers().parallelStream()
                .filter(p -> !viewers.contains(p))
                .filter(player -> player.getLocation().distanceSquared(center) <= radius * radius)
                .forEach(player -> {
                    entityHandler.spawn(player);
                    viewers.add(player);
                });
    }

    public void delete() {
        viewers.stream().filter(Player::isOnline).forEach(entityHandler::delete);
        viewers.clear();
    }

    public List<Player> getViewers() {
        return viewers;
    }
}
