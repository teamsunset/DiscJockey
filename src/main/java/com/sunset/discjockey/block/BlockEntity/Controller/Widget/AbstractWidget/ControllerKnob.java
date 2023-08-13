package com.sunset.discjockey.block.BlockEntity.Controller.Widget.AbstractWidget;

import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidget;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidgetManager;
import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;
import net.minecraft.nbt.CompoundTag;

public class ControllerKnob extends ControllerWidget
{
    public double value = 0.0; //between -1 to 1

    public ControllerKnob(String id, PlaneRange planeRange) {
        super(id, ControllerWidgetManager.InteractType.SCROLL, planeRange);
    }

    @Override
    public void execute(double value) {

    }

    @Override
    public CompoundTag getCompoundTag() {
        return null;
    }

    @Override
    public void writeCompoundTag(CompoundTag compoundTag) {

    }
}
