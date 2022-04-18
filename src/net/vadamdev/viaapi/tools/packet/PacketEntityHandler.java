package net.vadamdev.viaapi.tools.packet;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PacketEntityHandler {
    private final EntityLiving livingEntity;
    private final int id;
    private final boolean shouldDisplayArmor;

    public PacketEntityHandler(EntityLiving livingEntity) {
        this(livingEntity, true);
    }

    public PacketEntityHandler(EntityLiving livingEntity, boolean shouldDisplayArmor) {
        this.livingEntity = livingEntity;
        this.id = livingEntity.getId();
        this.shouldDisplayArmor = shouldDisplayArmor;
    }

    public void spawn(Player player) {
        Reflection.sendPacket(player, new PacketPlayOutSpawnEntityLiving(livingEntity));
        Reflection.sendPacket(player, new PacketPlayOutEntityMetadata(id, livingEntity.getDataWatcher(), true));

        if(shouldDisplayArmor) {
            Reflection.sendPacket(player, new PacketPlayOutEntityEquipment(id, 4, livingEntity.getEquipment()[4]));
            Reflection.sendPacket(player, new PacketPlayOutEntityEquipment(id, 3, livingEntity.getEquipment()[3]));
            Reflection.sendPacket(player, new PacketPlayOutEntityEquipment(id, 2, livingEntity.getEquipment()[2]));
            Reflection.sendPacket(player, new PacketPlayOutEntityEquipment(id, 1, livingEntity.getEquipment()[1]));
            Reflection.sendPacket(player, new PacketPlayOutEntityEquipment(id, 0, livingEntity.getEquipment()[0]));
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
}
