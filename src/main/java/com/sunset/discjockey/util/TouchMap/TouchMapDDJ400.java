package com.sunset.discjockey.util.TouchMap;

import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;
import com.sunset.discjockey.util.TouchMap.Vec2Type.Vec2Plane;

public class TouchMapDDJ400 extends AbstractTouchMap
{
    public static PlaneRange MIDDLE_BLADE_FADER = new PlaneRange(new Vec2Plane(-1 / 16D, -4.5 / 16D), new Vec2Plane(1 / 16D, -3.5 / 16D), PlaneRange.RangeReferenceAxis.X);
}
