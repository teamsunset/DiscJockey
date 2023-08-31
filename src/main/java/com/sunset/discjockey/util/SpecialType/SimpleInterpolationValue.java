package com.sunset.discjockey.util.SpecialType;

import com.sunset.discjockey.util.Reference;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Vector;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SimpleInterpolationValue
{
    public static Vector<SimpleInterpolationValue> VALUES = new Vector<>();

    public double _oVal;

    public double _dVal;

    public Runnable functionOnValueChanged = null;

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

    public void set(double val) {
        this._oVal = val;
        this._dVal = val;
    }

    public void setTarget(double _dVal) {
        this._dVal = _dVal;
    }

    //interpolate at the end of each tick
    @SubscribeEvent
    public static void onLevelTick(TickEvent.ServerTickEvent event) {
        if (event.side.isServer() && event.phase == TickEvent.Phase.END) {
            for (SimpleInterpolationValue value : VALUES) {
                if (Math.abs(value._dVal - value._oVal) > SimpleInterpolationValue.threshold) {
                    value.interpolate();
                    if (value.functionOnValueChanged != null) {
                        value.functionOnValueChanged.run();
                    }
                }
            }
        }
    }

}
