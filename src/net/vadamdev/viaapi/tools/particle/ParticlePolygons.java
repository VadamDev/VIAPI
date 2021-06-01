package net.vadamdev.viaapi.tools.particle;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

public class ParticlePolygons {
    /**
     * @author VadamDev & Implements
     * @since 09.10.2020
     */

    public void drawLine(ParticleEffect particle, Location loc1, Location loc2, float space) {
        World world = loc1.getWorld();
        double distance = loc1.distance(loc2);

        Vector v1 = loc1.toVector();
        Vector v2 = loc2.toVector();

        Vector f = v2.clone().subtract(v1).normalize().multiply(space);

        for(double length = 0; length < distance; v1.add(f)) {
            particle.display(0, 0, 0, 0.2f, 1, new Location(world, v1.getX(), v1.getY(), v1.getZ()), (List<Player>) Bukkit.getOnlinePlayers());
            length += space;
        }
    }

    public void drawCycle(ParticleEffect particle, Location center, float radius, ParticleDirection direction) {
        if(direction.equals(ParticleDirection.FLOOR)) {
            for (double t = 0.0D; t < 50.0D; t += 0.5D) {
                float x = radius * (float)Math.sin(t);
                float z = radius * (float)Math.cos(t);

                particle.display(0, 0, 0, 1, 1, new Location(center.getWorld(), center.getX() + x, center.getY(), center.getZ() + z));
            }
        }else if(direction.equals(ParticleDirection.SOUTH) || direction.equals(ParticleDirection.NORTH)) {
            for (double t = 0.0D; t < 50.0D; t += 0.5D) {
                float x = radius * (float)Math.sin(t);
                float y = radius * (float)Math.cos(t);

                particle.display(0, 0, 0, 1, 1, new Location(center.getWorld(), center.getX() + x, center.getY() + y, center.getZ()));
            }
        }else if(direction.equals(ParticleDirection.EAST) || direction.equals(ParticleDirection.WEST)) {
            for (double t = 0.0D; t < 50.0D; t += 0.5D) {
                float z = radius * (float)Math.sin(t);
                float y = radius * (float)Math.cos(t);

                particle.display(0, 0, 0, 1, 1, new Location(center.getWorld(), center.getX(), center.getY() + y, center.getZ() + z));
            }
        }
    }

    public void drawSphere(ParticleEffect particle, Location center,  float radius) {
        int pn = (int) radius * 125;
        for(int t1 = 0; t1 < pn; t1++) {
            double theta = 2 * Math.PI * Math.random();
            double phi = Math.acos(2 * Math.random() - 1);

            double x = radius * Math.sin(phi) * Math.cos(theta);
            double y = radius * Math.sin(phi) * Math.sin(theta);
            y += radius / 2;
            double z = radius * Math.cos(phi);

            particle.display(0, 0, 0, 1, 1, center.clone().add(x, y, z));
        }
    }

    public void drawSquare(ParticleEffect particle, Location loc, double distance, ParticleDirection direction) {
        if(direction.equals(ParticleDirection.FLOOR)) {
            drawLine(particle, loc, new Location(loc.getWorld(), loc.getX() + distance, loc.getY(), loc.getZ()), 0.15f);
            drawLine(particle, loc, new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() + distance), 0.15f);
            Location newLoc = loc.clone().add(distance, 0, distance);
            drawLine(particle, newLoc, new Location(loc.getWorld(), newLoc.getX() - distance, newLoc.getY(), newLoc.getZ()), 0.15f);
            drawLine(particle, newLoc, new Location(loc.getWorld(), newLoc.getX(), newLoc.getY(), newLoc.getZ() - distance), 0.15f);
        }else if(direction.equals(ParticleDirection.SOUTH) || direction.equals(ParticleDirection.NORTH)) {
            drawLine(particle, loc, new Location(loc.getWorld(), loc.getX() + distance, loc.getY(), loc.getZ()), 0.15f);
            drawLine(particle, loc, new Location(loc.getWorld(), loc.getX(), loc.getY() + distance, loc.getZ()), 0.15f);
            Location newLoc = loc.clone().add(distance, distance, 0);
            drawLine(particle, newLoc, new Location(loc.getWorld(), newLoc.getX() - distance, newLoc.getY(), newLoc.getZ()), 0.15f);
            drawLine(particle, newLoc, new Location(loc.getWorld(), newLoc.getX(), newLoc.getY() - distance, newLoc.getZ()), 0.15f);
        }else if(direction.equals(ParticleDirection.EAST) || direction.equals(ParticleDirection.WEST)) {
            drawLine(particle, loc, new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() + distance), 0.15f);
            drawLine(particle, loc, new Location(loc.getWorld(), loc.getX(), loc.getY() + distance, loc.getZ()), 0.15f);
            Location newLoc = loc.clone().add(0, distance, distance);
            drawLine(particle, newLoc, new Location(loc.getWorld(), newLoc.getX(), newLoc.getY(), newLoc.getZ() - distance), 0.15f);
            drawLine(particle, newLoc, new Location(loc.getWorld(), newLoc.getX(), newLoc.getY() - distance, newLoc.getZ()), 0.15f);
        }
    }

    public void drawCube(ParticleEffect particle, Location loc1, float space, double distance) {
        World world = loc1.getWorld();

        drawLine(particle, loc1, new Location(loc1.getWorld(), loc1.getX() + distance, loc1.getY(), loc1.getZ()), space);
        drawLine(particle, loc1, new Location(loc1.getWorld(), loc1.getX(), loc1.getY(), loc1.getZ() + distance), space);
        Location newLoc = loc1.clone().add(distance, 0, distance);
        drawLine(particle, loc1, new Location(loc1.getWorld(), loc1.getX(), loc1.getY() + distance, loc1.getZ()), space);
        drawLine(particle, newLoc, new Location(loc1.getWorld(), newLoc.getX() - distance, newLoc.getY(), newLoc.getZ()), space);
        drawLine(particle, newLoc, new Location(loc1.getWorld(), newLoc.getX(), newLoc.getY(), newLoc.getZ() - distance), space);

        drawLine(particle, loc1.clone().add(0,distance,0), new Location(loc1.getWorld(), loc1.getX() + distance, loc1.getY() + distance, loc1.getZ()), space);
        drawLine(particle, loc1.clone().add(0,distance,0), new Location(loc1.getWorld(), loc1.getX(), loc1.getY() + distance, loc1.getZ() + distance), space);
        Location newLoc1 = loc1.clone().add(distance, distance, distance);
        drawLine(particle, newLoc, new Location(loc1.getWorld(), newLoc.getX(), newLoc.getY() + distance, newLoc.getZ()), space);
        drawLine(particle, newLoc1.add(-distance, 0, 0), new Location(newLoc1.getWorld(), newLoc1.getX(), newLoc1.getY() - distance, newLoc1.getZ()), space);
        drawLine(particle, newLoc1.add(distance, 0, -distance), new Location(newLoc1.getWorld(), newLoc1.getX(), newLoc1.getY() - distance, newLoc1.getZ()), space);
        drawLine(particle, newLoc.clone().add(0,distance,0), new Location(loc1.getWorld(), newLoc.getX() - distance, newLoc.getY() + distance, newLoc.getZ()), space);
        drawLine(particle, newLoc.clone().add(0,distance,0), new Location(loc1.getWorld(), newLoc.getX(), newLoc.getY() + distance, newLoc.getZ() - distance), space);
    }
}
