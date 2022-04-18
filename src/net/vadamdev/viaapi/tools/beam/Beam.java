package net.vadamdev.viaapi.tools.beam;

import com.google.common.base.Preconditions;
import net.vadamdev.viaapi.VIAPI;
import net.vadamdev.viaapi.tools.beam.protocol.LocationTargetBeam;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

/**
 * Creates a guardian beam between two locations.
 * This uses ProtocolLib to send two entities: A guardian and a squid.
 * The guardian is then set to target the squid.
 * @author Jaxon A Brown, updated by VadamDev
 */
public class Beam {
    private final UUID worldUID;
    private final double viewingRadiusSquared;
    private final long updateDelay;

    private boolean isActive;
    private final LocationTargetBeam beam;
    private Location startingPosition, endingPosition;
    private final Set<UUID> viewers;

    private BukkitRunnable runnable;

    /**
     * Create a guardian beam for anyone to see. This sets up the packets.
     * @param startingPosition Position to start the beam, or the position which the effect 'moves towards'.
     * @param endingPosition Position to stop the beam, or the position which the effect 'moves away from'.
     * @param viewingRadius Radius from either node of the beam from which it can be seen.
     * @param updateDelay Delay between checking if the beam should be hidden or shown to potentially applicable players.
     */
    public Beam(Location startingPosition, Location endingPosition, double viewingRadius, long updateDelay) {
        Preconditions.checkState(startingPosition.getWorld().equals(endingPosition.getWorld()), "startingPosition and endingPosition must be in the same world");
        Preconditions.checkArgument(viewingRadius > 0, "viewingRadius must be positive");
        Preconditions.checkArgument(updateDelay >= 1, "viewingRadius must be a natural number");

        this.worldUID = startingPosition.getWorld().getUID();
        this.viewingRadiusSquared = viewingRadius * viewingRadius;
        this.updateDelay = updateDelay;

        this.isActive = false;
        this.beam = new LocationTargetBeam(startingPosition, endingPosition);
        this.startingPosition = startingPosition;
        this.endingPosition = endingPosition;
        this.viewers = new HashSet<>();
    }

    /**
     * Send the packets to create the beam to applicable players.
     * This also starts the runnable which will make the effect visible if it becomes applicable to a player.
     */
    public void start() {
        Preconditions.checkState(!isActive, "The beam must be disabled in order to start it");

        isActive = true;
        runnable = new BeamUpdater(updateDelay);
    }

    /**
     * Send the packets to remove the beam from the player, if applicable.
     * This also stops the runnable.
     */
    public void stop() {
        Preconditions.checkState(isActive, "The beam must be enabled in order to stop it");

        isActive = false;

        viewers.stream().filter(uuid -> {
            Player player = Bukkit.getPlayer(uuid);
            return player != null && player.getWorld().getUID().equals(this.worldUID) && isCloseEnough(player.getLocation());
        }).forEach(uuid -> beam.cleanup(Bukkit.getPlayer(uuid)));

        viewers.clear();
        runnable.cancel();
        runnable = null;
    }

    /**
     * Sets the starting position of the beam, or the position which the effect 'moves towards'.
     * @param location the starting position.
     */
    public void setStartingPosition(Location location) {
        Preconditions.checkArgument(location.getWorld().getUID().equals(worldUID), "location must be in the same world as this beam");

        startingPosition = location;

        Iterator<UUID> iterator = viewers.iterator();
        while(iterator.hasNext()) {
            Player player = Bukkit.getPlayer(iterator.next());

            if(player == null || !player.isOnline() || !player.getWorld().getUID().equals(worldUID) || !isCloseEnough(player.getLocation())) {
                iterator.remove();
                continue;
            }

            beam.setStartingPosition(player, location);
        }
    }

    /**
     * Sets the ending position of the beam, or the position which the effect 'moves away from'.
     * @param location the ending position.
     */
    public void setEndingPosition(Location location) {
        Preconditions.checkArgument(location.getWorld().getUID().equals(worldUID), "location must be in the same world as this beam");

        endingPosition = location;

        Iterator<UUID> iterator = viewers.iterator();
        while(iterator.hasNext()) {
            Player player = Bukkit.getPlayer(iterator.next());

            if(!player.isOnline() || !player.getWorld().getUID().equals(worldUID) || !isCloseEnough(player.getLocation())) {
                iterator.remove();
                continue;
            }

            beam.setEndingPosition(player, location);
        }
    }

    /**
     * Checks if any packets need to be sent to show or hide the beam to any applicable player.
     */
    public void update() {
        if(isActive) {
            for(Player player : Bukkit.getOnlinePlayers()) {
                UUID uuid = player.getUniqueId();

                if(!player.getWorld().getUID().equals(worldUID)) {
                    viewers.remove(uuid);
                    return;
                }

                if(isCloseEnough(player.getLocation())) {
                    if(!viewers.contains(uuid)) {
                        beam.start(player);
                        viewers.add(uuid);
                    }
                }else if(viewers.contains(uuid)) {
                    beam.cleanup(player);
                    viewers.remove(uuid);
                }
            }
        }
    }

    /**
     * Checks if the beam is active (will show when applicable).
     * @return True if active.
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Checks if the player is currently viewing the beam.
     * @param player player to check
     * @return True if viewing.
     */
    public boolean isViewing(Player player) {
        return viewers.contains(player.getUniqueId());
    }

    private boolean isCloseEnough(Location location) {
        return startingPosition.distanceSquared(location) <= viewingRadiusSquared || endingPosition.distanceSquared(location) <= viewingRadiusSquared;
    }

    /**
     * @author Jaxon A Brown, updated by VadamDev
     */
    private class BeamUpdater extends BukkitRunnable {
        private BeamUpdater(long updateDelay) {
            this.runTaskTimerAsynchronously(VIAPI.get(), 0, updateDelay);
        }

        @Override
        public void run() {
            Beam.this.update();
        }
    }
}