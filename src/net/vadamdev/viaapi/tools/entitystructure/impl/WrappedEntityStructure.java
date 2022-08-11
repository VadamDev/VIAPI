package net.vadamdev.viaapi.tools.entitystructure.impl;

import net.minecraft.server.v1_8_R3.*;
import net.vadamdev.viaapi.tools.entitystructure.EntityStructure;
import net.vadamdev.viaapi.tools.entitystructure.IEntityStructure;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * @author VadamDev
 * @since 10/08/2022
 */
public class WrappedEntityStructure implements IEntityStructure {
    private final EntityStructure entityStructure;

    private final List<PacketPlayOutEntityDestroy> deletePackets;

    public WrappedEntityStructure(EntityStructure entityStructure) {
        this.entityStructure = entityStructure;

        this.deletePackets = entityStructure.createDeletePacket();
    }

    public WrappedEntityStructure(Location origin, Location location, List<Block> blocks) {
        this(new EntityStructure(origin, location, blocks));
    }

    @Override
    public void spawn(Player... players) {
        List<Packet<PacketListenerPlayOut>> spawnPackets = entityStructure.createSpawnPackets();

        for (Player player : players) {
            PlayerConnection playerConnection = getPlayerConnection(player);
            spawnPackets.forEach(playerConnection::sendPacket);
        }
    }

    @Override
    public void delete(Player... players) {
        for (Player player : players) {
            PlayerConnection playerConnection = getPlayerConnection(player);
            deletePackets.forEach(playerConnection::sendPacket);
        }
    }

    @Override
    public void updateLocalPosition(Location location) {
        entityStructure.updateLocalPosition(location);
    }

    @Override
    public void updateLocationAndRotation(Player... players) {
        List<PacketPlayOutEntityTeleport> teleportPackets = entityStructure.createTeleportPackets();

        for (Player player : players) {
            PlayerConnection playerConnection = getPlayerConnection(player);
            teleportPackets.forEach(playerConnection::sendPacket);
        }
    }

    @Override
    public void updateLocalRotationAroundAxisX(double angle) {
        entityStructure.rotateAroundAxisX(angle);
    }

    @Override
    public void updateLocalRotationAroundAxisY(double angle) {
        entityStructure.rotateAroundAxisY(angle);
    }

    @Override
    public void updateLocalRotationAroundAxisZ(double angle) {
        entityStructure.rotateAroundAxisZ(angle);
    }

    @Override
    public void updateLocalRotation(double angleX, double angleY, double angleZ) {
        entityStructure.rotate(angleX, angleY, angleZ);
    }

    @Override
    public Location getLocation() {
        return entityStructure.getLocation();
    }

    private PlayerConnection getPlayerConnection(Player player) {
        return ((CraftPlayer) player).getHandle().playerConnection;
    }
}
