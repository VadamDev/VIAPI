package net.vadamdev.viapi.tools.particle;

import net.vadamdev.viapi.tools.enums.EnumDirection;
import net.vadamdev.viapi.tools.math.MathF;
import net.vadamdev.viapi.tools.utils.TriConsumer;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.security.InvalidParameterException;
import java.util.function.BiConsumer;

/**
 * @author VadamDev
 * @since 24/08/2023
 */
public final class ParticlePolygons {
    private ParticlePolygons() {
    }

    public static void drawLine(ParticleEffect particle, Location start, Location goal, float spacing, float speed) {
        if (!start.getWorld().equals(goal.getWorld()))
            throw new InvalidParameterException("start and goal must be in the same world !");

        final Vector v1 = start.toVector();
        final Vector f = goal.toVector().subtract(v1).normalize().multiply(spacing);

        for (double length = 0; length < start.distance(goal); v1.add(f), length += spacing)
            particle.display(0, 0, 0, speed, 1, v1.toLocation(start.getWorld()), 32);
    }

    public static void drawCycle(ParticleEffect particle, Location location, EnumDirection direction, float radius, float spacing, float speed) {
        final BiConsumer<Float, Float> consumer = generate(particle, location, direction, speed);

        for(float t = 0.0f; t < 50.0f; t += spacing)
            consumer.accept(radius * MathF.sin(t), radius * MathF.cos(t));
    }

    public static void drawSphere(ParticleEffect particle, Location location, float radius, float speed) {
        for (int i = 0; i < radius * 125; i++) {
            final double theta = 2 * Math.PI * Math.random();
            final double phi = Math.acos(2 * Math.random() - 1);

            double x = radius * Math.sin(phi) * Math.cos(theta);
            double y = radius * Math.sin(phi) * Math.sin(theta);
            y += radius / 2;
            double z = radius * Math.cos(phi);

            particle.display(0, 0, 0, speed, 1, location.clone().add(x, y, z), 32);
        }
    }

    public static void drawSquare(ParticleEffect particle, Location location, EnumDirection direction, float width, float height, float spacing, float speed) {
        final float halfWidth = width / 2;
        final float halfHeight = height / 2;

        final BiConsumer<Float, Float> consumer = generate(particle, location, direction, speed);

        for(float oX = 0; oX < width; oX += spacing) {
            consumer.accept(oX - halfWidth, halfHeight);
            consumer.accept(oX - halfWidth, -halfHeight);
        }

        for(float oY = 0; oY < height; oY += spacing) {
            consumer.accept(halfWidth, oY - halfHeight);
            consumer.accept(-halfWidth, oY - halfHeight);
        }
    }

    public static void drawCube(ParticleEffect particle, Location location, float width, float height, float depth, float spacing, float speed) {
        final float halfWidth = width / 2;
        final float halfDepth = depth / 2;

        final TriConsumer<Float, Float, Float> consumer = (a, b, c) -> particle.display(0, 0, 0, speed, 1, location.clone().add(a, b, c), 32);

        for(float oX = 0; oX < width; oX += spacing) {
            consumer.accept(oX - halfWidth, 0f, halfDepth);
            consumer.accept(oX - halfWidth, 0f, -halfDepth);

            consumer.accept(oX - halfWidth, height, halfDepth);
            consumer.accept(oX - halfWidth, height, -halfDepth);
        }

        for(float oY = 0; oY < height; oY += spacing) {
            consumer.accept(-halfWidth, oY, -halfDepth);
            consumer.accept(halfWidth, oY, -halfDepth);
            consumer.accept(-halfWidth, oY, halfDepth);
            consumer.accept(halfWidth, oY, halfDepth);
        }

        for(float oZ = 0; oZ < depth; oZ += spacing) {
            consumer.accept(halfWidth, 0f, oZ - halfDepth);
            consumer.accept(-halfWidth, 0f, oZ - halfDepth);

            consumer.accept(halfWidth, height, oZ - halfDepth);
            consumer.accept(-halfWidth, height, oZ - halfDepth);
        }
    }

    private static BiConsumer<Float, Float> generate(ParticleEffect particle, Location location, EnumDirection direction, float speed) {
        BiConsumer<Float, Float> consumer = (a, b) -> System.err.println("Invalid Direction");
        switch(direction) {
            case UP: case DOWN:
                consumer = (a, b) -> particle.display(0, 0, 0, speed, 1, location.clone().add(a, 0, b), 32);
                break;
            case SOUTH: case NORTH:
                consumer = (a, b) -> particle.display(0, 0, 0, speed, 1, location.clone().add(a, b, 0), 32);
                break;
            case EAST: case WEST:
                consumer = (a, b) -> particle.display(0, 0, 0, speed, 1, location.clone().add(0, b, a), 32);
                break;
            default:
                break;
        }

        return consumer;
    }
}
