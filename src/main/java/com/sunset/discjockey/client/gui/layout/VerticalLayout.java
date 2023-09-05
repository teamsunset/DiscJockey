package com.sunset.discjockey.client.gui.layout;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class VerticalLayout extends AbstractLayout {

    public VerticalLayout(Screen pScreen, int pX, int pY, int pWidth, int pHeight, int pGap) {
        super(pScreen, pX, pY, pWidth, pHeight, pGap);
    }

    @Override
    public void sort() {
        int size = this.getWidgets().size();
        int height = (this.getHeight() - (size - 1) * this.getGap()) / size;
        for (AbstractWidget widget : this.getWidgets()) {
            widget.setX(this.getX());
            widget.setY(this.getY() + (height + this.getGap()) * this.getWidgets().indexOf(widget));
            widget.setWidth(this.getWidth());
            widget.setHeight(height);
        }
    }

}
