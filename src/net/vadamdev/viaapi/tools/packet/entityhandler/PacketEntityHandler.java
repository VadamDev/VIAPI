package net.vadamdev.viaapi.tools.packet.entityhandler;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * @author VadamDev
 * @since 10/05/2022
 */
public class PacketEntityHandler implements IEntityHandler {
    protected final EntityLiving entity;
    protected final int id;
    private final boolean shouldDisplayArmor;

    private Location entityLocation;
    private float yaw;

    public PacketEntityHandler(EntityLiving entity) {
        this(entity, true);
    }

    public PacketEntityHandler(EntityLiving entity, boolean shouldDisplayArmor) {
        this.entity = entity;
        this.id = entity.getId();
        this.shouldDisplayArmor = shouldDisplayArmor;

        this.entityLocation = entity.getBukkitEntity().getLocation();
        this.yaw = 0;
    }

    @Override
    public void spawn(Player player) {
        PlayerConnection playerConnection = getPlayerConnection(player);

        playerConnection.sendPacket(new PacketPlayOutSpawnEntityLiving(entity));
        playerConnection.sendPacket(new PacketPlayOutEntityHeadRotation(entity, (byte) ((yaw * 256f) / 360f)));

        if(shouldDisplayArmor) {
            for(int i = 0; i < entity.getEquipment().length; i++) {
                if(entity.getEquipment()[i] == null) continue;
                playerConnection.sendPacket(new PacketPlayOutEntityEquipment(id, i, entity.getEquipment()[i]));
            }
        }
    }

    @Override
    public void updateLocalEquipment(int slot, ItemStack itemStack) {
        entity.setEquipment(slot, itemStack);
    }


    @Override
    public void updateEquipment(Player player, int slot, ItemStack itemStack) {
        getPlayerConnection(player).sendPacket(new PacketPlayOutEntityEquipment(id, slot, itemStack));
    }

    @Override
    public void updateLocalPosition(Location location) {
        this.entityLocation = location;
        entity.setPosition(location.getX(), location.getY(), location.getZ());
    }

    @Override
    public void teleport(Player player, Location location) {
        byte yaw = (byte) ((int) (location.getYaw() * 256.0F / 360.0F));
        byte pitch = (byte) ((int) (location.getPitch() * 256.0F / 360.0F));

        getPlayerConnection(player).sendPacket(new PacketPlayOutEntityTeleport(id,
                MathHelper.floor(location.getX() * 32D), MathHelper.floor(location.getY() * 32D), MathHelper.floor(location.getZ() * 32D),
                yaw, pitch,
                false));
    }

    @Override
    public void delete(Player player) {
        getPlayerConnection(player).sendPacket(new PacketPlayOutEntityDestroy(id));
    }

    @Override
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    @Override
    public Location getLocation() {
        return entityLocation;
    }

    protected PlayerConnection getPlayerConnection(Player player) {
        return ((CraftPlayer) player).getHandle().playerConnection;
    }
}
