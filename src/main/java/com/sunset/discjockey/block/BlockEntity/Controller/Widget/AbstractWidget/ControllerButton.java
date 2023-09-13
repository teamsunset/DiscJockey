package com.sunset.discjockey.block.BlockEntity.Controller.Widget.AbstractWidget;

import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidget;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidgetManager;
import com.sunset.discjockey.util.SpecialType.OneShotBoolean;
import com.sunset.discjockey.util.SpecialType.Property;
import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;
import net.minecraft.nbt.CompoundTag;

public abstract class ControllerButton extends ControllerWidget {
    public Property<Boolean> pressed = new OneShotBoolean();

    public ControllerButton(String id, PlaneRange planeRange) {
        super(id, ControllerWidgetManager.InteractType.PRESS, planeRange);
    }

    @Override
    public void executeOnServer(double value) {
        pressed.set(!pressed.get());
        this.markExecute();
        this.markDirty();
    }

    @Override
    public CompoundTag getCompoundTag() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putBoolean("pressed", pressed.get());
        return compoundTag;
    }

    @Override
    public void writeCompoundTag(CompoundTag compoundTag) {
        pressed.set(compoundTag.getBoolean("pressed"));
    }

}
