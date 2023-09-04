package com.sunset.discjockey.item;

import net.minecraft.client.gui.Gui;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.DistExecutor;

public class ItemUSBFlashDisk extends Item
{
    public ItemUSBFlashDisk() {
        super(ItemUSBFlashDisk.GetProperties());
    }

    public static Properties GetProperties() {
        return new Properties();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide) {

        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
