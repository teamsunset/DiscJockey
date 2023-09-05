package com.sunset.discjockey.client.gui.layout;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;

public class VerticalLayout extends AbstractLayout {

    public VerticalLayout(Screen pScreen, AbstractLayout pParent, int pX, int pY, int pWidth, int pHeight, int pGap, int pLimit) {
        super(pScreen, pParent, pX, pY, pWidth, pHeight, pGap, pLimit);
    }

    public VerticalLayout(int pGap, int pLimit) {
        this(null, null, 0, 0, 0, 0, pGap, pLimit);
    }

//    @Override
//    public void sort() {
//        int size = this.widgets.size();
//        int height = (this.getHeight() - (size - 1) * this.gap) / size;
//        for (AbstractWidget widget : this.widgets) {
//            widget.setX(this.getX());
//            widget.setY(this.getY() + (height + this.gap) * this.widgets.indexOf(widget));
//            widget.setWidth(this.getWidth());
//            widget.setHeight(height);
//        }
//        super.sort();
//    }

    @Override
    public void sort() {
        int variableCount = this.widgets.size();
        int totalFixedHeight = 0;
        int variableHeight = 0;

        if (this.getHeight() > 2) { //fixed height
            for (AbstractWidget widget : this.widgets) {
                if (this.getFixedSize(widget).getB() > 2) {
                    totalFixedHeight += this.getFixedSize(widget).getB();
                    variableCount--;
                } else if (this.getFixedSize(widget).getB() > -100 && this.getFixedSize(widget).getB() < 0) {
                    totalFixedHeight += (int) (this.getPercentage(this.getFixedSize(widget).getB()) * this.getHeight());
                    variableCount--;
                }
            }
            if (variableCount != 0)
                variableHeight = (this.getHeight() - totalFixedHeight - this.widgets.size() * this.gap) / variableCount;
        }

        int nowY = this.getY();

        for (AbstractWidget widget : this.widgets) {
            if (this.getHeight() > 2) {
                if (this.getFixedSize(widget).getB() > 2 && this.getFixedSize(widget).getB() < this.getHeight()) {
                    widget.setHeight(this.getFixedSize(widget).getB());
                } else if (this.getFixedSize(widget).getB() > -100 && this.getFixedSize(widget).getB() < 0) {
                    widget.setHeight((int) (this.getPercentage(this.getFixedSize(widget).getB()) * this.getHeight()));
                } else {
                    widget.setHeight(variableHeight);
                }
                widget.setY(nowY);
                nowY += (widget.getHeight() + this.gap);
            } else {
                widget.setY(this.getY());
                widget.setHeight(this.getFixedSize(widget).getB());
            }

            if (this.getFixedSize(widget).getA() > 2 && this.getFixedSize(widget).getA() < this.getWidth()) {
                widget.setWidth(this.getFixedSize(widget).getA());
                widget.setX(this.getX() + (this.getWidth() - this.getFixedSize(widget).getA()) / 2); //Horizontal Center
            } else if (this.getFixedSize(widget).getA() > -100 && this.getFixedSize(widget).getA() < 0) {
                widget.setWidth((int) (this.getPercentage(this.getFixedSize(widget).getA()) * this.getWidth()));
                widget.setX(this.getX() + (this.getWidth() - this.getFixedSize(widget).getA()) / 2); //Horizontal Center
            } else {
                widget.setWidth(this.getWidth());
                widget.setX(this.getX());
            }
        }

        super.sort();
    }

}
