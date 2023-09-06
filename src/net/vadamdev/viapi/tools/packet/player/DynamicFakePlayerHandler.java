package net.vadamdev.viapi.tools.packet.player;

import net.vadamdev.viapi.tools.packet.handler.DynamicPacketEntityHandler;

/**
 * @author VadamDev
 * @since 03/09/2023
 */
public class DynamicFakePlayerHandler extends DynamicPacketEntityHandler {
    private final PacketFakePlayer fakePlayer;

    public DynamicFakePlayerHandler(PacketFakePlayer fakePlayer, int viewRadius, int period) {
        super(fakePlayer, viewRadius, period);

        this.fakePlayer = fakePlayer;
    }

    public void setSneaking(boolean sneaking) {
        fakePlayer.setSneakingLocal(sneaking);
        fakePlayer.updateMetadata(viewers);
    }
}
