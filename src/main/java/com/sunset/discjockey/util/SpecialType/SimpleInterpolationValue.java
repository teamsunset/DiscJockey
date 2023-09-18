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

    public static double renderR = 0.8;

    public static double threshold = 0.01;


    //is used to monoInterpolate
    double prePartialTick = 0;

    double totalPartialTick = 0;


    OneShotBoolean hasSetTarget = new OneShotBoolean();


    public SimpleInterpolationValue() {
        this(0.0, 0.0);
    }

    public SimpleInterpolationValue(double _oVal, double _dVal) {
        this._oVal = _oVal;
        this._dVal = _dVal;
    }

    public SimpleInterpolationValue interpolate() {
        if (_oVal != _dVal && Math.abs(_dVal - _oVal) > SimpleInterpolationValue.threshold) {
            _oVal = MathMisc.linearInterpolate(_oVal, _dVal, r);
        } else {
            _oVal = _dVal;
        }
        return this;
    }

    public double interpolateFuture() {
        if (_oVal != _dVal && Math.abs(_dVal - _oVal) > SimpleInterpolationValue.threshold) {
            return MathMisc.linearInterpolate(_oVal, _dVal, r);
        } else {
            return _dVal;
        }
    }

//    public double interpolate(double r) {
//        if (_oVal != _dVal && Math.abs(_dVal - _oVal) > SimpleInterpolationValue.threshold) {
//            return MathMisc.linearInterpolate(_oVal, _dVal, Math.clamp(r, 0.0, 1.0));
//        } else {
//            return _dVal;
//        }
//    }
//
//    public double monoInterpolate(double r) {
//        if (this.hasSetTarget.get()) {
//            this.prePartialTick = 0;
//        }
//
//        this.prePartialTick = Math.max(r, this.prePartialTick);
//        return this.interpolate(this.prePartialTick);
//    }

    public double nonLinerInterpolate(double n) {
        if (_oVal != _dVal && Math.abs(_dVal - _oVal) > SimpleInterpolationValue.threshold) {
            return MathMisc.nonLinearInterpolate(_oVal, _dVal, renderR, n);
        } else {
            return _dVal;
        }
    }

    public double monoNonLinerInterpolate(double n) {
        if (this.hasSetTarget.get()) {
            this.totalPartialTick = 0;
        }

        this.totalPartialTick += n;
        return this.nonLinerInterpolate(this.totalPartialTick);
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
        this.hasSetTarget.set(true);
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
