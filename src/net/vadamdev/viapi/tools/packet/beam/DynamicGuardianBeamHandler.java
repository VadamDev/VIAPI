package net.vadamdev.viapi.tools.packet.beam;

import net.vadamdev.viapi.tools.packet.handler.DynamicPacketEntityHandler;
import org.bukkit.Location;

/**
 * @author VadamDev
 * @since 03/09/2023
 */
public class DynamicGuardianBeamHandler extends DynamicPacketEntityHandler {
    private final GuardianBeam guardianBeam;

    public DynamicGuardianBeamHandler(GuardianBeam guardianBeam, int viewRadius, int period) {
        super(guardianBeam, viewRadius, period);

        this.guardianBeam = guardianBeam;
    }

    public void updateStartPosition(Location startingPosition) {
        guardianBeam.updateStartPositionLocal(startingPosition);
        guardianBeam.updateStartPosition(startingPosition, viewers);
    }

    public void updateEndingPosition(Location endingPosition) {
        guardianBeam.updateStartPositionLocal(endingPosition);
        guardianBeam.updateStartPosition(endingPosition, viewers);
    }
}
