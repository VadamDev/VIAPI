package net.vadamdev.viaapi.tools.beam.protocol;

import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Creates a guardian beam between two locations.
 * This uses ProtocolLib to send two entities: A guardian and a squid.
 * The guardian is then set to target the squid.
 * Be sure to run #cleanup for any players you #start.
 * @author Jaxon A Brown, edited by VadamDev
 */
public class LocationTargetBeam {
    private final WrappedBeamPacket packetSquidSpawn, packetSquidMove;
    private final WrappedBeamPacket packetGuardianSpawn, packetGuardianMove;
    private final WrappedBeamPacket packetRemoveEntities;

    /**
     * Create a guardian beam. This sets up the packets.
     * @param startingPosition Position to start the beam, or the position which the effect 'moves towards'.
     * @param endingPosition Position to stop the beam, or the position which the effect 'moves away from'.
     */
    public LocationTargetBeam(Location startingPosition, Location endingPosition) {
        Preconditions.checkState(startingPosition.getWorld().equals(endingPosition.getWorld()), "startingPosition and endingPosition must be in the same world");

        this.packetSquidSpawn = BeamPacketFactory.createPacketSquidSpawn(startingPosition);
        this.packetSquidMove = BeamPacketFactory.createPacketEntityMove(this.packetSquidSpawn);
        this.packetGuardianSpawn = BeamPacketFactory.createPacketGuardianSpawn(endingPosition, this.packetSquidSpawn);
        this.packetGuardianMove = BeamPacketFactory.createPacketEntityMove(this.packetGuardianSpawn);
        this.packetRemoveEntities = BeamPacketFactory.createPacketRemoveEntities(this.packetSquidSpawn, this.packetGuardianSpawn);
    }

    /**
     * Send the packets to create the beam to the player.
     * @param player player to whom the beam will be sent.
     */
    public void start(Player player) {
        packetSquidSpawn.send(player);
        packetGuardianSpawn.send(player);
    }

    /**
     * Sets the position of the beam which the effect 'moves away from'.
     * @param player player who should receive the update. They MUST have been showed the beam already.
     * @param location location of the new position.
     */
    public void setStartingPosition(Player player, Location location) {
        BeamPacketFactory.modifyPacketEntitySpawn(packetSquidSpawn, location);
        BeamPacketFactory.modifyPacketEntityMove(packetSquidMove, location).send(player);
    }

    /**
     * Sets the position of the beam which the effect 'moves towards'.
     * @param player player who should receive the update. They MUST have been showed the beam already.
     * @param location location of the new position.
     */
    public void setEndingPosition(Player player, Location location) {
        BeamPacketFactory.modifyPacketEntitySpawn(packetGuardianSpawn, location);
        BeamPacketFactory.modifyPacketEntityMove(packetGuardianMove, location).send(player);
    }

    /**
     * Cleans up the entities on the player's side.
     * @param player player who needs the cleanup.
     */
    public void cleanup(Player player) {
        packetRemoveEntities.send(player);
    }
}
