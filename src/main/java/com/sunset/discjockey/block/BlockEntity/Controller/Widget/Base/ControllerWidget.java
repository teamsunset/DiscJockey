package com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base;

import com.sunset.discjockey.util.SpecialType.Property;
import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;
import net.minecraft.nbt.CompoundTag;

public abstract class ControllerWidget {
    public ControllerWidgetManager controllerWidgetManager;

    public String id;

    public ControllerWidgetManager.InteractType interactType;

    public PlaneRange planeRange;

    //    public boolean syncMark = false;
    public Property<Boolean> syncMark = new Property<>(false,
            ov -> {
                boolean v = ov;
                ov = false;
                return v;
            },
            (ov, dv) -> {
                ov = dv;
            }
    );

//    public boolean executeMark = false;

    public Property<Boolean> executeMark = new Property<>(false,
            ov -> {
                boolean v = ov;
                ov = false;
                return v;
            },
            (ov, dv) -> {
                ov = dv;
            }
    );

    public ControllerWidget(String id, ControllerWidgetManager.InteractType interactType, PlaneRange planeRange) {
        this.id = id;
        this.interactType = interactType;
        this.planeRange = planeRange;
    }

    //dirty
    //only on serverside
    public void markDirty() {
        this.syncMark.set(true);
        controllerWidgetManager.controller.markDirty();
    }

    public void markExecute() {
        this.executeMark.set(true);
    }

    public void markClean() {
        this.syncMark.set(false);
        this.executeMark.set(false);
    }

    //should clean mark on serverside after send packet
    public abstract void executeOnServer(double value);

    public abstract void executeOnClient();

    //serverside
    public abstract CompoundTag getCompoundTag();

    //clientside
    public abstract void writeCompoundTag(CompoundTag compoundTag);

}
