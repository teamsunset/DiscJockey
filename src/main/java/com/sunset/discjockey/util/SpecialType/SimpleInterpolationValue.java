package com.sunset.discjockey.util.SpecialType;

import com.sunset.discjockey.util.Reference;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SimpleInterpolationValue
{
    public static ArrayList<SimpleInterpolationValue> VALUES = new ArrayList<>();

    public double _oVal;

    public double _dVal;

    public static double r = 0.5;

    public SimpleInterpolationValue(double _oVal, double _dVal) {
        this._oVal = _oVal;
        this._dVal = _dVal;
        VALUES.add(this);
    }

    @Override
    public void finalize() throws Throwable {
        VALUES.remove(this);
        super.finalize();
    }

    public void interpolate() {
        if (_oVal != _dVal) {
            _oVal = MathMisc.linearInterpolate(_oVal, _dVal, r);
        }
    }

    public double getVal() {
        return _oVal;
    }

    public void setVal(double _dVal) {
        this._dVal = _dVal;
    }

    //interpolate at the end of each tick
    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (!event.level.isClientSide() && event.phase == TickEvent.Phase.END) {
            for (SimpleInterpolationValue value : VALUES) {
                value.interpolate();
            }
        }
    }
}
