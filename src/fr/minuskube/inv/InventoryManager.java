package fr.minuskube.inv;

import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.opener.ChestInventoryOpener;
import fr.minuskube.inv.opener.InventoryOpener;
import fr.minuskube.inv.opener.SpecialInventoryOpener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.logging.Level;

public class InventoryManager {
    private final JavaPlugin plugin;

    private final Map<UUID, SmartInventory> inventories;
    private final Map<UUID, InventoryContents> contents;

    private final List<InventoryOpener> defaultOpeners;
    private final List<InventoryOpener> openers;

    public InventoryManager(JavaPlugin plugin) {
        this.plugin = plugin;

        this.inventories = new HashMap<>();
        this.contents = new HashMap<>();

        this.defaultOpeners = Arrays.asList(
                new ChestInventoryOpener(),
                new SpecialInventoryOpener()
        );

        this.openers = new ArrayList<>();

        Bukkit.getPluginManager().registerEvents(new InvListener(), plugin);

        new InvTask().runTaskTimer(plugin, 1, 1);
    }

    public Optional<InventoryOpener> findOpener(InventoryType type) {
        Optional<InventoryOpener> opInv = openers.stream()
                .filter(opener -> opener.supports(type))
                .findAny();

        if (!opInv.isPresent()) {
            opInv = defaultOpeners.stream()
                    .filter(opener -> opener.supports(type))
                    .findAny();
        }

        return opInv;
    }

    public void registerOpeners(InventoryOpener... openers) {
        this.openers.addAll(Arrays.asList(openers));
    }

    public List<Player> getOpenedPlayers(SmartInventory inv) {
        final List<Player> list = new ArrayList<>();

        inventories.forEach((player, playerInv) -> {
            if (inv.equals(playerInv))
                list.add(Bukkit.getPlayer(player));
        });

        return list;
    }

    public Optional<SmartInventory> getInventory(Player p) {
        return Optional.ofNullable(inventories.get(p.getUniqueId()));
    }

    protected void setInventory(Player p, SmartInventory inv) {
        if (inv == null)
            inventories.remove(p.getUniqueId());
        else
            inventories.put(p.getUniqueId(), inv);
    }

    public Optional<InventoryContents> getContents(Player p) {
        return Optional.ofNullable(contents.get(p.getUniqueId()));
    }

    protected void setContents(Player p, InventoryContents contents) {
        if (contents == null)
            this.contents.remove(p.getUniqueId());
        else
            this.contents.put(p.getUniqueId(), contents);
    }

    public void handleInventoryOpenError(SmartInventory inventory, Player player, Exception exception) {
        inventory.close(player);

        Bukkit.getLogger().log(Level.SEVERE, "Error while opening SmartInventory:", exception);
    }

    public void handleInventoryUpdateError(SmartInventory inventory, Player player, Exception exception) {
        inventory.close(player);

        Bukkit.getLogger().log(Level.SEVERE, "Error while updating SmartInventory:", exception);
    }

    @SuppressWarnings("unchecked")
    class InvListener implements Listener {
        @EventHandler(priority = EventPriority.LOW)
        public void onInventoryClick(InventoryClickEvent e) {
            final Player p = (Player) e.getWhoClicked();

            if (!inventories.containsKey(p.getUniqueId()))
                return;

            // Restrict putting items from the bottom inventory into the top inventory
            final Inventory clickedInventory = e.getClickedInventory();
            if (clickedInventory == p.getOpenInventory().getBottomInventory()) {
                if (e.getAction() == InventoryAction.COLLECT_TO_CURSOR || e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                    e.setCancelled(true);
                    return;
                }
  
                if (e.getAction() == InventoryAction.NOTHING && e.getClick() != ClickType.MIDDLE) {
                    e.setCancelled(true);
                    return;
                }
            }

            if (clickedInventory == p.getOpenInventory().getTopInventory()) {
                e.setCancelled(true);

                int row = e.getSlot() / 9;
                int column = e.getSlot() % 9;

                if (row < 0 || column < 0)
                    return;

                final SmartInventory inv = inventories.get(p.getUniqueId());

                if (row >= inv.getRows() || column >= inv.getColumns())
                    return;

                inv.getListeners().stream()
                        .filter(listener -> listener.getType() == InventoryClickEvent.class)
                        .forEach(listener -> ((InventoryListener<InventoryClickEvent>) listener).accept(e));

                contents.get(p.getUniqueId()).get(row, column).ifPresent(item -> item.run(e));

                p.updateInventory();
            }
        }

        @EventHandler(priority = EventPriority.LOW)
        public void onInventoryDrag(InventoryDragEvent e) {
            final Player p = (Player) e.getWhoClicked();

            if (!inventories.containsKey(p.getUniqueId()))
                return;

            for(int slot : e.getRawSlots()) {
                if (slot >= p.getOpenInventory().getTopInventory().getSize())
                    continue;

                e.setCancelled(true);
                break;
            }

            inventories.get(p.getUniqueId()).getListeners().stream()
                    .filter(listener -> listener.getType() == InventoryDragEvent.class)
                    .forEach(listener -> ((InventoryListener<InventoryDragEvent>) listener).accept(e));
        }

        @EventHandler(priority = EventPriority.LOW)
        public void onInventoryOpen(InventoryOpenEvent e) {
            final Player p = (Player) e.getPlayer();

            if (!inventories.containsKey(p.getUniqueId()))
                return;

            inventories.get(p.getUniqueId()).getListeners().stream()
                    .filter(listener -> listener.getType() == InventoryOpenEvent.class)
                    .forEach(listener -> ((InventoryListener<InventoryOpenEvent>) listener).accept(e));
        }

        @EventHandler(priority = EventPriority.LOW)
        public void onInventoryClose(InventoryCloseEvent e) {
            final Player p = (Player) e.getPlayer();

            if (!inventories.containsKey(p.getUniqueId()))
                return;

            final SmartInventory inv = inventories.get(p.getUniqueId());

            inv.getListeners().stream()
                    .filter(listener -> listener.getType() == InventoryCloseEvent.class)
                    .forEach(listener -> ((InventoryListener<InventoryCloseEvent>) listener).accept(e));

            if (inv.isCloseable()) {
                e.getInventory().clear();

                inventories.remove(p.getUniqueId());
                contents.remove(p.getUniqueId());
            } else
                Bukkit.getScheduler().runTask(plugin, () -> p.openInventory(e.getInventory()));
        }

        @EventHandler(priority = EventPriority.LOW)
        public void onPlayerQuit(PlayerQuitEvent e) {
            final Player p = e.getPlayer();

            if (!inventories.containsKey(p.getUniqueId()))
                return;

            inventories.get(p.getUniqueId()).getListeners().stream()
                    .filter(listener -> listener.getType() == PlayerQuitEvent.class)
                    .forEach(listener -> ((InventoryListener<PlayerQuitEvent>) listener).accept(e));

            inventories.remove(p.getUniqueId());
            contents.remove(p.getUniqueId());
        }

        @EventHandler(priority = EventPriority.LOW)
        public void onPluginDisable(PluginDisableEvent e) {
            new HashMap<>(inventories).forEach((player, inv) -> {
                inv.getListeners().stream()
                        .filter(listener -> listener.getType() == PluginDisableEvent.class)
                        .forEach(listener -> ((InventoryListener<PluginDisableEvent>) listener).accept(e));

                inv.close(Bukkit.getPlayer(player));
            });

            inventories.clear();
            contents.clear();
        }

    }

    class InvTask extends BukkitRunnable {
        @Override
        public void run() {
            new HashMap<>(inventories).forEach((uuid, inv) -> {
                final Player player = Bukkit.getPlayer(uuid);

                try {
                    inv.getProvider().update(player, contents.get(uuid));
                } catch (Exception e) {
                    handleInventoryUpdateError(inv, player, e);
                }
            });
        }
    }
}
