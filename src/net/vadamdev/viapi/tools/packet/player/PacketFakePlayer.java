package net.vadamdev.viapi.tools.packet.player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.*;
import net.vadamdev.viapi.internal.VIAPIPlugin;
import net.vadamdev.viapi.tools.packet.IEquipmentHolder;
import net.vadamdev.viapi.tools.packet.IPacketEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * @author VadamDev
 * @since 03/09/2023
 */
public class PacketFakePlayer implements IPacketEntity, IEquipmentHolder {
    protected final EntityPlayer entityPlayer;
    protected final int playerId;

    private final PacketPlayOutEntityDestroy destroyPacket;

    protected Location location;

    public PacketFakePlayer(Location location, String name, String value, String signature, boolean showCape) {
        final WorldServer ws = ((CraftWorld) location.getWorld()).getHandle();

        final GameProfile gameProfile = new GameProfile(UUID.randomUUID(), name);
        gameProfile.getProperties().put("textures", new Property("textures", value, signature));

        this.entityPlayer = new EntityPlayer(MinecraftServer.getServer(), ws, gameProfile, new PlayerInteractManager(ws));
        this.entityPlayer.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        this.entityPlayer.getDataWatcher().watch(10, (byte) ((showCape ? 0x01 : 0) + 0x02 + 0x04 + 0x08 + 0x10 + 0x20 + 0x40));

        this.playerId = entityPlayer.getId();

        this.destroyPacket = new PacketPlayOutEntityDestroy(playerId);

        this.location = location;
    }

    @Override
    public void spawn(Collection<Player> players) {
        final List<Packet<PacketListenerPlayOut>> packets = Arrays.asList(
                new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer),
                new PacketPlayOutNamedEntitySpawn(entityPlayer),
                new PacketPlayOutEntityHeadRotation(entityPlayer, (byte) ((int) (location.getYaw() * 256.0F / 360.0F)))
        );

        for (Player player : players) {
            final PlayerConnection playerConnection = getPlayerConnection(player);
            packets.forEach(playerConnection::sendPacket);
        }

        final PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer);
        Bukkit.getScheduler().runTaskLaterAsynchronously(VIAPIPlugin.instance, () -> {
            for (Player player : players)
                getPlayerConnection(player).sendPacket(packet);
        }, 15);
    }

    @Override
    public void delete(Collection<Player> players) {
        for (Player player : players)
            getPlayerConnection(player).sendPacket(destroyPacket);
    }

    public void setSneakingLocal(boolean sneaking) {
        entityPlayer.setSneaking(sneaking);
    }

    public void updateMetadata(Collection<Player> players) {
        final PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(playerId, entityPlayer.getDataWatcher(), false);

        for (Player player : players)
            getPlayerConnection(player).sendPacket(packet);
    }

    public void updateMetadata(Player player) {
        updateMetadata(Collections.singleton(player));
    }

    @Override
    public void teleportLocal(Location location) {
        entityPlayer.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        this.location = location;
    }

    @Override
    public void teleport(Location location, Collection<Player> players) {
        final byte yaw = (byte) ((int) (location.getYaw() * 256.0F / 360.0F));
        final byte pitch = (byte) ((int) (location.getPitch() * 256.0F / 360.0F));

        final PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(playerId,
                MathHelper.floor(location.getX() * 32D), MathHelper.floor(location.getY() * 32D), MathHelper.floor(location.getZ() * 32D),
                yaw, pitch,
                false);

        for(Player player : players)
            getPlayerConnection(player).sendPacket(packet);
    }

    @Override
    public void updateLocalEquipment(int slot, ItemStack itemStack) {
        entityPlayer.setEquipment(slot, itemStack);
    }

    @Override
    public void updateEquipment(int slot, ItemStack itemStack, Collection<Player> players) {
        final PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(playerId, slot, itemStack);

        for (Player player : players)
            getPlayerConnection(player).sendPacket(packet);
    }

    @Override
    public ItemStack getLocalEquipment(int slot) {
        return entityPlayer.getEquipment(slot);
    }

    @Override
    public Location getLocalLocation() {
        return location;
    }

    public int getPlayerId() {
        return playerId;
    }

    protected PlayerConnection getPlayerConnection(Player player) {
        return ((CraftPlayer) player).getHandle().playerConnection;
    }
}
