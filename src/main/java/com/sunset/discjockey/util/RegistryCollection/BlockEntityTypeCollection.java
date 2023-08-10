package com.sunset.discjockey.util.RegistryCollection;

import com.sunset.discjockey.block.BlockEntity.BlockEntityDDJ400;
import com.sunset.discjockey.util.Reference;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityTypeCollection
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Reference.MOD_ID);

    public static final RegistryObject<BlockEntityType<BlockEntityDDJ400>> BLOCK_ENTITY_DDJ400 = BLOCK_ENTITY_TYPE_DEFERRED_REGISTER.register("ddj_400", () -> BlockEntityType.Builder.of(BlockEntityDDJ400::new, BlockCollection.BLOCK_DDJ400.get()).build(null));
}
