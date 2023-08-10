package com.sunset.discjockey.item;

import net.minecraft.world.item.Item;

public class ItemUSBFlashDisk extends Item
{
    public ItemUSBFlashDisk() {
        super(ItemUSBFlashDisk.GetProperties());
    }

    public static Properties GetProperties() {
        return new Properties();
    }
}
