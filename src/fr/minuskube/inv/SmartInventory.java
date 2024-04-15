package fr.minuskube.inv;

import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.opener.InventoryOpener;
import net.vadamdev.viapi.VIAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unchecked")
public class SmartInventory {
    private final InventoryManager manager;

    private String id;
    private String title;
    private InventoryType type;
    private int rows, columns;
    private boolean closeable;

    private InventoryProvider provider;
    private SmartInventory parent;

    private List<InventoryListener<? extends Event>> listeners;

    private SmartInventory(InventoryManager manager) {
        this.manager = manager;
    }

    public Inventory open(Player player) {
        return open(player, 0);
    }

    public Inventory open(Player player, int page) {
        manager.getInventory(player).ifPresent(inv -> {
            inv.getListeners().stream()
                    .filter(listener -> listener.getType() == InventoryCloseEvent.class)
                    .forEach(listener -> ((InventoryListener<InventoryCloseEvent>) listener)
                            .accept(new InventoryCloseEvent(player.getOpenInventory())));

            manager.setInventory(player, null);
        });

        final InventoryContents contents = new InventoryContents.Impl(this, player.getUniqueId());
        contents.pagination().page(page);

        manager.setContents(player, contents);

        try {
            provider.init(player, contents);

            // If the current inventory has been closed or replaced within the init method, returns
            if (!manager.getContents(player).equals(Optional.of(contents)))
                return null;

            final InventoryOpener opener = manager.findOpener(type)
                    .orElseThrow(() -> new IllegalStateException("No opener found for the inventory type " + type.name()));
            final Inventory handle = opener.open(this, player);

            manager.setInventory(player, this);

            return handle;
        } catch (Exception e) {
            manager.handleInventoryOpenError(this, player, e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public void close(Player player) {
        listeners.stream()
                .filter(listener -> listener.getType() == InventoryCloseEvent.class)
                .forEach(listener -> ((InventoryListener<InventoryCloseEvent>) listener)
                        .accept(new InventoryCloseEvent(player.getOpenInventory())));

        manager.setInventory(player, null);
        player.closeInventory();

        manager.setContents(player, null);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public InventoryType getType() {
        return type;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public boolean isCloseable() {
        return closeable;
    }

    public void setCloseable(boolean closeable) {
        this.closeable = closeable;
    }

    public InventoryProvider getProvider() {
        return provider;
    }

    public Optional<SmartInventory> getParent() {
        return Optional.ofNullable(parent);
    }

    public InventoryManager getManager() {
        return manager;
    }

    List<InventoryListener<? extends Event>> getListeners() {
        return listeners;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id = "unknown";
        private String title = "";
        private InventoryType type = InventoryType.CHEST;
        private int rows = 6, columns = 9;
        private boolean closeable = true;

        private InventoryManager manager;
        private InventoryProvider provider;
        private SmartInventory parent;

        private final List<InventoryListener<? extends Event>> listeners = new ArrayList<>();

        private Builder() {
            this.manager = VIAPI.get().getInventoryManager();
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder type(InventoryType type) {
            this.type = type;
            return this;
        }

        public Builder size(int rows, int columns) {
            this.rows = rows;
            this.columns = columns;
            return this;
        }

        public Builder closeable(boolean closeable) {
            this.closeable = closeable;
            return this;
        }

        public Builder provider(InventoryProvider provider) {
            this.provider = provider;
            return this;
        }

        public Builder parent(SmartInventory parent) {
            this.parent = parent;
            return this;
        }

        public Builder listener(InventoryListener<? extends Event> listener) {
            this.listeners.add(listener);
            return this;
        }

        public Builder manager(InventoryManager manager) {
            this.manager = manager;
            return this;
        }

        public SmartInventory build() {
            if(provider == null)
                throw new IllegalStateException("The provider of the SmartInventory.Builder must be set.");

            SmartInventory inv = new SmartInventory(this.manager != null ? this.manager : VIAPI.get().getInventoryManager());
            inv.id = this.id;
            inv.title = this.title;
            inv.type = this.type;
            inv.rows = this.rows;
            inv.columns = this.columns;
            inv.closeable = this.closeable;
            inv.provider = this.provider;
            inv.parent = this.parent;
            inv.listeners = this.listeners;

            return inv;
        }
    }
}
