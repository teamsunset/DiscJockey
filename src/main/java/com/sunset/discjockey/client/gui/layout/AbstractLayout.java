package com.sunset.discjockey.client.gui.layout;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.List;


//layout
public abstract class AbstractLayout extends AbstractWidget {
    public List<AbstractWidget> widgets = new ArrayList<>();

    public List<Pair<AbstractWidget, Pair<Integer, Integer>>> fixedSizes = new ArrayList<>(); //0 is invalid,-100 to 0 is percent number

    public AbstractLayout parent;

    public Screen screen;

    //    public int x;
//    public int y;
    public int gap;

    public int limit; //0 means that there is no limit

    public AbstractLayout(Screen pScreen, AbstractLayout pParent, int pX, int pY, int pWidth, int pHeight, int pGap, int limit) {
        super(pX, pY, pWidth, pHeight, Component.nullToEmpty(null));
        this.active = false;

        this.width = pWidth;
        this.height = pHeight;

        this.screen = pScreen;
        this.parent = pParent;
        this.gap = pGap;
        this.limit = limit;
    }

    public Pair<Integer, Integer> getFixedSize(AbstractWidget widget) {
        for (Pair<AbstractWidget, Pair<Integer, Integer>> pair : this.fixedSizes) {
            if (pair.getA() == widget) {
                return pair.getB();
            }
        }
        return new Pair<>(0, 0);
    }

    public AbstractLayout addFixedSize(AbstractWidget widget, int pFixedWidth, int pFixedHeight) {
        this.fixedSizes.add(new Pair<>(widget, new Pair<>(pFixedWidth, pFixedHeight)));
        return this;
    }

    public Double getPercentage(int pNumber) {
        return Math.abs(pNumber) / 100.0;
    }

    public AbstractLayout setScreen(Screen pScreen) {
        this.screen = pScreen;
        for (AbstractWidget widget : this.widgets) {
            if (widget instanceof AbstractLayout layout) {
                layout.setScreen(pScreen);
            } else if (this.screen != null) {
                this.screen.renderables.add(widget);
                this.screen.narratables.add(widget);
                this.screen.children.add(widget);
            }
        }
        return this;
    }

    //child should override this method to implement layout
    //do super at the end
    public void sort() {
        for (AbstractWidget widget : this.widgets) {
//            if (widget.getWidth() < 2 || widget.getHeight() < 2) {
//                new RuntimeException("size should be positive(>1)").printStackTrace();
//                System.err.println("size should be positive(>1)");
//            }
            widget.setWidth(Math.max(widget.getWidth(), 2));
            widget.setHeight(Math.max(widget.getHeight(), 2));

            if (widget instanceof EditBox editBox) {
                editBox.setValue(editBox.getValue());
            }

            if (widget instanceof AbstractLayout layout) {
                layout.sort();
            }
        }
    }

    public AbstractLayout add(AbstractWidget widget) {
        if (this.limit < 1 || this.widgets.size() < this.limit) {
            this.widgets.add(widget);

            if (widget instanceof AbstractLayout layout) {
                layout.setScreen(this.screen);
                layout.parent = this;
            } else if (screen != null) {
                this.screen.renderables.add(widget);
                this.screen.narratables.add(widget);
                this.screen.children.add(widget);
            }

            this.sort();
        }
        return this;
    }

    public AbstractLayout add(AbstractWidget widget, int pFixedWidth, int pFixedHeight) {
        this.addFixedSize(widget, pFixedWidth, pFixedHeight);
        this.add(widget);//sequential because of sort()
        return this;
    }

    public AbstractLayout addAll(List<? extends AbstractWidget> widgets) {
        widgets.forEach(this::add);
        return this;
    }


    public AbstractLayout remove(AbstractWidget widget) {
        this.widgets.remove(widget);
        this.fixedSizes.removeIf((pair) -> pair.getA() == widget);

        if (!(widget instanceof AbstractLayout) && this.screen != null) {
            this.screen.renderables.remove(widget);
            this.screen.narratables.remove(widget);
            this.screen.children.remove(widget);
        }

        if (!this.widgets.isEmpty())
            this.sort();
        return this;
    }

    public void removeAll() {
        while (!this.widgets.isEmpty()) {
            this.remove(this.widgets.get(0));
        }
    }


    public void destroy() {
        if (parent != null)
            this.parent.remove(this);
        this.removeAll();
    }


    //deprecated
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
}
