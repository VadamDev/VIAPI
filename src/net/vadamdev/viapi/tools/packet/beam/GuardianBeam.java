package net.vadamdev.viapi.tools.packet.beam;

import net.minecraft.server.v1_8_R3.*;
import net.vadamdev.viapi.tools.builders.ArmorStandBuilder;
import net.vadamdev.viapi.tools.builders.ArmorStandLocker;
import net.vadamdev.viapi.tools.packet.IPacketEntity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;

/**
 * @author VadamDev
 * @since 03/09/2023
 */
public class GuardianBeam implements IPacketEntity {
    private final EntityGuardian guardian;
    private final EntityArmorStand armorStand;
    private final int guardianId, armorStandId;

    private final PacketPlayOutEntityDestroy destroyPacket;

    private Location startingPosition, endingPosition;

    public GuardianBeam(Location startingPosition, Location endingPosition) {
        this.guardian = new EntityGuardian(((CraftWorld) startingPosition.getWorld()).getHandle());
        this.guardian.setPosition(startingPosition.getX(), startingPosition.getY(), startingPosition.getZ());
        this.guardian.getDataWatcher().watch(0, (byte) 32);
        this.guardian.getDataWatcher().watch(16, 0);

        this.armorStand = ArmorStandBuilder.nms(endingPosition)
                .setAsMarker()
                .lockSlot(new ArmorStandLocker().lockAll())
                .setVisible(false)
                .build();

        this.guardianId = guardian.getId();
        this.armorStandId = armorStand.getId();

        this.guardian.getDataWatcher().watch(17, armorStandId);

        this.destroyPacket = new PacketPlayOutEntityDestroy(guardianId, armorStandId);

        this.startingPosition = startingPosition;
        this.endingPosition = endingPosition;
    }

    @Override
    public void spawn(Collection<Player> players) {
        final List<PacketPlayOutSpawnEntityLiving> packets = Arrays.asList(
                new PacketPlayOutSpawnEntityLiving(guardian),
                new PacketPlayOutSpawnEntityLiving(armorStand)
        );

        for(Player player : players) {
            PlayerConnection playerConnection = getPlayerConnection(player);
            packets.forEach(playerConnection::sendPacket);
        }
    }

    @Override
    public void delete(Collection<Player> players) {
        for (Player player : players)
            getPlayerConnection(player).sendPacket(destroyPacket);
    }

    public void updateStartPositionLocal(Location startingPosition) {
        guardian.setPosition(startingPosition.getX(), startingPosition.getY(), startingPosition.getZ());
        this.startingPosition = startingPosition;
    }

    public void updateStartPosition(Location startingPosition, Collection<Player> players) {
        final PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(guardianId,
                MathHelper.floor(startingPosition.getX() * 32D), MathHelper.floor(startingPosition.getY() * 32D), MathHelper.floor(startingPosition.getZ() * 32D),
                (byte) 0, (byte) 0,
                false);

        for(Player player : players)
            getPlayerConnection(player).sendPacket(packet);
    }

    public void updateStartPosition(Location startingPosition, Player player) {
        updateStartPosition(startingPosition, Collections.singleton(player));
    }

    public void updateEndingPositionLocal(Location endingPosition) {
        armorStand.setPosition(endingPosition.getX(), endingPosition.getY(), endingPosition.getZ());
        this.endingPosition = endingPosition;
    }

    public void updateEndingPosition(Location endingPosition, Collection<Player> players) {
        final PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(armorStandId,
                MathHelper.floor(endingPosition.getX() * 32D), MathHelper.floor(endingPosition.getY() * 32D), MathHelper.floor(endingPosition.getZ() * 32D),
                (byte) 0, (byte) 0,
                false);

        for(Player player : players)
            getPlayerConnection(player).sendPacket(packet);
    }

    public void updateEndingPosition(Location endingPosition, Player player) {
        updateEndingPosition(endingPosition, Collections.singleton(player));
    }

    @Override
    public void teleportLocal(Location location) {
        final Vector transform = endingPosition.toVector().subtract(startingPosition.toVector());

        this.startingPosition = location;
        this.endingPosition = location.clone().add(transform);
    }

    @Override
    public void teleport(Location location, Collection<Player> players) {
        final Location endingPosition = location.clone().add(this.endingPosition.toVector().subtract(this.startingPosition.toVector()));

        final List<PacketPlayOutEntityTeleport> packets = Arrays.asList(
                new PacketPlayOutEntityTeleport(guardianId,
                        MathHelper.floor(location.getX() * 32D), MathHelper.floor(location.getY() * 32D), MathHelper.floor(location.getZ() * 32D),
                        (byte) 0, (byte) 0,
                        false),

                new PacketPlayOutEntityTeleport(armorStandId,
                        MathHelper.floor(endingPosition.getX() * 32D), MathHelper.floor(endingPosition.getY() * 32D), MathHelper.floor(endingPosition.getZ() * 32D),
                        (byte) 0, (byte) 0,
                        false)
        );

        for(Player player : players) {
            final PlayerConnection playerConnection = getPlayerConnection(player);
            packets.forEach(playerConnection::sendPacket);
        }
    }

    @Override
    public Location getLocalLocation() {
        return startingPosition;
    }

    public Location getEndingLocation() {
        return endingPosition;
    }

    protected PlayerConnection getPlayerConnection(Player player) {
        return ((CraftPlayer) player).getHandle().playerConnection;
    }
}
