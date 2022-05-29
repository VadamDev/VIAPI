package net.vadamdev.viaapi.tools.packet.entityhandler;

import net.minecraft.server.v1_8_R3.*;
import net.vadamdev.viaapi.tools.packet.Reflection;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * @author VadamDev
 * @since 10/05/2022
 */
public class PacketEntityHandler {
    protected final EntityLiving entity;
    protected final int id;
    private final boolean shouldDisplayArmor;

    protected Location entityLocation;
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

    public void spawn(Player player) {
        sendPacket(player, new PacketPlayOutSpawnEntityLiving(entity));
        sendPacket(player, new PacketPlayOutEntityHeadRotation(entity, (byte) ((yaw * 256f) / 360f)));

        if(shouldDisplayArmor) {
            sendPacket(player, new PacketPlayOutEntityEquipment(id, 4, entity.getEquipment()[4]));
            sendPacket(player, new PacketPlayOutEntityEquipment(id, 3, entity.getEquipment()[3]));
            sendPacket(player, new PacketPlayOutEntityEquipment(id, 2, entity.getEquipment()[2]));
            sendPacket(player, new PacketPlayOutEntityEquipment(id, 1, entity.getEquipment()[1]));
            sendPacket(player, new PacketPlayOutEntityEquipment(id, 0, entity.getEquipment()[0]));
        }
    }

    public void updateLocalEquipment(int slot, ItemStack itemStack) {
        entity.setEquipment(slot, itemStack);
    }

    public void updateEquipment(Player player, int slot, ItemStack itemStack) {
        sendPacket(player, new PacketPlayOutEntityEquipment(id, slot, itemStack));
    }

    public void updateLocalPosition(Location location) {
        this.entityLocation = location;
        entity.setPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public void teleport(Player player, Location location) {
        int x = MathHelper.floor(location.getX() * 32D);
        int y = MathHelper.floor(location.getY() * 32D);
        int z = MathHelper.floor(location.getZ() * 32D);
        byte yaw = (byte) ((int)(location.getYaw() * 256.0F / 360.0F));
        byte pitch = (byte) ((int)(location.getPitch() * 256.0F / 360.0F));

        sendPacket(player, new PacketPlayOutEntityTeleport(id, x, y, z, yaw, pitch, false));
    }

    public void delete(Player player) {
        Reflection.sendPacket(player, new PacketPlayOutEntityDestroy(id));
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    protected void sendPacket(Player player, Packet packet) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
