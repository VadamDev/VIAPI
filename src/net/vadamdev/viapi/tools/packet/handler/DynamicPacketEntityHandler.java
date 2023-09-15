package net.vadamdev.viapi.tools.packet.handler;

import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.vadamdev.viapi.internal.VIAPIPlugin;
import net.vadamdev.viapi.tools.packet.IEquipmentHolder;
import net.vadamdev.viapi.tools.packet.IPacketEntity;
import net.vadamdev.viapi.tools.packet.entities.GenericPacketEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author VadamDev
 * @since 02/09/2023
 */
public class DynamicPacketEntityHandler implements IPacketEntityHandler {
    protected final IPacketEntity packetEntity;
    private int viewRadius;
    private final int period;

    protected final Set<Player> viewers;

    private Updater updater;

    public DynamicPacketEntityHandler(EntityLiving entity, int viewRadius, int period) {
        this(new GenericPacketEntity(entity), viewRadius, period);
    }

    public DynamicPacketEntityHandler(IPacketEntity packetEntity, int viewRadius, int period) {
        this.packetEntity = packetEntity;
        this.viewRadius = viewRadius * viewRadius;
        this.period = period;

        this.viewers = new HashSet<>();
    }

    @Override
    public void spawn() {
        updater = new Updater(period);
    }

    @Override
    public void delete() {
        updater.cancel();
        updater = null;

        final Set<Player> toRemove = viewers.parallelStream()
                .filter(Player::isOnline)
                .collect(Collectors.toSet());

        if(!toRemove.isEmpty())
            packetEntity.delete(toRemove);

        viewers.clear();
    }

    public void teleport(Location location) {
        packetEntity.teleportLocal(location);
        packetEntity.teleport(location, viewers);
    }

    public void updateEquipment(int slot, ItemStack itemStack) {
        if(!(packetEntity instanceof IEquipmentHolder))
            throw new UnsupportedOperationException("");

        final IEquipmentHolder equipmentHolder = (IEquipmentHolder) packetEntity;
        equipmentHolder.updateLocalEquipment(slot, itemStack);
        equipmentHolder.updateEquipment(slot, itemStack, viewers);
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

    private class Updater extends BukkitRunnable {
        private final Set<Player> toAdd;
        private final Set<Player> toRemove;

        public Updater(int period) {
            this.toAdd = new HashSet<>();
            this.toRemove = new HashSet<>();

            runTaskTimerAsynchronously(VIAPIPlugin.instance, 0, period);
        }

        @Override
        public void run() {
            for(final Player player : Bukkit.getOnlinePlayers()) {
                final boolean closeEnough = isCloseEnough(player);

                if(isViewing(player)) {
                    if(!player.isOnline())
                        viewers.remove(player);
                    else if(!closeEnough)
                        toRemove.add(player);
                }else if(closeEnough)
                    toAdd.add(player);
            }

            if(!toRemove.isEmpty()) {
                viewers.removeAll(toRemove);
                packetEntity.delete(toRemove);
                toRemove.clear();
            }

            if(!toAdd.isEmpty()) {
                viewers.addAll(toAdd);
                packetEntity.spawn(toAdd);
                toAdd.clear();
            }
        }

        private boolean isCloseEnough(Player player) {
            final Location location = packetEntity.getLocalLocation();

            if(!player.getLocation().getWorld().equals(location.getWorld()))
                return false;

            return player.getLocation().distanceSquared(location) <= viewRadius && player.getWorld().equals(location.getWorld());
        }
    }
}
