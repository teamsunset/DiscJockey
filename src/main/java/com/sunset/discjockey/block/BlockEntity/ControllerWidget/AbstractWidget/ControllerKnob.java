package com.sunset.discjockey.block.BlockEntity.ControllerWidget.AbstractWidget;

import com.sunset.discjockey.block.BlockEntity.ControllerWidget.Base.ControllerWidget;
import com.sunset.discjockey.block.BlockEntity.ControllerWidget.Base.ControllerWidgetSystem;
import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;
import net.minecraft.nbt.CompoundTag;

public class ControllerKnob extends ControllerWidget
{
    public ControllerKnob(String id, PlaneRange planeRange) {
        super(id, ControllerWidgetSystem.InteractType.SCROLL, planeRange);
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
