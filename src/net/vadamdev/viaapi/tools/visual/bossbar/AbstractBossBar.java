package net.vadamdev.viaapi.tools.visual.bossbar;

import net.minecraft.server.v1_8_R3.EntityWither;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.WorldServer;
import net.vadamdev.viaapi.tools.packet.Reflection;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class AbstractBossBar {
    /**
     * @author VadamDev
     * @since 26.08.2021
     */

    public Player player;
    private EntityWither wither;

    public String title;

    public AbstractBossBar(Player player) {
        this.player = player;
    }

    public abstract void reloadData();
    public abstract void animate();

    public void sendWither() {
        if(wither != null) Reflection.sendPacket(player, new PacketPlayOutEntityDestroy(wither.getId()));

        Location loc = player.getLocation();

        EntityWither wither = new EntityWither((WorldServer) Reflection.getHandle(loc.getWorld()));
        Location farLoc = loc.add(loc.getDirection().multiply(30));

        wither.setLocation(farLoc.getX(), farLoc.getY(), farLoc.getZ(), 0, 0);
        wither.setCustomName(title);
        wither.setCustomNameVisible(false);
        wither.setInvisible(true);
        wither.b(true);

        this.wither = wither;

        Reflection.sendPacket(player, new PacketPlayOutSpawnEntityLiving(wither));
    }
}
