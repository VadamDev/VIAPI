package net.vadamdev.viaapi.tools.entitystructure;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * @author VadamDev
 * @since 10/08/2022
 */
public interface IEntityStructure {
    void spawn(Player... players);
    void delete(Player... players);

    void updateLocalPosition(Location location);
    void updateLocationAndRotation(Player... players);

    void updateLocalRotationAroundAxisX(double angle);
    void updateLocalRotationAroundAxisY(double angle);
    void updateLocalRotationAroundAxisZ(double angle);
    void updateLocalRotation(double angleX, double angleY, double angleZ);

    Location getLocation();
}
