package net.vadamdev.viaapi.tools.entitystructure;

import net.minecraft.server.v1_8_R3.*;
import net.vadamdev.viaapi.tools.builders.NMSArmorStandBuilder;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.block.Block;

import java.util.Arrays;
import java.util.List;

/**
 * @author VadamDev
 * @since 09/08/2022
 */
public class EntityBlock {
    private Location location;
    private final int blockId;
    private final byte blockData;

    private final EntityFallingBlock entityFallingBlock;
    private final EntityArmorStand armorStand;

    public EntityBlock(Location location, Block block) {
        this.location = location;
        this.blockId = block.getTypeId();
        this.blockData = block.getData();

        this.entityFallingBlock = new EntityFallingBlock(((CraftWorld) location.getWorld()).getHandle(), location.getX(), location.getY(), location.getZ(), null);
        this.armorStand = new NMSArmorStandBuilder(location)
                .setAsMarker()
                .setSmall(true)
                .setVisible(false)
                .setBasePlate(false)
                .toArmorStandEntity();
    }

    public void updateLocalPosition(Location location) {
        this.location = location;
        armorStand.setPosition(location.getX(), location.getY(), location.getZ());
    }

    public List<Packet<PacketListenerPlayOut>> getSpawnPackets() {
        return Arrays.asList(
                new PacketPlayOutSpawnEntity(entityFallingBlock, 70, blockId | (blockData << 0xC)),
                new PacketPlayOutSpawnEntityLiving(armorStand),
                new PacketPlayOutAttachEntity(0, entityFallingBlock, armorStand)
        );
    }

    public PacketPlayOutEntityDestroy getDeletePacket() {
        return new PacketPlayOutEntityDestroy(entityFallingBlock.getId(), armorStand.getId());
    }

    public PacketPlayOutEntityTeleport createTeleportPacket() {
        return new PacketPlayOutEntityTeleport(armorStand.getId(),
                MathHelper.floor(location.getX() * 32D), MathHelper.floor(location.getY() * 32D), MathHelper.floor(location.getZ() * 32D),
                (byte) 0, (byte) 0, false);
    }

    public Location getLocation() {
        return location;
    }
}
