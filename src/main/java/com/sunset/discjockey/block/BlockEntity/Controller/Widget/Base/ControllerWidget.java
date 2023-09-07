package com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base;

import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;
import net.minecraft.nbt.CompoundTag;

public abstract class ControllerWidget {
    public ControllerWidgetManager controllerWidgetManager;

    public String id;

    public ControllerWidgetManager.InteractType interactType;

    public PlaneRange planeRange;

    public boolean syncMark = false;

    public boolean executeMark = false;

    public ControllerWidget(String id, ControllerWidgetManager.InteractType interactType, PlaneRange planeRange) {
        this.id = id;
        this.interactType = interactType;
        this.planeRange = planeRange;
    }

    //dirty
    //only on serverside
    public void markDirty() {
        this.syncMark = true;
        controllerWidgetManager.controller.markDirty();
    }

    public void markExecute() {
        this.executeMark = true;
    }

    public void markClean() {
        this.syncMark = false;
        this.executeMark = false;
    }

    //should clean mark on serverside after send packet
    public abstract void executeOnServer(double value);

    public abstract void executeOnClient();

    //serverside
    public abstract CompoundTag getCompoundTag();

    //clientside
    public abstract void writeCompoundTag(CompoundTag compoundTag);

}
