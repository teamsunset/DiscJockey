package com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base;

import com.sunset.discjockey.block.BlockEntity.Controller.AbstractControllerEntity;
import com.sunset.discjockey.util.TouchMap.Vec2Type.Vec2Plane;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.world.entity.player.Player;

import java.util.Vector;

//for load at launching, this.controllerWidgets will be statically defined by BlockEntity.if you want to create dynamic widgets in the future, you should write the load function.
public class ControllerWidgetManager {
    public AbstractControllerEntity controller;

    public enum InteractType {
        PRESS,
        DRAG,
        SCROLL,
        NONE
    }

    public Vector<ControllerWidget> controllerWidgets = new Vector<>();


    //it will be load function.
//    public ControllerWidgetManager(CompoundTag compoundTag) {
//        for (String key : compoundTag.getAllKeys()) {
//            controllerWidgets.add(ControllerWidget.create(key, compoundTag.getCompound(key)));
//        }
//    }

    public ControllerWidgetManager(AbstractControllerEntity controller) {
        this.controller = controller;
    }

    public ControllerWidgetManager add(ControllerWidget controllerWidget) {
        controllerWidget.controllerWidgetManager = this;
        controllerWidgets.add(controllerWidget);
        return this;
    }

    public ControllerWidget get(String id) {
        for (ControllerWidget controllerWidget : controllerWidgets) {
            if (controllerWidget.id.equals(id)) {
                return controllerWidget;
            }
        }
        return null;
    }

    //this function is only on serverside
    public void interact(Player player, InteractType interactType, int interactValue, Vec2Plane relativeActionPos) {
        for (ControllerWidget controllerWidget : controllerWidgets) {
            if (controllerWidget.interactType == interactType && controllerWidget.planeRange.isInRange(relativeActionPos)) {
                switch (interactType) {
                    case PRESS -> {
                        controllerWidget.executeOnServer(player, 1);
                    }
                    case DRAG -> {
                        double value = controllerWidget.planeRange.getValueInRange(relativeActionPos) * 2 - 1;
                        controllerWidget.executeOnServer(player, value);
                    }
                    case SCROLL -> {
                        controllerWidget.executeOnServer(player, interactValue);
                    }
                }
            }
        }
    }

    public CompoundTag getCompoundTag() {
        CompoundTag compoundTag = new CompoundTag();
        for (ControllerWidget controllerWidget : controllerWidgets) {
//            if (controllerWidget.syncMark.get()) {
            CompoundTag widgetTag = new CompoundTag();
            widgetTag.put(
                    "data",
                    controllerWidget.getCompoundTag() == null ? new CompoundTag() : controllerWidget.getCompoundTag()
            );
            widgetTag.put("execute", IntTag.valueOf(controllerWidget.executeMark.get() ? 1 : 0));
            compoundTag.put(controllerWidget.id, widgetTag);
//                controllerWidget.markClean();
//            }

        }
        return compoundTag;
    }

    public void writeCompoundTag(CompoundTag compoundTag) {
        for (ControllerWidget controllerWidget : controllerWidgets) {
            if (compoundTag.contains(controllerWidget.id)) {
                CompoundTag widgetTag = compoundTag.getCompound(controllerWidget.id);
                controllerWidget.writeCompoundTag(widgetTag.getCompound("data"));
                if (widgetTag.getInt("execute") == 1) {
                    controllerWidget.executeOnClient();
                }
            }
        }
    }

    public void onServerTick() {
        for (ControllerWidget controllerWidget : controllerWidgets) {
            controllerWidget.onServerTick();
        }
    }

    public void onClientTick() {
        for (ControllerWidget controllerWidget : controllerWidgets) {
            controllerWidget.onClientTick();
        }
    }
}
