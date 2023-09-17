package com.sunset.discjockey.util.SpecialType;

import org.joml.Math;

//@Mod.EventBusSubscriber(modid = ModReference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SimpleInterpolationValue {

    public double _oVal;

    public double _dVal;

    public Runnable onServerInterpolate = null;

    public Runnable onClientInterpolate = null;

    public OneShotBoolean isSetCalled = new OneShotBoolean();

    //interpolation rate
    public static double r = 0.6;

    public static double threshold = 0.01;

    public SimpleInterpolationValue() {
        this(0.0, 0.0);
    }

    public SimpleInterpolationValue(double _oVal, double _dVal) {
        this._oVal = _oVal;
        this._dVal = _dVal;
    }

    public SimpleInterpolationValue interpolate() {
        if (_oVal != _dVal) {
            _oVal = MathMisc.linearInterpolate(_oVal, _dVal, r);
        }
        return this;
    }

    public double interpolateFuture() {
        if (_oVal != _dVal) {
            return MathMisc.linearInterpolate(_oVal, _dVal, r);
        } else {
            return _oVal;
        }
    }

    public double interpolate(double r) {
        double val = _oVal;
        if (_oVal != _dVal) {
            val = MathMisc.linearInterpolate(_oVal, _dVal, Math.clamp(r, 0.0, 1.0));
        }
        return val;
    }

    public double get() {
        return _oVal;
    }

    public double getTarget() {
        return _dVal;
    }

    public void set(double val) {
        this._oVal = val;
        this._dVal = val;
        this.isSetCalled.set(true);
    }

    public void setTarget(double _dVal) {
        this._dVal = _dVal;
    }

    //interpolate at the end of each tick
//    @SubscribeEvent

    public boolean check() {
        return Math.abs(this._dVal - this._oVal) > SimpleInterpolationValue.threshold;
    }

    public void onServerTick() {
        if (this.check()) {
            this.interpolate();
            if (this.onServerInterpolate != null) {
                this.onServerInterpolate.run();
            }
        } else if (this.isSetCalled.get()) {
            if (this.onServerInterpolate != null) {
                this.onServerInterpolate.run();
            }
        }
    }

    public void onClientTick() {
        if (this.check()) {
            this.interpolate();
            if (this.onClientInterpolate != null) {
                this.onClientInterpolate.run();
            }
        } else if (this.isSetCalled.get()) {
            if (this.onClientInterpolate != null) {
                this.onClientInterpolate.run();
            }
        }
    }

}
