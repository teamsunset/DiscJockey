package com.sunset.discjockey.util.TouchMap.Vec2Type;

import net.minecraft.core.Direction;

import java.util.Map;

public class Vec2Plane
{
    public static final Map<Direction, Integer> DIRECTION_DEGREE_MAP = Map.of(
            Direction.NORTH, 0,
            Direction.WEST, 90,
            Direction.SOUTH, 180,
            Direction.EAST, 270
    );

    public static final Vec2Plane ORIGIN = new Vec2Plane(0, 0);

    public double x;
    public double z;

    public Vec2Plane() {
        this.x = 0;
        this.z = 0;
    }

    public Vec2Plane(double x, double z) {
        this.x = x;
        this.z = z;
    }

    public Vec2Plane(Vec2Plane vec) {
        this.x = vec.x;
        this.z = vec.z;
    }

    public Vec2Plane set(double x, double z) {
        this.x = x;
        this.z = z;
        return this;
    }

    public Vec2Plane rotate(int degree, Vec2Plane centerPoint) {
        return switch (degree) {
            case 0 -> new Vec2Plane(this);

            case 90, -270 -> new Vec2Plane(
                    centerPoint.x + (this.z - centerPoint.z),
                    centerPoint.z - (this.x - centerPoint.x)
            );

            case 180, -180 -> new Vec2Plane(
                    centerPoint.x - (this.x - centerPoint.x),
                    centerPoint.z - (this.z - centerPoint.z)
            );

            case 270, -90 -> new Vec2Plane(
                    centerPoint.x - (this.z - centerPoint.z),
                    centerPoint.z + (this.x - centerPoint.x)
            );

            default -> {
                new IllegalStateException("Unexpected value: " + degree).printStackTrace();
                yield new Vec2Plane(this);
            }
        };
    }

    public Vec2Plane rotate(Direction direction, Vec2Plane centerPoint) {
        return switch (direction) {
            case NORTH, WEST, SOUTH, EAST -> rotate(DIRECTION_DEGREE_MAP.get(direction), centerPoint);
            default -> {
                new IllegalStateException("Unexpected value: " + direction).printStackTrace();
                yield rotate(0, centerPoint);
            }
        };
    }

    public static void swap(Vec2Plane p1, Vec2Plane p2) {
        Vec2Plane temp = p1;
        p1 = p2;
        p2 = temp;
    }

    public static void swapX(Vec2Plane p1, Vec2Plane p2) {
        double temp = p1.x;
        p1.x = p2.x;
        p2.x = temp;
    }

    public static void swapZ(Vec2Plane p1, Vec2Plane p2) {
        double temp = p1.z;
        p1.z = p2.z;
        p2.z = temp;
    }
}