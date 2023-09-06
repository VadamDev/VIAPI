package net.vadamdev.viapi.tools.packet.entities;

import net.minecraft.server.v1_8_R3.*;
import net.vadamdev.viapi.tools.packet.IEquipmentHolder;
import net.vadamdev.viapi.tools.packet.IPacketEntity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author VadamDev
 * @since 02/09/2023
 */
public class GenericPacketEntity implements IPacketEntity, IEquipmentHolder {
    protected final EntityLiving entity;
    protected final int entityId;
    protected final boolean displayArmor;

    private final PacketPlayOutEntityDestroy destroyPacket;

    protected Location location;

    public GenericPacketEntity(EntityLiving entity) {
        this(entity, true);
    }

    public GenericPacketEntity(EntityLiving entity, boolean displayArmor) {
        this.entity = entity;
        this.entityId = entity.getId();
        this.displayArmor = displayArmor;

        this.destroyPacket = new PacketPlayOutEntityDestroy(entityId);

        this.location = entity.getBukkitEntity().getLocation();
    }

    @Override
    public void spawn(Collection<Player> players) {
        final List<Packet<PacketListenerPlayOut>> packets = new ArrayList<>();

        packets.add(new PacketPlayOutSpawnEntityLiving(entity));
        packets.add(new PacketPlayOutEntityHeadRotation(entity, (byte) ((location.getYaw() * 256f) / 360f)));

        if(displayArmor) {
            for(int i = 0; i < entity.getEquipment().length; i++) {
                if(entity.getEquipment()[i] == null)
                    continue;

                packets.add(new PacketPlayOutEntityEquipment(entityId, i, entity.getEquipment()[i]));
            }
        }

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
        entity.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        this.location = location;
    }

    @Override
    public void teleport(Location location, Collection<Player> players) {
        final byte yaw = (byte) ((int) (location.getYaw() * 256.0F / 360.0F));
        final byte pitch = (byte) ((int) (location.getPitch() * 256.0F / 360.0F));

        final PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(entityId,
                MathHelper.floor(location.getX() * 32D), MathHelper.floor(location.getY() * 32D), MathHelper.floor(location.getZ() * 32D),
                yaw, pitch,
                false);

        for(Player player : players)
            getPlayerConnection(player).sendPacket(packet);
    }

    @Override
    public void updateLocalEquipment(int slot, ItemStack itemStack) {
        entity.setEquipment(slot, itemStack);
    }

    @Override
    public void updateEquipment(int slot, ItemStack itemStack, Collection<Player> players) {
        final PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(entityId, slot, itemStack);

        for (Player player : players)
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
