package net.vadamdev.viaapi.api.entities;

import net.minecraft.server.v1_8_R3.*;
import net.vadamdev.viaapi.tools.packet.Reflection;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class EntityCorpse {
    /**
     * @author VadamDev
     * @since 28.08.2021
     */

    private Player owner;
    private Location loc;
    private boolean equipped;
    private int id;

    public EntityCorpse(Player owner, Location loc, boolean equipped) {
        this.owner = owner;
        this.loc = loc;
        this.equipped = equipped;

        this.id = getNextEntityId();
    }

    public void sendWithPacket(Player player) {
        PacketPlayOutNamedEntitySpawn npc = new PacketPlayOutNamedEntitySpawn(((CraftPlayer) owner).getHandle());
        Reflection.setField(Reflection.getField(npc.getClass(), "a"), npc, id);
        Reflection.setField(Reflection.getField(npc.getClass(), "f"), npc, (byte) 0);
        Reflection.setField(Reflection.getField(npc.getClass(), "g"), npc, (byte) 0);

        PacketPlayOutBed bed = new PacketPlayOutBed();
        Reflection.setField(Reflection.getField(bed.getClass(), "a"), bed, id);
        Reflection.setField(Reflection.getField(bed.getClass(), "b"), bed, new BlockPosition(loc.getBlockX(), 0, loc.getBlockZ()));

        player.sendBlockChange(loc.clone().subtract(0, loc.getY(), 0), Material.BED_BLOCK, (byte) 0);

        Reflection.sendPacket(player, npc);
        Reflection.sendPacket(player, bed);
        Reflection.sendPacket(player, new PacketPlayOutEntity.PacketPlayOutRelEntityMove(id, (byte) 0, (byte) 4, (byte) 0, false));

        if(equipped) {
            Reflection.sendPacket(player, new PacketPlayOutEntityEquipment(id, 4, CraftItemStack.asNMSCopy(owner.getEquipment().getHelmet())));
            Reflection.sendPacket(player, new PacketPlayOutEntityEquipment(id, 3, CraftItemStack.asNMSCopy(owner.getEquipment().getChestplate())));
            Reflection.sendPacket(player, new PacketPlayOutEntityEquipment(id, 2, CraftItemStack.asNMSCopy(owner.getEquipment().getLeggings())));
            Reflection.sendPacket(player, new PacketPlayOutEntityEquipment(id, 1, CraftItemStack.asNMSCopy(owner.getEquipment().getBoots())));
        }
    }

    public void removeWithPacket(Player player) {
        Reflection.sendPacket(player, new PacketPlayOutEntityDestroy(id));
    }

    public Location getLoc() {
        return loc;
    }

    private int getNextEntityId() {
        try {
            Field entityCount = Reflection.getField(Entity.class, "entityCount");

            int id = entityCount.getInt(null);
            entityCount.setInt(null, id + 1);

            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return (int) Math.round(Math.random() * 2.147483647E9D * 0.25D);
        }
    }
}
