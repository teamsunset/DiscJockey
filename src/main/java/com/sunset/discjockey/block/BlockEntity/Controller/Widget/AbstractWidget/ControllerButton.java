package com.sunset.discjockey.block.BlockEntity.Controller.Widget.AbstractWidget;

import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidget;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidgetManager;
import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;
import net.minecraft.nbt.CompoundTag;

public abstract class ControllerButton extends ControllerWidget
{
    public ControllerButton(String id, PlaneRange planeRange) {
        super(id, ControllerWidgetManager.InteractType.PRESS, planeRange);
    }

    @Override
    public abstract CompoundTag getCompoundTag();

    @Override
    public abstract void writeCompoundTag(CompoundTag compoundTag);
}
