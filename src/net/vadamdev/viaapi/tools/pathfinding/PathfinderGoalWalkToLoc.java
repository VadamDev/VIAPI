package net.vadamdev.viaapi.tools.pathfinding;

import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.Navigation;
import net.minecraft.server.v1_8_R3.PathEntity;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import org.bukkit.Location;

/**
 * @author VadamDev
 */
public class PathfinderGoalWalkToLoc extends PathfinderGoal {
    private final float speed;
    private final Location loc;
    private final Navigation navigation;

    public PathfinderGoalWalkToLoc(float speed, EntityInsentient entity, Location loc) {
        this.speed = speed;
        this.loc = loc;
        this.navigation = (Navigation) entity.getNavigation();
    }

    @Override
    public boolean a() {
        return true;
    }

    @Override
    public boolean b() {
        return false;
    }

    @Override
    public void c() {
        PathEntity pE = this.navigation.a(loc.getX(), loc.getY(), loc.getZ());
        this.navigation.a(pE, speed);
    }
}
