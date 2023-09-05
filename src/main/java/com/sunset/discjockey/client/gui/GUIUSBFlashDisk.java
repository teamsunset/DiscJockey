package com.sunset.discjockey.client.gui;

import com.sunset.discjockey.client.gui.layout.AbstractLayout;
import com.sunset.discjockey.client.gui.layout.HorizontalLayout;
import com.sunset.discjockey.client.gui.layout.VerticalLayout;
import net.minecraft.client.GameNarrator;
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

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class GUIUSBFlashDisk extends Screen {
    private static final Component TITLE = null;
    private Player owner;
    private ItemStack itemStack;

    //widget

    private List<AbstractLayout> inputLines;

    private Button confirmButton;

    private AbstractLayout layout;


    public GUIUSBFlashDisk(Player pOwner, ItemStack pItemStack) {
        super(GameNarrator.NO_TITLE);
        this.owner = pOwner;
        this.itemStack = pItemStack;
    }

//    @Override
//    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
//        return super.mouseClicked(pMouseX, pMouseY, pButton);
//    }

    @Override
    protected void init() {
        layout = new VerticalLayout(this, null, this.width / 4, this.height / 4, this.width / 2, this.height / 2, 10);

        this.readFromNBT();
        confirmButton = Button.builder(Component.literal("Confirm"), (button) -> {
            this.saveToNBT();
            this.getMinecraft().popGuiLayer();
        }).build();

        layout.addAll(inputLines)
                .add(confirmButton);

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
    public AbstractLayout createEmptyLine() {
        HorizontalLayout inputLine = new HorizontalLayout(this, 10);
        EditBox editBox = new EditBox(font, 0, 0, 0, 0, Component.literal("url input"));
        Button removeButton = Button.builder(Component.literal("Remove"), (button) -> {
            inputLine.destroy();
        }).build();
        return inputLine
                .add(editBox)
                .add(removeButton);
    }

    public void readFromNBT() {
        inputLines = new ArrayList<>();
        CompoundTag compoundTag = this.itemStack.getTag();
        if (compoundTag != null) {
            ListTag listTag = compoundTag.getList("url", Tag.TAG_STRING);
            listTag.forEach(i -> {
                AbstractLayout inputLine = this.createEmptyLine();
                ((EditBox) inputLine.getWidgets().get(0)).setValue(i.getAsString());
                inputLines.add(inputLine);
            });
        }
        inputLines.add(this.createEmptyLine());
    }

    public void saveToNBT() {
        ListTag listTag = new ListTag();

        inputLines.forEach(i -> {
            listTag.add(StringTag.valueOf(((EditBox) i.getWidgets().get(0)).getValue()));
        });

        this.itemStack.addTagElement("url", listTag);
    }
}
