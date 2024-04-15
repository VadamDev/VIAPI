package net.vadamdev.viapi.internal.bossbar;

import net.minecraft.server.v1_8_R3.*;
import net.vadamdev.viapi.internal.VIAPIPlugin;
import net.vadamdev.viapi.tools.bossbar.BossBar;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class BossBarImpl implements BossBar {
    private static final int ENTITY_DISTANCE = 32;

    private static final BossBarManager bossBarManager = (BossBarManager) VIAPIPlugin.instance.getBossBarAPI();

    private final Map<Player, EntityLiving> viewers;
    private String title;
    private float progress;

    private Function<World, EntityLiving> bossSupplier;

    public BossBarImpl(String title, Type type) {
        this.viewers = new HashMap<>();
        this.title = title;
        this.progress = 1f;

        switch(type) {
            case WITHER:
                bossSupplier = EntityWither::new;
                break;
            case ENDER_DRAGON:
                bossSupplier = EntityEnderDragon::new;
                break;
            default:
                break;
        }
    }

    /*
       Utility Methods
     */
    public void handlePlayerMove(Player player, Location from, Location to) {
        if(from.getWorld().equals(to.getWorld())) {
            final Location playerLocation = player.getLocation();
            final Vector transform = playerLocation.getDirection().multiply(ENTITY_DISTANCE).add(playerLocation.toVector());

            getPlayerConnection(player).sendPacket(new PacketPlayOutEntityTeleport(viewers.get(player).getId(),
                    MathHelper.floor(transform.getX() * 32D), MathHelper.floor(transform.getY() * 32D), MathHelper.floor(transform.getZ() * 32D),
                    (byte) 0, (byte) 0,
                    false));
        }else {
            destroyCurrentEntity(player);
            viewers.replace(player, spawnNewEntity(player));
        }
    }

    /*
       Impl
     */

    @Override
    public void addPlayer(Player player) {
        viewers.put(player, spawnNewEntity(player));
        bossBarManager.registerPlayer(player, this);
    }

    @Override
    public void removePlayer(Player player) {
        bossBarManager.removePlayer(player, this);

        destroyCurrentEntity(player);
        viewers.remove(player);
    }

    @Override
    public void removeAll() {
        viewers.forEach((player, entityLiving) -> removePlayer(player));
    }

    @Override
    public void setTitle(String title) {
        this.title = title;

        viewers.forEach((player, entity) -> {
            entity.setCustomName(title);
            getPlayerConnection(player).sendPacket(new PacketPlayOutEntityMetadata(entity.getId(), entity.getDataWatcher(), false));
        });
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setProgress(float progress) {
        if(progress <= 0 || progress > 1)
            throw new InvalidParameterException("Progress must be greater than 0 and less than 1");

        this.progress = progress;

        viewers.forEach((player, entity) -> {
            entity.setHealth(progress * entity.getMaxHealth());
            getPlayerConnection(player).sendPacket(new PacketPlayOutEntityMetadata(entity.getId(), entity.getDataWatcher(), false));
        });
    }

    @Override
    public float getProgress() {
        return progress;
    }

    @Override
    public Set<Player> getPlayers() {
        return viewers.keySet();
    }

    private EntityLiving spawnNewEntity(Player player) {
        final Location playerLocation = player.getLocation();
        final Vector transform = playerLocation.getDirection().multiply(ENTITY_DISTANCE).add(playerLocation.toVector());

        final EntityLiving entity = bossSupplier.apply(((CraftWorld) player.getWorld()).getHandle());
        entity.setPosition(transform.getX(), transform.getY(), transform.getZ());
        entity.b(true);
        entity.setInvisible(true);
        entity.setHealth(progress * entity.getMaxHealth());
        entity.setCustomName(title);

        getPlayerConnection(player).sendPacket(new PacketPlayOutSpawnEntityLiving(entity));

        return entity;
    }

    private void destroyCurrentEntity(Player player) {
        getPlayerConnection(player).sendPacket(new PacketPlayOutEntityDestroy(viewers.get(player).getId()));
    }

    private PlayerConnection getPlayerConnection(Player player) {
        return ((CraftPlayer) player).getHandle().playerConnection;
    }
}
