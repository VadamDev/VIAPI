package net.vadamdev.viaapi.tools.beam;

import com.google.common.base.Preconditions;
import net.vadamdev.viaapi.VIAPI;
import net.vadamdev.viaapi.tools.beam.protocol.LocationTargetBeam;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

/**
 * Creates a guardian beam between two locations.
 * This uses ProtocolLib to send two entities: A guardian and a squid.
 * The guardian is then set to target the squid.
 * @author Jaxon A Brown
 */
public class ClientBeam {
    private final UUID worldUID;
    private final double viewingRadiusSquared;
    private final long updateDelay;

    private boolean isActive;
    private final LocationTargetBeam beam;
    private Location startingPosition, endingPosition;
    private Player player;
    private boolean isViewing;

    private BukkitRunnable runnable;

    /**
     * Create a guardian beam for a specific player. This sets up the packets.
     * @param player Player who will see the beam.
     * @param startingPosition Position to start the beam, or the position which the effect 'moves towards'.
     * @param endingPosition Position to stop the beam, or the position which the effect 'moves away from'.
     */
    public ClientBeam(Player player, Location startingPosition, Location endingPosition) {
        this(player, startingPosition, endingPosition, 100D, 5);
    }

    /**
     * Create a guardian beam for a specific player. This sets up the packets.
     * @param player Player who will see the beam.
     * @param startingPosition Position to start the beam, or the position which the effect 'moves towards'.
     * @param endingPosition Position to stop the beam, or the position which the effect 'moves away from'.
     * @param viewingRadius Radius from either node of the beam from which it can be seen.
     * @param updateDelay Delay between checking if the beam should be hidden or shown to the player.
     */
    public ClientBeam(Player player, Location startingPosition, Location endingPosition, double viewingRadius, long updateDelay) {
        Preconditions.checkArgument(player.isOnline(), "The player must be online");
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

        this.player = player;
        this.isViewing = false;
    }

    /**
     * Send the packets to create the beam to the player, if applicable.
     * This also starts the runnable which will make the effect visible if it becomes applicable later.
     */
    public void start() {
        Preconditions.checkState(!isActive, "The beam must be disabled in order to start it");
        Preconditions.checkState(this.player != null && !player.isOnline(), "The player must be online");

        isActive = true;
        runnable = new ClientBeamUpdater(updateDelay);
    }

    /**
     * Send the packets to remove the beam from the player, if applicable.
     * This also stops the runnable.
     */
    public void stop() {
        Preconditions.checkState(this.isActive, "The beam must be enabled in order to stop it");

        isActive = false;
        isViewing = false;

        if(player != null && !player.isOnline())
            this.player = null;

        runnable.cancel();
        runnable = null;
    }

    /**
     * Sets the starting position of the beam, or the position which the effect 'moves towards'.
     * @param location the starting position.
     */
    public void setStartingPosition(Location location) {
        Preconditions.checkArgument(location.getWorld().getUID().equals(this.worldUID), "location must be in the same world as this beam");
        Preconditions.checkState(player != null && !player.isOnline(), "The player must be online");

        startingPosition = location;
        beam.setStartingPosition(player, location);
    }

    /**
     * Sets the ending position of the beam, or the position which the effect 'moves away from'.
     * @param location the ending position.
     */
    public void setEndingPosition(Location location) {
        Preconditions.checkArgument(location.getWorld().getUID().equals(worldUID), "location must be in the same world as this beam");
        Preconditions.checkState(player != null && !player.isOnline(), "The player must be online");

        endingPosition = location;
        beam.setEndingPosition(player, location);
    }

    /**
     * Checks if any packets need to be sent to show or hide the beam. Stops the beam if the player is offline.
     */
    public void update() {
        if(player == null || !player.isOnline())
            stop();

        if(isActive) {
            if(!player.getWorld().getUID().equals(worldUID))
                stop();

            if(isCloseEnough(player.getLocation())) {
                if(!isViewing) {
                    beam.start(player);
                    isViewing = true;
                }
            }else if(isViewing) {
                beam.cleanup(player);
                isViewing = false;
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
     * Checks if the player is currently viewing the beam (can the player see it).
     * @return True if viewing.
     */
    public boolean isViewing() {
        return isViewing;
    }

    private boolean isCloseEnough(Location location) {
        return startingPosition.distanceSquared(location) <= viewingRadiusSquared || endingPosition.distanceSquared(location) <= viewingRadiusSquared;
    }

    /**
     * @author Jaxon A Brown, edited by VadamDev
     */
    private class ClientBeamUpdater extends BukkitRunnable {
        private ClientBeamUpdater(long updateDelay) {
            this.runTaskTimerAsynchronously(VIAPI.get(), 0, updateDelay);
        }

        @Override
        public void run() {
            ClientBeam.this.update();
        }
    }
}
