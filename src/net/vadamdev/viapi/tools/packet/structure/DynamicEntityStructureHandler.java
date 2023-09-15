package net.vadamdev.viapi.tools.packet.structure;

import net.vadamdev.viapi.tools.packet.IEntityStructure;
import net.vadamdev.viapi.tools.packet.handler.DynamicPacketEntityHandler;

/**
 * @author VadamDev
 * @since 05/09/2023
 */
public class DynamicEntityStructureHandler extends DynamicPacketEntityHandler {
    private final IEntityStructure entityStructure;

    public DynamicEntityStructureHandler(IEntityStructure entityStructure, int viewRadius, int period) {
        super(entityStructure, viewRadius, period);

        this.entityStructure = entityStructure;
    }

    public void updateLocation() {
        entityStructure.updateLocation(viewers);
    }

    public void rotateAxisXLocal(double angle) {
        entityStructure.rotateAxisXLocal(angle);
    }

    public void rotateAxisYLocal(double angle) {
        entityStructure.rotateAxisYLocal(angle);
    }

    public void rotateAxisZLocal(double angle) {
        entityStructure.rotateAxisZLocal(angle);
    }

    public void rotateLocal(double angleX, double angleY, double angleZ) {
        entityStructure.rotateLocal(angleX, angleY, angleZ);
    }
}
