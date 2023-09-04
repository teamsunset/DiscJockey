package com.sunset.discjockey.client.gui;

import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GUIUSBFlashDisk extends Screen {
    private static final Component TITLE = null;
    private Player owner;
    private ItemStack itemStack;

    //widget

    private EditBox editBox;

    private Button confirmButton;


    public GUIUSBFlashDisk(Player pOwner, ItemStack pItemStack) {
        super(GameNarrator.NO_TITLE);
        this.owner = pOwner;
        this.itemStack = pItemStack;
    }


    @Override
    protected void init() {
        //this.width
        editBox = new EditBox(font, 30, 50, 300, 50, TITLE);
        confirmButton = Button.builder(Component.literal("test"), (button) -> {
            this.getMinecraft().popGuiLayer();
        }).bounds(200, 200, 50, 20).build();
        super.init();
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(pGuiGraphics);
        editBox.render(pGuiGraphics, mouseX, mouseY, partialTicks);
        confirmButton.render(pGuiGraphics, mouseX, mouseY, partialTicks);
        super.render(pGuiGraphics, mouseX, mouseY, partialTicks);
    }
}
