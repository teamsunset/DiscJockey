package com.sunset.discjockey.block.BlockEntity.Controller.Widget;

import com.sunset.discjockey.block.BlockEntity.Controller.Widget.AbstractWidget.ControllerFader;
import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;

public class ControllerVolumeFader extends ControllerFader {
    public ControllerVolumeFader(String id, PlaneRange planeRange) {
        super(id, planeRange);
    }

    @Override
    public void executeOnServer(double value) {
        this.value.setTarget((value + 1) * 0.5);
        this.markExecute();
        this.markDirty();
    }

    @Override
    public void executeOnClient() {
        super.executeOnClient();
    }
}
