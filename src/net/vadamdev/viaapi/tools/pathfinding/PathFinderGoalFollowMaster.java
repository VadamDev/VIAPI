package net.vadamdev.viaapi.tools.pathfinding;

import net.minecraft.server.v1_8_R3.*;

public class PathFinderGoalFollowMaster extends PathfinderGoal {
    private final EntityInsentient follower;
    private EntityLiving owner;

    private final double speed;
    private final float distance;

    private double x, y, z;

    public PathFinderGoalFollowMaster(EntityInsentient follower, double speed, float distance) {
        this.follower = follower;
        this.speed = speed;
        this.distance = distance;
        this.owner = follower.getGoalTarget();

        this.a(3);
    }

    @Override
    public boolean a() {
        if(owner == null || this.follower.getCustomName() == null || owner.h(follower) <= distance * distance) return false;

        this.x = owner.locX;
        this.y = owner.locY;
        this.z = owner.locZ;

        return true;
    }

    @Override
    public void c() {
        follower.getNavigation().a(x, y, z, speed);
    }

    @Override
    public boolean b() {
        return !follower.getNavigation().m() && owner.h(follower) < distance * distance;
    }
}
