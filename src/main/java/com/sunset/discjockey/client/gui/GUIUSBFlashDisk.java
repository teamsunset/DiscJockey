package com.sunset.discjockey.client.gui;

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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

import static com.sunset.discjockey.DiscJockey.DEBUG_LOGGER;

@OnlyIn(Dist.CLIENT)
public class GUIUSBFlashDisk extends Screen {
    private static final Component TITLE = null;
    private Player owner;
    private ItemStack itemStack;

    //widget

    private List<EditBox> editBoxes = new ArrayList<>();

    private Button confirmButton;


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
        this.readFromNBT();
        confirmButton = Button.builder(Component.literal("Confirm"), (button) -> {
            this.saveToNBT();
            this.getMinecraft().popGuiLayer();
        }).build();

        new VerticalLayout(this, this.width / 4, this.height / 4, this.width / 2, this.height / 2, 10)
                .addAll(editBoxes)
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
    public EditBox createEmptyEditBox() {
        return new EditBox(font, 0, 0, 0, 0, Component.literal("url input"));
    }

    public void readFromNBT() {
        CompoundTag compoundTag = this.itemStack.getTag();
        if (compoundTag != null) {
            ListTag listTag = compoundTag.getList("url", Tag.TAG_STRING);
            listTag.forEach(i -> {
                EditBox editBox = this.createEmptyEditBox();
                editBox.setValue(i.getAsString());
                editBoxes.add(editBox);
            });
        }
        editBoxes.add(this.createEmptyEditBox());
    }

    public void saveToNBT() {
        ListTag listTag = new ListTag();

        editBoxes.forEach(i -> {
            listTag.add(StringTag.valueOf(i.getValue()));
        });

        this.itemStack.addTagElement("url", listTag);
    }
}
