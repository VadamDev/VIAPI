package net.vadamdev.viapi.tools.packet.handler;

import net.vadamdev.viapi.tools.packet.IPacketEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author VadamDev
 * @since 02/09/2023
 */
public class StaticPacketEntityHandler implements IPacketEntityHandler {
    protected final IPacketEntity packetEntity;
    protected int viewRadius;

    protected final Set<Player> viewers;

    public StaticPacketEntityHandler(IPacketEntity packetEntity, int viewRadius) {
        this.packetEntity = packetEntity;
        this.viewRadius = viewRadius * viewRadius;

        this.viewers = new HashSet<>();
    }

    @Override
    public void spawn() {
        final Set<Player> toAdd = Bukkit.getOnlinePlayers().parallelStream()
                .filter(player -> !isViewing(player))
                .filter(player -> player.getWorld().equals(packetEntity.getLocalLocation().getWorld()))
                .filter(player -> player.getLocation().distanceSquared(packetEntity.getLocalLocation()) <= viewRadius)
                .collect(Collectors.toSet());

        if(toAdd.isEmpty())
            return;

        viewers.addAll(toAdd);
        packetEntity.spawn(toAdd);
    }

    @Override
    public void delete() {
        final Set<Player> toRemove = viewers.parallelStream()
                .filter(Player::isOnline)
                .collect(Collectors.toSet());

        if(!toRemove.isEmpty())
            packetEntity.delete(toRemove);

        viewers.clear();
    }

    @Override
    public boolean isViewing(Player player) {
        return viewers.contains(player);
    }

    @Override
    public Set<Player> getViewers() {
        return viewers;
    }

    @Override
    public Location getLocation() {
        return packetEntity.getLocalLocation();
    }

    public void setViewRadius(int viewRadius) {
        this.viewRadius = viewRadius * viewRadius;
    }
}
