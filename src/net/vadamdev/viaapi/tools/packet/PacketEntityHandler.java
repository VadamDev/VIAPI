package net.vadamdev.viaapi.tools.packet;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Deprecated
/**
 * @author VadamDev
 * Legacy PacketEntityHandler class, may still useful
 */
public final class PacketEntityHandler {
    private final EntityLiving entityLiving;
    private final int id;
    private final boolean shouldDisplayArmor;

    private float yaw;

    public PacketEntityHandler(EntityLiving entityLiving) {
        this(entityLiving, true);
    }

    public PacketEntityHandler(EntityLiving entityLiving, boolean shouldDisplayArmor) {
        this.entityLiving = entityLiving;
        this.id = entityLiving.getId();
        this.shouldDisplayArmor = shouldDisplayArmor;

        this.yaw = 0;
    }

    public void spawn(Player player) {
        Reflection.sendPacket(player, new PacketPlayOutSpawnEntityLiving(entityLiving));
        Reflection.sendPacket(player, new PacketPlayOutEntityHeadRotation(entityLiving, (byte) ((yaw * 256f) / 360f)));

        if(shouldDisplayArmor) {
            Reflection.sendPacket(player, new PacketPlayOutEntityEquipment(id, 4, entityLiving.getEquipment()[4]));
            Reflection.sendPacket(player, new PacketPlayOutEntityEquipment(id, 3, entityLiving.getEquipment()[3]));
            Reflection.sendPacket(player, new PacketPlayOutEntityEquipment(id, 2, entityLiving.getEquipment()[2]));
            Reflection.sendPacket(player, new PacketPlayOutEntityEquipment(id, 1, entityLiving.getEquipment()[1]));
            Reflection.sendPacket(player, new PacketPlayOutEntityEquipment(id, 0, entityLiving.getEquipment()[0]));
        }
    }

    public void delete(Player player) {
        Reflection.sendPacket(player, new PacketPlayOutEntityDestroy(id));
    }

    public void teleport(Player player, Location loc) {
        Reflection.sendPacket(player, new PacketPlayOutEntityTeleport(id, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), (byte) loc.getYaw(), (byte) loc.getPitch(), true));
    }

    public void spawnWithRadius(Location center, int radius) {
        Bukkit.getOnlinePlayers().parallelStream().filter(player -> player.getLocation().distanceSquared(center) <= radius * radius).forEach(this::spawn);
    }

    public void deleteWithRadius(Location center, int radius) {
        Bukkit.getOnlinePlayers().parallelStream().filter(player -> player.getLocation().distanceSquared(center) <= radius * radius).forEach(this::delete);
    }

    public void teleportWithRadius(Location loc, Location center, int radius) {
        Bukkit.getOnlinePlayers().parallelStream().filter(player -> player.getLocation().distanceSquared(center) <= radius * radius).forEach(player -> teleport(player, loc));
    }

    public void spawnForEveryone() {
        Bukkit.getOnlinePlayers().forEach(this::spawn);
    }

    public void deleteForEveryone() {
        Bukkit.getOnlinePlayers().forEach(this::delete);
    }

    public void teleportForEveryone(Location loc) {
        Bukkit.getOnlinePlayers().forEach(player -> teleport(player, loc));
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public EntityLiving getEntityLiving() {
        return entityLiving;
    }
}
