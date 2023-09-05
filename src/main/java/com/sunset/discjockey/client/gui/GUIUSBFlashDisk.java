package com.sunset.discjockey.client.gui;

import com.sunset.discjockey.client.gui.layout.AbstractLayout;
import com.sunset.discjockey.client.gui.layout.HorizontalLayout;
import com.sunset.discjockey.client.gui.layout.VerticalLayout;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GUIUSBFlashDisk extends Screen {
    private static final Component TITLE = null;
    private ItemStack itemStack;

    //widget

    private AbstractLayout inputLines;

    private AbstractLayout buttons;

    private AbstractLayout layout; //whole layout


    public GUIUSBFlashDisk(ItemStack pItemStack) {
        super(GameNarrator.NO_TITLE);
        this.itemStack = pItemStack;
    }

    //what the hell???
    public void show() {
        Minecraft.getInstance().setScreen(this);
    }

    @Override
    protected void init() {
        this.readFromNBT(); //init inputLines

        buttons = new HorizontalLayout(10, 0)
                .add(
                        Button.builder(Component.literal("Add"), (button) -> {
                            inputLines.add(this.createEmptyInputLine());
                        }).build()
                )
                .add(
                        Button.builder(Component.literal("Confirm"), (button) -> {
                            this.saveToNBT();
                            this.getMinecraft().popGuiLayer();
                        }).build()
                );

        layout = new VerticalLayout(this, null, this.width / 4, this.height / 8, this.width / 2, (int) (this.height / 1.3), 10, 0)
                .add(inputLines)
                .add(buttons, 0, 15);

        super.init();
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(pGuiGraphics);
//        editBox.render(pGuiGraphics, mouseX, mouseY, partialTicks);
//        confirmButton.render(pGuiGraphics, mouseX, mouseY, partialTicks);
        super.render(pGuiGraphics, mouseX, mouseY, partialTicks);
    }


    //other
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

    public void readFromNBT() {
        if (inputLines == null) {
            inputLines = new VerticalLayout(10, 8);
            CompoundTag compoundTag = this.itemStack.getTag();
            if (compoundTag != null) {
                ListTag listTag = compoundTag.getList("url", Tag.TAG_STRING);
                listTag.forEach(i -> {
                    AbstractLayout inputLine = this.createEmptyInputLine();
                    ((EditBox) inputLine.widgets.get(0)).setValue(i.getAsString());
                    inputLines.add(inputLine);
                });
            }
        } else {
            inputLines.screen = this;
        }
    }

    public void saveToNBT() {
        ListTag listTag = new ListTag();

        inputLines.widgets.forEach(i -> {
            listTag.add(StringTag.valueOf(((EditBox) ((HorizontalLayout) i).widgets.get(0)).getValue()));
        });

        this.itemStack.addTagElement("url", listTag);
    }
}
