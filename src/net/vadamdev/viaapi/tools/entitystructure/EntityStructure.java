package net.vadamdev.viaapi.tools.entitystructure;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import net.vadamdev.viaapi.tools.math.VectorUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author VadamDev
 * @since 06/08/2022
 */
public class EntityStructure {
    protected Location location;
    private final Map<EntityBlock, Vector> parts;

    public EntityStructure(Location origin, Location location, List<Block> blocks) {
        this.location = location;
        this.parts = new HashMap<>();

        blocks.forEach(block -> {
            if(block.getType().equals(Material.AIR))
                return;

            Vector relativeLoc = block.getLocation().clone().subtract(origin).toVector();
            parts.put(new EntityBlock(location.clone().add(relativeLoc), block), relativeLoc);
        });
    }

    /*
       Spawn / Delete
     */

    public List<Packet<PacketListenerPlayOut>> createSpawnPackets() {
        List<Packet<PacketListenerPlayOut>> spawnPackets = new ArrayList<>();
        parts.keySet().forEach(entityBlock -> spawnPackets.addAll(entityBlock.getSpawnPackets()));
        return spawnPackets;
    }

    public List<PacketPlayOutEntityDestroy> createDeletePacket() {
        List<PacketPlayOutEntityDestroy> packets = new ArrayList<>();
        parts.keySet().forEach(entityBlock -> packets.add(entityBlock.getDeletePacket()));
        return packets;
    }

    /*
       Teleport
     */

    public void updateLocalPosition(Location location) {
        this.location = location;
        new HashMap<>(parts).forEach(((entityBlock, relativeLoc) -> entityBlock.updateLocalPosition(location.clone().add(relativeLoc))));
    }

    public List<PacketPlayOutEntityTeleport> createTeleportPackets() {
        List<PacketPlayOutEntityTeleport> teleportPackets = new ArrayList<>();
        new HashMap<>(parts).forEach((entityBlock, relativeLoc) -> teleportPackets.add(entityBlock.createTeleportPacket()));
        return teleportPackets;
    }

    /*
       Rotations
     */

    public void rotateAroundAxisX(double angle) {
        new HashMap<>(parts).forEach((entityBlock, relativeLoc) -> {
            Vector newRelLoc = VectorUtils.rotateAroundAxisX(relativeLoc, angle);
            entityBlock.updateLocalPosition(location.clone().add(newRelLoc));
            parts.replace(entityBlock, newRelLoc);
        });
    }

    public void rotateAroundAxisY(double angle) {
        new HashMap<>(parts).forEach((entityBlock, relativeLoc) -> {
            Vector newRelLoc = VectorUtils.rotateAroundAxisY(relativeLoc, angle);
            entityBlock.updateLocalPosition(location.clone().add(newRelLoc));
            parts.replace(entityBlock, newRelLoc);
        });
    }

    public void rotateAroundAxisZ(double angle) {
        new HashMap<>(parts).forEach((entityBlock, relativeLoc) -> {
            Vector newRelLoc = VectorUtils.rotateAroundAxisZ(relativeLoc, angle);
            entityBlock.updateLocalPosition(location.clone().add(newRelLoc));
            parts.replace(entityBlock, newRelLoc);
        });
    }

    public void rotate(double angleX, double angleY, double angleZ) {
        new HashMap<>(parts).forEach((entityBlock, relativeLoc) -> {
            Vector newRelLoc = VectorUtils.rotateVector(relativeLoc, angleX, angleY, angleZ);
            entityBlock.updateLocalPosition(location.clone().add(newRelLoc));
            parts.replace(entityBlock, newRelLoc);
        });
    }

    public Location getLocation() {
        return location;
    }
}
