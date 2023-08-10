package com.sunset.discjockey.util.RegistryCollection;

import com.sunset.discjockey.block.BlockDDJ400;
import com.sunset.discjockey.block.BlockSpeaker;
import com.sunset.discjockey.util.Reference;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockCollection
{
    public static final DeferredRegister<Block> BLOCK_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MOD_ID);

    public static final RegistryObject<Block> BLOCK_DDJ400 = BLOCK_DEFERRED_REGISTER.register("ddj_400", BlockDDJ400::new);
    public static final RegistryObject<Block> BLOCK_SPEAKER = BLOCK_DEFERRED_REGISTER.register("speaker", BlockSpeaker::new);

}
