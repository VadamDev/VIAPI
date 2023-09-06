package net.vadamdev.viapi.tools.packet;

/**
 * @author VadamDev
 * @since 05/09/2023
 */
public interface IEntityStructure extends IPacketEntity {
    void rotateAxisXLocal(double angle);
    void rotateAxisYLocal(double angle);
    void rotateAxisZLocal(double angle);

    void rotateLocal(double angleX, double angleY, double angleZ);
}
