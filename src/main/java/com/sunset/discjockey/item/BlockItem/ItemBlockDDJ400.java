package com.sunset.discjockey.item.BlockItem;

import com.sunset.discjockey.util.RegistryCollection.BlockCollection;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class ItemBlockDDJ400 extends BlockItem
{

    public ItemBlockDDJ400() {
        super(BlockCollection.BLOCK_DDJ400.get(), ItemBlockDDJ400.GetProperties());
    }

    public static Properties GetProperties(){
        return new Properties();
    }
}
