package com.sunset.discjockey.client.gui.layout;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;

public class VerticalLayout extends AbstractLayout {

    public VerticalLayout(Screen pScreen, AbstractLayout pParent, int pX, int pY, int pWidth, int pHeight, int pGap) {
        super(pScreen, pParent, pX, pY, pWidth, pHeight, pGap);
    }

    public VerticalLayout(Screen pScreen, int pGap) {
        super(pScreen, pGap);
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
            if (widget instanceof AbstractLayout layout)
                layout.sort();
        }
    }

}
