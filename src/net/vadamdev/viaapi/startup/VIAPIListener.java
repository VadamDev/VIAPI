package net.vadamdev.viaapi.startup;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author VadamDev
 * @since 29/12/2022
 */
public class VIAPIListener implements Listener {
    private final boolean eggReplacer;

    public VIAPIListener(boolean eggReplacer) {
        this.eggReplacer = eggReplacer;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.isCancelled() || event.getClickedBlock() == null || event.getBlockFace() == null || !eggReplacer)
            return;

        ItemStack item = event.getItem();

        if(item != null && item.getType().equals(Material.MONSTER_EGG)) {
            EntityType entityType = getTypeByData(item.getData().getData());
            Location location = event.getClickedBlock().getRelative(event.getBlockFace()).getLocation();

            if(entityType != null) {
                event.setCancelled(true);
                location.getWorld().spawnEntity(location.clone().add(0.5f, 0, 0.5f), entityType);
            }
        }
    }

    private static EntityType getTypeByData(byte data) {
        switch(data) {
            case 50:
                return EntityType.CREEPER;
            case 51:
                return EntityType.SKELETON;
            case 52:
                return EntityType.SPIDER;
            case 54:
                return EntityType.ZOMBIE;
            case 55:
                return EntityType.SLIME;
            case 56:
                return EntityType.GHAST;
            case 57:
                return EntityType.PIG_ZOMBIE;
            case 58:
                return EntityType.ENDERMAN;
            case 59:
                return EntityType.CAVE_SPIDER;
            case 60:
                return EntityType.SILVERFISH;
            case 61:
                return EntityType.BLAZE;
            case 62:
                return EntityType.MAGMA_CUBE;
            case 65:
                return EntityType.BAT;
            case 66:
                return EntityType.WITCH;
            case 67:
                return EntityType.ENDERMITE;
            case 68:
                return EntityType.GUARDIAN;
            case 90:
                return EntityType.PIG;
            case 91:
                return EntityType.SHEEP;
            case 92:
                return EntityType.COW;
            case 93:
                return EntityType.CHICKEN;
            case 94:
                return EntityType.SQUID;
            case 95:
                return EntityType.WOLF;
            case 96:
                return EntityType.MUSHROOM_COW;
            case 98:
                return EntityType.OCELOT;
            case 100:
                return EntityType.HORSE;
            case 101:
                return EntityType.RABBIT;
            case 120:
                return EntityType.VILLAGER;
            default:
                return null;
        }
    }
}
