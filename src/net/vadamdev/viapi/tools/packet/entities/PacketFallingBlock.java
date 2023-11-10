package net.vadamdev.viapi.tools.packet.entities;

import net.minecraft.server.v1_8_R3.*;
import net.vadamdev.viapi.tools.builders.ArmorStandBuilder;
import net.vadamdev.viapi.tools.builders.ArmorStandLocker;
import net.vadamdev.viapi.tools.packet.IPacketEntity;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author VadamDev
 * @since 02/09/2023
 */
public class PacketFallingBlock implements IPacketEntity {
    private final EntityFallingBlock fallingBlock;
    private final EntityArmorStand armorStand;
    private final int armorStandId;

    private final PacketPlayOutEntityDestroy destroyPacket;

    private final int blockId;
    private final byte blockData;

    private Location location;

    public PacketFallingBlock(Location location, Block block) {
        this(location, block.getTypeId(), block.getData());
    }

    public PacketFallingBlock(Location location, int blockId, byte blockData) {
        this.fallingBlock = new EntityFallingBlock(((CraftWorld) location.getWorld()).getHandle(), location.getX(), location.getY(), location.getZ(), null);
        this.armorStand = ArmorStandBuilder.nms(location)
                .setAsMarker()
                .setSmall(true)
                .setVisible(false)
                .setBasePlate(false)
                .setRotation(0, 0)
                .build();
        new ArmorStandLocker().lockAll().apply(this.armorStand);

        this.armorStandId = armorStand.getId();

        this.destroyPacket = new PacketPlayOutEntityDestroy(fallingBlock.getId(), armorStandId);

        this.blockId = blockId;
        this.blockData = blockData;

        this.location = location;
    }

    @Override
    public void spawn(Collection<Player> players) {
        final List<Packet<PacketListenerPlayOut>> packets = createSpawnPackets();

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
        final PacketPlayOutEntityTeleport packet = createTeleportPacket(location);

        for (Player player : players)
            getPlayerConnection(player).sendPacket(packet);
    }

    public PacketPlayOutEntityDestroy getDestroyPacket() {
        return destroyPacket;
    }

    @Override
    public Location getLocalLocation() {
        return location;
    }

    public List<Packet<PacketListenerPlayOut>> createSpawnPackets() {
        return Arrays.asList(
                new PacketPlayOutSpawnEntity(fallingBlock, 70, blockId | (blockData << 0xC)),
                new PacketPlayOutSpawnEntityLiving(armorStand),
                new PacketPlayOutAttachEntity(0, fallingBlock, armorStand)
        );
    }

    public PacketPlayOutEntityTeleport createTeleportPacket(Location location) {
        return new PacketPlayOutEntityTeleport(armorStandId,
                MathHelper.floor(location.getX() * 32D), MathHelper.floor(location.getY() * 32D), MathHelper.floor(location.getZ() * 32D),
                (byte) 0, (byte) 0, false);
    }

    protected PlayerConnection getPlayerConnection(Player player) {
        return ((CraftPlayer) player).getHandle().playerConnection;
    }
}
