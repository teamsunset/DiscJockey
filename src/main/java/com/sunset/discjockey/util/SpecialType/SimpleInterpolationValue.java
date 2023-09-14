package com.sunset.discjockey.util.SpecialType;

import net.minecraftforge.event.TickEvent;

import java.util.Vector;

//@Mod.EventBusSubscriber(modid = ModReference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SimpleInterpolationValue {
    public static WeakCollection<SimpleInterpolationValue> VALUES = new WeakCollection<>(new Vector<>());

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
        VALUES.add(this);
    }

    //invalid
//    @Override
//    public void finalize() throws Throwable {
//        VALUES.remove(this);
//        super.finalize();
//    }

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
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            VALUES.iterate(value -> {
                if (Math.abs(value._dVal - value._oVal) > SimpleInterpolationValue.threshold) {
                    value.interpolate();
                    if (value.onServerInterpolate != null) {
                        value.onServerInterpolate.run();
                    }
                } else if (value.isSetCalled.get()) {
                    if (value.onServerInterpolate != null) {
                        value.onServerInterpolate.run();
                    }
                }
            });
        }
    }

    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            VALUES.iterate(value -> {
                if (Math.abs(value._dVal - value._oVal) > SimpleInterpolationValue.threshold) {
                    value.interpolate();
                    if (value.onClientInterpolate != null) {
                        value.onClientInterpolate.run();
                    }
                } else if (value.isSetCalled.get()) {
                    if (value.onClientInterpolate != null) {
                        value.onClientInterpolate.run();
                    }
                }
            });
        }
    }

}
