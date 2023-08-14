package com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base;

import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;
import net.minecraft.nbt.CompoundTag;

public abstract class ControllerWidget
{
    public ControllerWidgetManager controllerWidgetSystem;

    public String id;

    public ControllerWidgetManager.InteractType interactType;

    public PlaneRange planeRange;

    public boolean syncMark = false;

    public ControllerWidget(String id, ControllerWidgetManager.InteractType interactType, PlaneRange planeRange) {
        this.id = id;
        this.interactType = interactType;
        this.planeRange = planeRange;
    }

    //dirty
    //only on serverside
    public void markDirty() {this.syncMark = true;}

    public void markClean() {this.syncMark = false;}

    //should clean mark on serverside after send packet
    public void executeOnServer(double value) {}

    public void executeOnClient() {}

    public abstract CompoundTag getCompoundTag();

    public abstract void writeCompoundTag(CompoundTag compoundTag);
    
}
