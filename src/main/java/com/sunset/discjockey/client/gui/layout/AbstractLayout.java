package com.sunset.discjockey.client.gui.layout;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;


//layout
public abstract class AbstractLayout extends AbstractWidget {
    public List<AbstractWidget> widgets = new ArrayList<>();

    public AbstractLayout parent;

    public Screen screen;

    //    public int x;
//    public int y;
//    public int width;
//    public int height;
    public int gap;

    public AbstractLayout(Screen pScreen, AbstractLayout pParent, int pX, int pY, int pWidth, int pHeight, int pGap) {
        super(pX, pY, pWidth, pHeight, Component.nullToEmpty(null));
        this.active = false;
        this.screen = pScreen;
        this.parent = pParent;
//        this.x = pX;
//        this.y = pY;
//        this.width = pWidth;
//        this.height = pHeight;
        this.gap = pGap;
    }

    public AbstractLayout(Screen pScreen, int pGap) {
        this(pScreen, null, 0, 0, 0, 0, pGap);
    }

    public int getGap() {
        return gap;
    }

    public List<AbstractWidget> getWidgets() {
        return this.widgets;
    }

    //child should override this method to realize layout
    public abstract void sort();

    public AbstractLayout add(AbstractWidget widget) {
        this.widgets.add(widget);

        if (widget instanceof AbstractLayout layout) {
            layout.screen = this.screen;
            layout.parent = this;
        } else {
            screen.renderables.add(widget);
            screen.narratables.add(widget);
            screen.children.add(widget);
        }

        this.sort();
        return this;
    }

    public AbstractLayout addAll(List<? extends AbstractWidget> widgets) {
        widgets.forEach(this::add);
        return this;
    }


    public AbstractLayout remove(AbstractWidget widget) {
        this.widgets.remove(widget);

        if (!(widget instanceof AbstractLayout)) {
            screen.renderables.remove(widget);
            screen.narratables.remove(widget);
            screen.children.remove(widget);
        }
        if (!this.widgets.isEmpty())
            this.sort();
        return this;
    }

    public void removeAll() {
        this.widgets.forEach(this::remove);
    }

    public void destroy() {
        if (parent != null)
            this.parent.remove(this);
        this.removeAll();
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
