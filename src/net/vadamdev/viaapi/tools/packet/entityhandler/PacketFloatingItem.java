package net.vadamdev.viaapi.tools.packet.entityhandler;

import net.minecraft.server.v1_8_R3.*;
import net.vadamdev.viaapi.tools.builders.NMSArmorStandBuilder;
import net.vadamdev.viaapi.tools.enums.LockType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author VadamDev
 * @since 10/06/2022
 */
public class PacketFloatingItem implements IEntityHandler {
    private final EntityItem entityItem;
    private final EntityArmorStand armorStand;
    private final int itemId, armorStandId;

    private Location entityLocation;

    public PacketFloatingItem(Location location, ItemStack itemStack) {
        this.entityItem = new EntityItem(((CraftWorld) location.getWorld()).getHandle());
        this.entityItem.setPosition(location.getX(), location.getY(), location.getZ());
        this.entityItem.setItemStack(CraftItemStack.asNMSCopy(itemStack != null ? itemStack : new ItemStack(Material.STONE)));

        this.armorStand = new NMSArmorStandBuilder(location)
                .setSmall(true)
                .setAsMarker()
                .setBasePlate(false)
                .setVisible(false)
                .lockSlot(LockType.ALL)
                .toArmorStandEntity();

        this.itemId = entityItem.getId();
        this.armorStandId = armorStand.getId();

        entityLocation = armorStand.getBukkitEntity().getLocation();
    }

    @Override
    public void spawn(Player player) {
        PlayerConnection playerConnection = getPlayerConnection(player);

        playerConnection.sendPacket(new PacketPlayOutSpawnEntityLiving(armorStand));
        playerConnection.sendPacket(new PacketPlayOutSpawnEntity(entityItem, 2));
        playerConnection.sendPacket(new PacketPlayOutEntityMetadata(itemId, entityItem.getDataWatcher(), true));

        playerConnection.sendPacket(new PacketPlayOutAttachEntity(0, entityItem, armorStand));
    }

    @Override
    public void delete(Player player) {
        getPlayerConnection(player).sendPacket(new PacketPlayOutEntityDestroy(itemId, armorStandId));
    }

    @Override
    public void updateLocalPosition(Location location) {
        entityLocation = location;
        armorStand.setPosition(location.getX(), location.getY(), location.getZ());
    }

    @Override
    public void teleport(Player player, Location location) {
        getPlayerConnection(player).sendPacket(new PacketPlayOutEntityTeleport(armorStandId,
                MathHelper.floor(location.getX() * 32D), MathHelper.floor(location.getY() * 32D), MathHelper.floor(location.getZ() * 32D),
                (byte) 0, (byte) 0,
                false));
    }

    @Override
    public Location getLocation() {
        return entityLocation;
    }

    @Override
    public void setYaw(float yaw) {}

    protected PlayerConnection getPlayerConnection(Player player) {
        return ((CraftPlayer) player).getHandle().playerConnection;
    }
}
