package com.sunset.discjockey.block.BlockEntity.Controller.Widget.AbstractWidget;

import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidget;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidgetManager;
import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;
import net.minecraft.nbt.CompoundTag;

public class ControllerDisc extends ControllerWidget
{
    public ControllerDisc(String id, ControllerWidgetManager.InteractType interactType, PlaneRange planeRange) {
        super(id, interactType, planeRange);
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
