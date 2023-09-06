package com.sunset.discjockey.client.gui;

import com.sunset.discjockey.client.gui.layout.AbstractLayout;
import com.sunset.discjockey.client.gui.layout.HorizontalLayout;
import com.sunset.discjockey.client.gui.layout.VerticalLayout;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class GUIUSBFlashDisk extends Screen {

    private ItemStack itemStack;

    private int pageIndex = 0;

    //widget

    private List<AbstractLayout> inputLinesList = null;

    private AbstractLayout buttons;

    private AbstractLayout layout; //whole layout


    public GUIUSBFlashDisk(ItemStack pItemStack) {
        super(GameNarrator.NO_TITLE);
        this.itemStack = pItemStack;
    }

    @Override
    protected void init() {
        this.readFromNBT(); //init inputLines

        buttons = new VerticalLayout(10, 0)
                .add(
                        new HorizontalLayout(10, 0)
                                .add(
                                        Button.builder(Component.literal("<-"), (button) -> {
                                            if (this.pageIndex > 0) {
                                                this.pageIndex--;
                                                this.loadRefresh();
                                            }
                                        }).build()
                                )
                                .add(
                                        new StringWidget(Component.literal(""), this.font)
                                )
                                .add(
                                        Button.builder(Component.literal("->"), (button) -> {
                                            if (this.pageIndex < inputLinesList.size()) {
                                                this.pageIndex++;
                                                this.loadRefresh();
                                            }
                                        }).build()
                                )
                )
                .add(
                        new HorizontalLayout(10, 0)
                                .add(
                                        Button.builder(Component.literal("Add"), (button) -> {
                                            if (!inputLinesList.get(this.pageIndex).hasReachedLimit()) {
                                                inputLinesList.get(this.pageIndex).add(this.createEmptyInputLine());
                                            } else {//jump to the end page and add a new inputLine
                                                do {
                                                    this.pageIndex++;
                                                }
                                                while (this.pageIndex < inputLinesList.size() && inputLinesList.get(this.pageIndex).hasReachedLimit());
                                                this.loadRefresh();
                                                inputLinesList.get(this.pageIndex).add(this.createEmptyInputLine());
                                            }
                                        }).build()
                                )
                                .add(
                                        Button.builder(Component.literal("Confirm"), (button) -> {
                                            this.saveToNBT();
                                            this.getMinecraft().popGuiLayer();
                                        }).build()
                                )
                );

        layout = new VerticalLayout(this, null, this.width / 4, this.height / 8, this.width / 2, (int) (this.height / 1.3), 10, 0)
                .add(inputLinesList.get(this.pageIndex))
                .add(buttons, 0, 50);

        super.init();
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.tickRefresh();
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, mouseX, mouseY, partialTicks);
    }

    //element creator

    public AbstractLayout createEmptyInputLine() {
        HorizontalLayout inputLine = new HorizontalLayout(10, 0);
        EditBox editBox = new EditBox(font, 0, 0, 0, 0, Component.literal("url input"));
        editBox.setMaxLength(255);
        Button removeButton = Button.builder(Component.literal("Remove"), (button) -> {
            inputLine.destroy();
        }).bounds(0, 0, 0, 0).build();
        return inputLine
                .add(editBox, -70, 20)
                .add(removeButton, 0, 20);
    }

    public VerticalLayout createEmptyInputLines() {
        return new VerticalLayout(10, 4);
    }

    //refresh
    public void loadRefresh() {
        this.layout.removeAll();

        //create new inputLines
        if (this.pageIndex == inputLinesList.size())
            inputLinesList.add(this.createEmptyInputLines());

        this.removeNextInputLinesIfEmpty();
        this.init();
    }

    public void tickRefresh() {
        AbstractLayout currentInputLines = inputLinesList.get(this.pageIndex);

        //fill currentInputLines from nextInputLines until full
        if (this.pageIndex + 1 < inputLinesList.size()) {

            while (!currentInputLines.hasReachedLimit()) {
                currentInputLines.add(inputLinesList.get(this.pageIndex + 1).widgets.get(0));
                inputLinesList.get(this.pageIndex + 1).widgets.remove(0);
                this.removeNextInputLinesIfEmpty();
            }

            this.removeNextInputLinesIfEmpty();
        }

        //set buttons state
        ((HorizontalLayout) buttons.widgets.get(0)).widgets.get(0).active = this.pageIndex != 0;
        ((HorizontalLayout) buttons.widgets.get(0)).widgets.get(1).setMessage(Component.literal(this.pageIndex + 1 + "/" + inputLinesList.size()));
        ((HorizontalLayout) buttons.widgets.get(0)).widgets.get(2).active = currentInputLines.widgets.size() >= currentInputLines.limit;
    }

    public void removeNextInputLinesIfEmpty() {
        if (this.pageIndex + 1 < inputLinesList.size() && inputLinesList.get(this.pageIndex + 1).widgets.isEmpty()) {
            inputLinesList.get(this.pageIndex + 1).destroy();
            inputLinesList.remove(this.pageIndex + 1);
        }
    }

    //data
    public void readFromNBT() {
        if (inputLinesList == null) {
            inputLinesList = new ArrayList<>();

            CompoundTag compoundTag = this.itemStack.getOrCreateTag();
            ListTag listTag = compoundTag.getList("url", Tag.TAG_STRING);

            //fill inputLines and make sure that the inputLinesList is not empty
            VerticalLayout inputLines = createEmptyInputLines();
            for (Tag tag : listTag) {
                if (inputLines.hasReachedLimit()) {
                    inputLinesList.add(inputLines);
                    inputLines = this.createEmptyInputLines();
                }
                AbstractLayout inputLine = this.createEmptyInputLine();
                ((EditBox) inputLine.widgets.get(0)).setValue(tag.getAsString());
                inputLines.add(inputLine);
            }
            inputLinesList.add(inputLines);
        } else {
            inputLinesList.get(this.pageIndex).setScreen(this);
        }
    }

    public void saveToNBT() {
        ListTag listTag = new ListTag();

        inputLinesList.forEach(inputLines -> inputLines.widgets.forEach(inputLine -> {
            listTag.add(StringTag.valueOf(((EditBox) ((HorizontalLayout) inputLine).widgets.get(0)).getValue()));
        }));

        this.itemStack.addTagElement("url", listTag);
    }
}
