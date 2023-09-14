package com.sunset.discjockey.util.RegistryCollection;

import com.sunset.discjockey.util.ModReference;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.sunset.discjockey.util.RegistryCollection.ItemCollection.ITEM_DEFERRED_REGISTER;

public class CreativeModeTabCollection {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB_DEFERRED_REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ModReference.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TAB_DISC_JOCKEY = CREATIVE_MODE_TAB_DEFERRED_REGISTER.register(ModReference.MOD_ID, () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + ModReference.MOD_ID))
            .icon(() -> new ItemStack(ItemCollection.ITEM_BLOCK_DDJ400.get()))
            .displayItems((p, o) -> ITEM_DEFERRED_REGISTER.getEntries().forEach(i -> o.accept(i.get())))
            .build());
}
