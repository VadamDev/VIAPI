package net.vadamdev.viaapi.api.entities;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.*;
import net.vadamdev.viaapi.VIAPI;
import net.vadamdev.viaapi.tools.packet.Reflection;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;

import java.util.UUID;

public class EntityFakePlayer {
    /**
     * @author VadamDev
     * @since 21.12.2020 - Updated 07.09.2021
     */

    public EntityPlayer fakePlayer;
    private Location loc;
    private String name, value, signature;
    private boolean removedFromTab;

    public EntityFakePlayer(Location loc, String name, String value, String signature) {
        this.loc = loc;
        this.name = name;
        this.value = value;
        this.signature = signature;
        this.removedFromTab = false;

        init();
    }

    private void init() {
        WorldServer ws = ((CraftWorld) loc.getWorld()).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), name);

        fakePlayer = new EntityPlayer(MinecraftServer.getServer(), ws, gameProfile, new PlayerInteractManager(ws));

        gameProfile.getProperties().put("textures", new Property("textures", value, signature));

        fakePlayer.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }

    public void sendWithPacket(Player player) {
        Reflection.sendPacket(player, new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, fakePlayer));
        Reflection.sendPacket(player, new PacketPlayOutNamedEntitySpawn(fakePlayer));

        if(removedFromTab) VIAPI.getScheduler().runTaskLaterAsynchronously(VIAPI.get(), r -> Reflection.sendPacket(player, new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, fakePlayer)), 10);
    }

    public void removeWithPacket(Player player) {
        Reflection.sendPacket(player, new PacketPlayOutEntityDestroy(fakePlayer.getId()));
    }

    public void setSneaking() {
        fakePlayer.setSneaking(true);
    }

    public void setRemovedFromTab(boolean removedFromTab) {
        this.removedFromTab = removedFromTab;
    }
}
