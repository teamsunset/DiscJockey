package com.sunset.discjockey.util.TouchMap.Vec2Type;

import net.minecraft.core.Direction;

public class PlaneRange
{
    public enum RangeReferenceAxis
    {
        X,
        Z
    }

    public Vec2Plane p1;
    public Vec2Plane p2;

    public RangeReferenceAxis rangeReferenceAxis;

    public PlaneRange() {
        this(new Vec2Plane(), new Vec2Plane(), RangeReferenceAxis.X);
    }

    public PlaneRange(PlaneRange planeRange) {
        this(new Vec2Plane(planeRange.p1), new Vec2Plane(planeRange.p2), planeRange.rangeReferenceAxis);
    }

    public PlaneRange(Vec2Plane p1, Vec2Plane p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.rangeReferenceAxis = RangeReferenceAxis.X;
        sort();
    }

    public PlaneRange(Vec2Plane p1, Vec2Plane p2, RangeReferenceAxis rangeReferenceAxis) {
        this.p1 = p1;
        this.p2 = p2;
        this.rangeReferenceAxis = rangeReferenceAxis;
        sort();
    }

//    public PlaneRange set(Vec2Plane p1, Vec2Plane p2) {
//        this.p1 = p1;
//        this.p2 = p2;
//        return this;
//    }

    public boolean isInRange(Vec2Plane p) {
        return p.x >= Math.min(p1.x, p2.x) && p.x <= Math.max(p1.x, p2.x) && p.z >= Math.min(p1.z, p2.z) && p.z <= Math.max(p1.z, p2.z);
    }

    /**
     * @param p point
     * @return 0~1
     */
    public double getValueInRange(Vec2Plane p) {
        return switch (rangeReferenceAxis) {
            case X -> (p.x - Math.min(p1.x, p2.x)) / (Math.max(p1.x, p2.x) - Math.min(p1.x, p2.x));
            case Z -> (p.z - Math.min(p1.z, p2.z)) / (Math.max(p1.z, p2.z) - Math.min(p1.z, p2.z));
        };
    }

    public PlaneRange sort() {
        if (p1.x > p2.x)
            Vec2Plane.swapX(p1, p2);
        if (p1.z > p2.z)
            Vec2Plane.swapZ(p1, p2);
        return this;
    }

    //Anticlockwise by center point
    public PlaneRange rotate(int degree, Vec2Plane centerPoint) {
        return switch (degree) {
            case 0 -> new PlaneRange(this);

            case 90, -270 -> new PlaneRange(
                    p1.rotate(90, centerPoint),
                    p2.rotate(90, centerPoint),
                    rangeReferenceAxis == RangeReferenceAxis.X ? RangeReferenceAxis.Z : RangeReferenceAxis.X
            );

            case 180, -180 -> new PlaneRange(
                    p1.rotate(180, centerPoint),
                    p2.rotate(180, centerPoint)
            );

            case 270, -90 -> new PlaneRange(
                    p1.rotate(270, centerPoint),
                    p2.rotate(270, centerPoint),
                    rangeReferenceAxis == RangeReferenceAxis.X ? RangeReferenceAxis.Z : RangeReferenceAxis.X
            );

            default -> {
                new IllegalStateException("Unexpected value: " + degree).printStackTrace();
                yield new PlaneRange(this);
            }
        };
    }

    public PlaneRange rotate(Direction direction, Vec2Plane centerPoint) {
        PlaneRange planeRange = new PlaneRange(this);

        try {
            switch (direction) {
                case NORTH, WEST, SOUTH, EAST ->
                        planeRange = rotate(Vec2Plane.DIRECTION_DEGREE_MAP.get(direction), centerPoint);
                default -> throw new IllegalStateException("Unexpected value: " + direction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return planeRange;
    }
}
