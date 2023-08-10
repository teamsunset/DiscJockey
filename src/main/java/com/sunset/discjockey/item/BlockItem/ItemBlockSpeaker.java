package com.sunset.discjockey.item.BlockItem;

import com.sunset.discjockey.util.RegistryCollection.BlockCollection;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.common.property.Properties;

public class ItemBlockSpeaker extends BlockItem
{
    
    public ItemBlockSpeaker() {
        super(BlockCollection.BLOCK_SPEAKER.get(), ItemBlockSpeaker.GetProperties());
    }

    public static Properties GetProperties() {
        return new Properties();
    }

}
