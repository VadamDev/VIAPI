package net.vadamdev.viapi.tools.packet.entities;

import net.minecraft.server.v1_8_R3.*;
import net.vadamdev.viapi.tools.builders.ArmorStandBuilder;
import net.vadamdev.viapi.tools.builders.ArmorStandLocker;
import net.vadamdev.viapi.tools.packet.IPacketEntity;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author VadamDev
 * @since 02/09/2023
 */
public class PacketFloatingItem implements IPacketEntity {
    private final EntityItem entityItem;
    private final EntityArmorStand armorStand;
    private final int itemId, armorStandId;

    private final PacketPlayOutEntityDestroy destroyPacket;

    private Location location;

    public PacketFloatingItem(Location location, ItemStack itemStack) {
        this.entityItem = new EntityItem(((CraftWorld) location.getWorld()).getHandle());
        this.entityItem.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        this.entityItem.setItemStack(CraftItemStack.asNMSCopy(itemStack != null ? itemStack : new ItemStack(Material.STONE)));

        this.armorStand = ArmorStandBuilder.nms(location)
                .setSmall(true)
                .setAsMarker()
                .setBasePlate(false)
                .setVisible(false)
                .build();
        new ArmorStandLocker().lockAll().apply(this.armorStand);

        this.itemId = entityItem.getId();
        this.armorStandId = armorStand.getId();

        this.destroyPacket = new PacketPlayOutEntityDestroy(itemId, armorStandId);

        this.location = location;
    }

    @Override
    public void spawn(Collection<Player> players) {
        final List<Packet<PacketListenerPlayOut>> packets = new ArrayList<>();

        packets.add(new PacketPlayOutSpawnEntityLiving(armorStand));
        packets.add(new PacketPlayOutSpawnEntity(entityItem, 2));
        packets.add(new PacketPlayOutEntityMetadata(itemId, entityItem.getDataWatcher(), true));

        packets.add(new PacketPlayOutAttachEntity(0, entityItem, armorStand));

        for (Player player : players) {
            final PlayerConnection playerConnection = getPlayerConnection(player);
            packets.forEach(playerConnection::sendPacket);
        }
    }

    @Override
    public void delete(Collection<Player> players) {
        for (Player player : players)
            getPlayerConnection(player).sendPacket(destroyPacket);
    }

    @Override
    public void teleportLocal(Location location) {
        armorStand.setPosition(location.getX(), location.getY(), location.getZ());
        this.location = location;
    }

    @Override
    public void teleport(Location location, Collection<Player> players) {
        final PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(armorStandId,
                MathHelper.floor(location.getX() * 32D), MathHelper.floor(location.getY() * 32D), MathHelper.floor(location.getZ() * 32D), (byte) 0, (byte) 0,
                false);

        for(Player player : players)
            getPlayerConnection(player).sendPacket(packet);
    }

    @Override
    public Location getLocalLocation() {
        return location;
    }

    protected PlayerConnection getPlayerConnection(Player player) {
        return ((CraftPlayer) player).getHandle().playerConnection;
    }
}
