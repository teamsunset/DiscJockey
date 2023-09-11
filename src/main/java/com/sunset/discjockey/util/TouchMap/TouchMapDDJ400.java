package com.sunset.discjockey.util.TouchMap;

import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;
import com.sunset.discjockey.util.TouchMap.Vec2Type.Vec2Plane;

public class TouchMapDDJ400 extends AbstractTouchMap {
    public static PlaneRange MIX_FADER = new PlaneRange(new Vec2Plane(-1 / 16D, -4.5 / 16D), new Vec2Plane(1 / 16D, -3.5 / 16D), PlaneRange.RangeReferenceAxis.X);
    public static PlaneRange LEFT_PLAY_BUTTON = new PlaneRange(new Vec2Plane(7 / 16D, -5 / 16D), new Vec2Plane(8 / 16D, -4 / 16D));

    public static PlaneRange LEFT_CUE_BUTTON = new PlaneRange(new Vec2Plane(7 / 16D, -3 / 16D), new Vec2Plane(8 / 16D, -2 / 16D));
    public static PlaneRange RIGHT_PLAY_BUTTON = new PlaneRange(new Vec2Plane(-8 / 16D, -4 / 16D), new Vec2Plane(7 / 16D, -3 / 16D));

}
