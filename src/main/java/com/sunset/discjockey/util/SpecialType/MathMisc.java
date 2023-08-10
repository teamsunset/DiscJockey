package com.sunset.discjockey.util.SpecialType;

public class MathMisc
{
    public static double linearInterpolate(double _oVal, double _dVal, double r) {
        return _oVal + (_dVal - _oVal) * r;
    }

    public static double nonLinearInterpolate(double _oVal, double _dVal, double r, double n) {
        return linearInterpolate(_oVal, _dVal, 1 - java.lang.Math.pow(r, n));
    }
}
