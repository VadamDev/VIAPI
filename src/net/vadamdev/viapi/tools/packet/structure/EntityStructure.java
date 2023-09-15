package net.vadamdev.viapi.tools.packet.structure;

import net.minecraft.server.v1_8_R3.*;
import net.vadamdev.viapi.tools.math.VectorUtils;
import net.vadamdev.viapi.tools.packet.IEntityStructure;
import net.vadamdev.viapi.tools.packet.entities.PacketFallingBlock;
import net.vadamdev.viapi.tools.tuple.MutablePair;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author VadamDev
 * @since 05/09/2023
 */
public class EntityStructure implements IEntityStructure {
    private final List<MutablePair<PacketFallingBlock, Vector>> fallingBlocks;
    private final List<PacketPlayOutEntityDestroy> destroyPackets;

    private Location location;

    public EntityStructure(Location location, Location origin, List<Block> blocks) {
        this.fallingBlocks = blocks.stream()
                .filter(block -> !block.getType().equals(Material.AIR))
                .map(block -> {
                    final Vector transform = block.getLocation().toVector().subtract(origin.toVector());
                    return MutablePair.of(new PacketFallingBlock(location.clone().add(transform), block), transform);
                }).collect(Collectors.toList());

        this.destroyPackets = new ArrayList<>();
        this.fallingBlocks.forEach(pair -> destroyPackets.add(pair.getLeft().getDestroyPacket()));

        this.location = location;
    }

    @Override
    public void spawn(Collection<Player> players) {
        final List<Packet<PacketListenerPlayOut>> packets = new ArrayList<>();
        fallingBlocks.forEach(pair -> packets.addAll(pair.getLeft().createSpawnPackets()));

        for(Player player : players) {
            final PlayerConnection playerConnection = getPlayerConnection(player);
            packets.forEach(playerConnection::sendPacket);
        }
    }

    @Override
    public void delete(Collection<Player> players) {
        for(Player player : players) {
            final PlayerConnection playerConnection = getPlayerConnection(player);
            destroyPackets.forEach(playerConnection::sendPacket);
        }
    }

    @Override
    public void teleportLocal(Location location) {
        fallingBlocks.forEach(pair -> pair.getLeft().teleportLocal(location.clone().add(pair.getRight())));
        this.location = location;
    }

    @Override
    public void teleport(Location location, Collection<Player> players) {
        final List<PacketPlayOutEntityTeleport> packets = new ArrayList<>();
        fallingBlocks.forEach(pair -> packets.add(pair.getLeft().createTeleportPacket(location.clone().add(pair.getRight()))));

        for(Player player : players) {
            final PlayerConnection playerConnection = getPlayerConnection(player);
            packets.forEach(playerConnection::sendPacket);
        }
    }

    @Override
    public void updateLocation(Collection<Player> players) {
        fallingBlocks.forEach(pair -> pair.getLeft().updateLocation(players));
    }

    @Override
    public void rotateAxisXLocal(double angle) {
        fallingBlocks.forEach(pair -> {
            final Vector newRelLoc = VectorUtils.rotateAroundAxisX(pair.getRight(), angle);

            pair.getLeft().teleportLocal(location.clone().add(newRelLoc));
            pair.setRight(newRelLoc);
        });
    }

    @Override
    public void rotateAxisYLocal(double angle) {
        fallingBlocks.forEach(pair -> {
            final Vector newRelLoc = VectorUtils.rotateAroundAxisY(pair.getRight(), angle);

            pair.getLeft().teleportLocal(location.clone().add(newRelLoc));
            pair.setRight(newRelLoc);
        });
    }

    @Override
    public void rotateAxisZLocal(double angle) {
        fallingBlocks.forEach(pair -> {
            final Vector newRelLoc = VectorUtils.rotateAroundAxisZ(pair.getRight(), angle);

            pair.getLeft().teleportLocal(location.clone().add(newRelLoc));
            pair.setRight(newRelLoc);
        });
    }

    @Override
    public void rotateLocal(double angleX, double angleY, double angleZ) {
        fallingBlocks.forEach(pair -> {
            final Vector newRelLoc = VectorUtils.rotateVector(pair.getRight(), angleX, angleY, angleZ);

            pair.getLeft().teleportLocal(location.clone().add(newRelLoc));
            pair.setRight(newRelLoc);
        });
    }

    @Override
    public Location getLocalLocation() {
        return location;
    }

    protected PlayerConnection getPlayerConnection(Player player) {
        return ((CraftPlayer) player).getHandle().playerConnection;
    }
}
