package com.sunset.discjockey.block.BlockEntity.Controller.Widget.AbstractWidget;

import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidget;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidgetManager;
import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;
import net.minecraft.nbt.CompoundTag;

public abstract class ControllerKnob extends ControllerWidget
{
    public double value = 0.0; //between -1 to 1

    public ControllerKnob(String id, PlaneRange planeRange) {
        super(id, ControllerWidgetManager.InteractType.SCROLL, planeRange);
    }

    @Override
    public abstract CompoundTag getCompoundTag();

    @Override
    public abstract void writeCompoundTag(CompoundTag compoundTag);
}
