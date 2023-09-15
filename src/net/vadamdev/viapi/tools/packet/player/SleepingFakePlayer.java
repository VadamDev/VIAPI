package net.vadamdev.viapi.tools.packet.player;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutBed;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * @author VadamDev
 * @since 03/09/2023
 */
public class SleepingFakePlayer extends PacketFakePlayer {
    protected final Location bedLocation;

    public SleepingFakePlayer(Location location, String name, String value, String signature, boolean showCape) {
        super(location, name, value, signature, showCape);

        this.location.setYaw(0);
        this.location.setPitch(0);

        this.bedLocation = location.clone().subtract(0, location.getY(), 0);
    }

    @Override
    public void spawn(Collection<Player> players) {
        super.spawn(players);

        final PacketPlayOutBed packet = new PacketPlayOutBed(entityPlayer, new BlockPosition(bedLocation.getBlockX(), bedLocation.getBlockY(), bedLocation.getBlockZ()));
        for (Player player : players) {
            player.sendBlockChange(bedLocation, Material.BED_BLOCK, (byte) 0);
            getPlayerConnection(player).sendPacket(packet);
        }

        teleport(location, players);
    }

    @Override
    public void delete(Collection<Player> players) {
        super.delete(players);

        final Material oldMaterial = bedLocation.getBlock().getType();
        for (Player player : players)
            player.sendBlockChange(bedLocation, oldMaterial, (byte) 0);
    }
}
