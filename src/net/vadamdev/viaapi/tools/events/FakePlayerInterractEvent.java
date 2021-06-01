package net.vadamdev.viaapi.tools.events;

import net.vadamdev.viaapi.api.entities.EntityFakePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FakePlayerInterractEvent extends Event {
    private int id;
    private Player player;

    private EntityFakePlayer.NPCAction action;

    public FakePlayerInterractEvent(int id, Player player, EntityFakePlayer.NPCAction action) {
        this.id = id;
        this.player = player;
        this.action = action;
    }

    public int getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public EntityFakePlayer.NPCAction getAction() {
        return action;
    }

    public static final HandlerList handlers = new HandlerList();
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
