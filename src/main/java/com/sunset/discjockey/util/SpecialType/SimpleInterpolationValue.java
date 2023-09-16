package com.sunset.discjockey.util.SpecialType;

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

    public void interpolate() {
        if (_oVal != _dVal) {
            _oVal = MathMisc.linearInterpolate(_oVal, _dVal, r);
        }
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
    public void onServerTick() {
        if (Math.abs(this._dVal - this._oVal) > SimpleInterpolationValue.threshold) {
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
        if (Math.abs(this._dVal - this._oVal) > SimpleInterpolationValue.threshold) {
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
