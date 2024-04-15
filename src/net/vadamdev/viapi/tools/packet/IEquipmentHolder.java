package net.vadamdev.viapi.tools.packet;

import net.minecraft.server.v1_8_R3.ItemStack;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Collections;

/**
 * @author VadamDev
 * @since 02/09/2023
 */
public interface IEquipmentHolder {
    void updateLocalEquipment(int slot, ItemStack itemStack);

    void updateEquipment(int slot, ItemStack itemStack, Collection<Player> players);
    default void updateEquipment(int slot, ItemStack itemStack, Player player) {
        updateEquipment(slot, itemStack, Collections.singleton(player));
    }

    ItemStack getLocalEquipment(int slot);
}
