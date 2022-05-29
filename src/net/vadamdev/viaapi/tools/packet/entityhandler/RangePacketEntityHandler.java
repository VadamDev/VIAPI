package net.vadamdev.viaapi.tools.packet.entityhandler;

import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.vadamdev.viaapi.VIAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author VadamDev
 * @since 10/05/2022
 */
public final class RangePacketEntityHandler extends PacketEntityHandler {
    private final int viewingRadius, updateDelay;

    private final List<Player> viewers;

    private PacketEntityHandlerUpdater updater;

    public RangePacketEntityHandler(EntityLiving entity, int viewingRadius, int updateDelay) {
        super(entity);
        this.viewingRadius = viewingRadius * viewingRadius;
        this.updateDelay = updateDelay;

        this.viewers = new ArrayList<>();
    }

    public void spawn() {
        updater = new PacketEntityHandlerUpdater(updateDelay);
    }

    public void delete() {
        updater.cancel();

        viewers.parallelStream()
                .filter(Player::isOnline)
                .forEach(this::delete);

        viewers.clear();
    }

    public void updateEquipmentForViewers(int slot, ItemStack itemStack) {
        updateLocalEquipment(slot, itemStack);
        viewers.forEach(player -> updateEquipment(player, slot, itemStack));
    }

    public void teleportForViewers(Location location) {
        updateLocalPosition(location);
        viewers.forEach(p -> teleport(p, location));
    }

    public void addViewer(Player player) {
        viewers.add(player);
    }

    public void deleteViewer(Player player) {
        viewers.remove(player);
    }

    public boolean isViewing(Player player) {
        return viewers.contains(player);
    }

    private boolean isCloseEnough(Player player) {
        return player.getLocation().distanceSquared(entityLocation) <= viewingRadius && player.getWorld().equals(entityLocation.getWorld());
    }

    private class PacketEntityHandlerUpdater extends BukkitRunnable {
        public PacketEntityHandlerUpdater(int period) {
            runTaskTimerAsynchronously(VIAPI.get(), 0, period);
        }

        @Override
        public void run() {
            new ArrayList<>(viewers).parallelStream()
                    .filter(player -> !player.isOnline())
                    .forEach(RangePacketEntityHandler.this::deleteViewer);

            Bukkit.getOnlinePlayers().parallelStream()
                    .filter(p -> !RangePacketEntityHandler.this.isViewing(p))
                    .filter(RangePacketEntityHandler.this::isCloseEnough)
                    .forEach(player -> {
                        spawn(player);
                        addViewer(player);
                    });

            Bukkit.getOnlinePlayers().parallelStream()
                    .filter(RangePacketEntityHandler.this::isViewing)
                    .filter(p -> !RangePacketEntityHandler.this.isCloseEnough(p))
                    .forEach(player -> {
                        delete(player);
                        deleteViewer(player);
                    });
        }
    }
}
