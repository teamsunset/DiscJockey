package com.sunset.discjockey.item;

import com.sunset.discjockey.client.gui.GUIUSBFlashDisk;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemUSBFlashDisk extends Item {
    public ItemUSBFlashDisk() {
        super(ItemUSBFlashDisk.GetProperties());
    }

    public static Properties GetProperties() {
        return new Properties()
                .stacksTo(1);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide) {
            Minecraft.getInstance().setScreen(new GUIUSBFlashDisk(pPlayer, pPlayer.getItemInHand(pUsedHand)));
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
