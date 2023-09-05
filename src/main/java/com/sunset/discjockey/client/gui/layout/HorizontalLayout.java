package com.sunset.discjockey.client.gui.layout;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;

public class HorizontalLayout extends AbstractLayout {
    public HorizontalLayout(Screen pScreen, AbstractLayout pParent, int pX, int pY, int pWidth, int pHeight, int pGap) {
        super(pScreen, pParent, pX, pY, pWidth, pHeight, pGap);
    }

    public HorizontalLayout(Screen pScreen, int pGap) {
        super(pScreen, pGap);
    }

    @Override
    public void sort() {
        int size = this.getWidgets().size();
        int width = (this.getWidth() - (size - 1) * this.getGap()) / size;
        for (AbstractWidget widget : this.getWidgets()) {
            widget.setX(this.getX() + (width + this.getGap()) * this.getWidgets().indexOf(widget));
            widget.setY(this.getY());
            widget.setWidth(width);
            widget.setHeight(this.getHeight());
            if (widget instanceof AbstractLayout layout)
                layout.sort();
        }
    }
}
