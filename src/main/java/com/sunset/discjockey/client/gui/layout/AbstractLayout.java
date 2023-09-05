package com.sunset.discjockey.client.gui.layout;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLayout extends AbstractWidget {
    public List<AbstractWidget> widgets = new ArrayList<>();

    public Screen screen;

    //    public int x;
//    public int y;
//    public int width;
//    public int height;
    public int gap;

    public AbstractLayout(Screen pScreen, int pX, int pY, int pWidth, int pHeight, int pGap) {
        super(pX, pY, pWidth, pHeight, Component.nullToEmpty(null));
        this.active = false;
        this.screen = pScreen;
//        this.x = pX;
//        this.y = pY;
//        this.width = pWidth;
//        this.height = pHeight;
        this.gap = pGap;
    }

    public int getGap() {
        return gap;
    }

    public List<AbstractWidget> getWidgets() {
        return widgets;
    }

    public abstract void sort();

    public AbstractLayout add(AbstractWidget widget) {
        this.widgets.add(widget);
        screen.renderables.add(widget);
        screen.narratables.add(widget);
        screen.children.add(widget);
        this.sort();
        return this;
    }

    public AbstractLayout addAll(List<? extends AbstractWidget> widgets) {
        widgets.forEach(this::add);
        return this;
    }

    public AbstractLayout add(AbstractLayout layout) {
        this.widgets.add(layout);
        return this;
    }

    public AbstractLayout remove(AbstractWidget widget) {
        widgets.remove(widget);
        screen.renderables.remove(widget);
        screen.narratables.remove(widget);
        screen.children.remove(widget);
        this.sort();
        return this;
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
//        widgets.forEach((widget) -> {
//            widget.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
//        });
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
//        widgets.forEach((widget) -> {
//            if (widget.isHovered())
//                widget.updateNarration(narrationElementOutput);
//        });
    }
//
//    @Override
//    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
//        widgets.forEach((widget) -> {
//            if (widget.mouseClicked(pMouseX, pMouseY, pButton)) {
//                widget.setFocused(true);
//            } else {
//                widget.setFocused(false);
//            }
//        });
//        return true;
//    }
//
//    @Override
//    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
//        widgets.forEach((widget) -> {
//            widget.mouseReleased(pMouseX, pMouseY, pButton);
//        });
//        return true;
//    }
//
//    @Override
//    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
//        widgets.forEach((widget) -> {
//            widget.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
//        });
//        return true;
//    }
//
//    @Override
//    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
//        widgets.forEach((widget) -> {
//            widget.keyPressed(pKeyCode, pScanCode, pModifiers);
//        });
//        return true;
//    }
//
//    @Override
//    public boolean keyReleased(int pKeyCode, int pScanCode, int pModifiers) {
//        widgets.forEach((widget) -> {
//            widget.keyReleased(pKeyCode, pScanCode, pModifiers);
//        });
//        return true;
//    }
//
//    @Override
//    public void mouseMoved(double pMouseX, double pMouseY) {
//        widgets.forEach((widget) -> {
//            widget.mouseMoved(pMouseX, pMouseY);
//        });
//    }
//
//    @Override
//    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
//        widgets.forEach((widget) -> {
//            widget.mouseScrolled(pMouseX, pMouseY, pDelta);
//        });
//        return true;
//    }
//
//    @Override
//    public boolean charTyped(char pCodePoint, int pModifiers) {
//        widgets.forEach((widget) -> {
//            widget.charTyped(pCodePoint, pModifiers);
//        });
//        return true;
//    }


}
