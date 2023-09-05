package com.sunset.discjockey.client.gui.layout;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;

public class HorizontalLayout extends AbstractLayout {

    public HorizontalLayout(Screen pScreen, AbstractLayout pParent, int pX, int pY, int pWidth, int pHeight, int pGap, int pLimit) {
        super(pScreen, pParent, pX, pY, pWidth, pHeight, pGap, pLimit);
    }

    public HorizontalLayout(int pGap, int pLimit) {
        this(null, null, 0, 0, 0, 0, pGap, pLimit);
    }

    @Override
    public void sort() {
        int variableCount = this.widgets.size();
        int totalFixedWidth = 0;
        int variableWidth = 0;

        if (this.getWidth() > 2) { //fixed width
            for (AbstractWidget widget : this.widgets) {
                if (this.getFixedSize(widget).getA() > 2) {
                    totalFixedWidth += this.getFixedSize(widget).getA();
                    variableCount--;
                } else if (this.getFixedSize(widget).getA() > -100 && this.getFixedSize(widget).getA() < 0) {
                    totalFixedWidth += (int) (this.getPercentage(this.getFixedSize(widget).getA()) * this.getWidth());
                    variableCount--;
                }
            }
            if (variableCount != 0)
                variableWidth = (this.getWidth() - totalFixedWidth - this.widgets.size() * this.gap) / variableCount;
        }

        int nowX = this.getX();

        for (AbstractWidget widget : this.widgets) {
            if (this.getWidth() > 2) {
                if (this.getFixedSize(widget).getA() > 2 && this.getFixedSize(widget).getA() < this.getWidth()) {//variable
                    widget.setWidth(this.getFixedSize(widget).getA());
                } else if (this.getFixedSize(widget).getA() > -100 && this.getFixedSize(widget).getA() < 0) {//variable percent
                    widget.setWidth((int) (this.getPercentage(this.getFixedSize(widget).getA()) * this.getWidth()));
                } else {//fixed
                    widget.setWidth(variableWidth);
                }
                widget.setX(nowX);
                nowX += (widget.getWidth() + this.gap);
            } else {//this variable
                widget.setX(this.getX());
                widget.setWidth(this.getFixedSize(widget).getA());
            }

            if (this.getFixedSize(widget).getB() > 2 && this.getFixedSize(widget).getB() < this.getHeight()) {//variable
                widget.setHeight(this.getFixedSize(widget).getB());
                widget.setY(this.getY() + (this.getHeight() - this.getFixedSize(widget).getB()) / 2); //Vertical Center
            } else if (this.getFixedSize(widget).getB() > -100 && this.getFixedSize(widget).getB() < 0) {//variable percent
                widget.setHeight((int) (this.getPercentage(this.getFixedSize(widget).getB()) * this.getHeight()));
                widget.setY(this.getY() + (this.getHeight() - this.getFixedSize(widget).getB()) / 2); //Vertical Center
            } else {//fixed
                widget.setHeight(this.getHeight());
                widget.setY(this.getY());
            }
        }

        super.sort();
    }
}
