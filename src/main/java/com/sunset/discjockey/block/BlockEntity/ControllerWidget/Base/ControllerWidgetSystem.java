package com.sunset.discjockey.block.BlockEntity.ControllerWidget.Base;

import com.sunset.discjockey.block.BlockEntity.ControllerWidget.Base.ControllerWidget;
import com.sunset.discjockey.util.TouchMap.Vec2Type.Vec2Plane;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;

public class ControllerWidgetSystem
{
    public enum InteractType
    {
        PRESS,
        DRAG,
        SCROLL
    }

    public ArrayList<ControllerWidget> controllerWidgets = new ArrayList<>();

    public void add(ControllerWidget controllerWidget) {
        controllerWidget.controllerWidgetSystem = this;
        controllerWidgets.add(controllerWidget);
    }

    public void interact(InteractType interactType, int interactValue, Vec2Plane relativeActionPos) {
        for (ControllerWidget controllerWidget : controllerWidgets) {
            if (controllerWidget.interactType == interactType && controllerWidget.planeRange.isInRange(relativeActionPos)) {
                switch (interactType) {
                    case PRESS -> {
                        controllerWidget.execute(1);
                    }
                    case DRAG -> {
                        double value = controllerWidget.planeRange.getValueInRange(relativeActionPos) * 2 - 1;
                        controllerWidget.execute(value);
                    }
                    case SCROLL -> {
                        controllerWidget.execute(interactValue);
                    }
                }
            }
        }
    }

    public void render() {

    }

    public CompoundTag getCompoundTag() {
        CompoundTag compoundTag = new CompoundTag();
        for (ControllerWidget controllerWidget : controllerWidgets) {
            compoundTag.put(controllerWidget.id, controllerWidget.getCompoundTag());
        }
        return compoundTag;
    }

    public void writeCompoundTag(CompoundTag compoundTag) {
        for (ControllerWidget controllerWidget : controllerWidgets) {
            controllerWidget.writeCompoundTag(compoundTag.getCompound(controllerWidget.id));
        }
    }
}
