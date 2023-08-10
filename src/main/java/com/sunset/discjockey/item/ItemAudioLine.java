package com.sunset.discjockey.item;

import net.minecraft.world.item.Item;

public class ItemAudioLine extends Item
{
    public ItemAudioLine() {
        super(ItemAudioLine.GetProperties());
    }

    public static Properties GetProperties() {
        return new Properties();
    }
}