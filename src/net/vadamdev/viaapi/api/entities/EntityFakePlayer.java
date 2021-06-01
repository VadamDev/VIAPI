package net.vadamdev.viaapi.api.entities;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.*;
import net.vadamdev.viaapi.tools.utils.Constants;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class EntityFakePlayer {
    /**
     * @author VadamDev
     * @since 21.12.2020
     */

    public EntityPlayer fakePlayer, nmsPlayer;
    private Location loc;
    private String name, value, signature;

    public EntityFakePlayer(Location loc, String name, String value, String signature) {
        this.loc = loc;
        this.name = name;
        this.value = value;
        this.signature = signature;

        init();
    }

    private void init() {
        MinecraftServer ms = MinecraftServer.getServer();
        WorldServer ws = ((CraftWorld) loc.getWorld()).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), name);

        fakePlayer = new EntityPlayer(ms, ws, gameProfile, new PlayerInteractManager(ws));

        gameProfile.getProperties().put("textures", new Property("textures", value, signature));

        fakePlayer.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());

        Constants.npcIds.add(fakePlayer.getId());
    }

    public void sendWithPacket(Player player) {
        nmsPlayer = ((CraftPlayer) player).getHandle();

        PacketPlayOutPlayerInfo ppopi = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, fakePlayer);
        PacketPlayOutNamedEntitySpawn ppones = new PacketPlayOutNamedEntitySpawn(fakePlayer);

        nmsPlayer.playerConnection.sendPacket(ppopi);
        nmsPlayer.playerConnection.sendPacket(ppones);
    }

    public void removeWithPacket(Player player) {
        nmsPlayer = ((CraftPlayer) player).getHandle();

        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(fakePlayer.getId());
        nmsPlayer.playerConnection.sendPacket(packet);
    }

    public void setSneaking() {
        fakePlayer.setSneaking(true);
    }

    public void removeFromTab() {
        PacketPlayOutPlayerInfo ppopi = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, fakePlayer);
        nmsPlayer.playerConnection.sendPacket(ppopi);
    }

    public enum NPCAction {
        INTERRACT, ATTACK
    }
}
