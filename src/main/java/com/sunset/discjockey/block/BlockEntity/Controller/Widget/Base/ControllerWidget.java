package com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base;

import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;
import net.minecraft.nbt.CompoundTag;

public abstract class ControllerWidget
{
    public ControllerWidgetManager controllerWidgetSystem;

    public String id;

    public ControllerWidgetManager.InteractType interactType;

    public PlaneRange planeRange;

    public ControllerWidget(String id, ControllerWidgetManager.InteractType interactType, PlaneRange planeRange) {
        this.id = id;
        this.interactType = interactType;
        this.planeRange = planeRange;
    }

    public abstract void execute(double value);

    public abstract CompoundTag getCompoundTag();

    public abstract void writeCompoundTag(CompoundTag compoundTag);

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (controllerWidgetSystem != null)
            controllerWidgetSystem.controllerWidgets.remove(this);
    }
}
