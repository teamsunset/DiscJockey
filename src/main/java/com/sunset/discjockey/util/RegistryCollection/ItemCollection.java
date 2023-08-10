package com.sunset.discjockey.util.RegistryCollection;

import com.sunset.discjockey.block.BlockSpeaker;
import com.sunset.discjockey.item.BlockItem.ItemBlockDDJ400;
import com.sunset.discjockey.item.BlockItem.ItemBlockSpeaker;
import com.sunset.discjockey.item.ItemAudioLine;
import com.sunset.discjockey.item.ItemUSBFlashDisk;
import com.sunset.discjockey.util.Reference;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemCollection
{
    public static final DeferredRegister<Item> ITEM_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);

    public static final RegistryObject<Item> ITEM_BLOCK_DDJ400 = ITEM_DEFERRED_REGISTER.register("ddj_400", ItemBlockDDJ400::new);
    public static final RegistryObject<Item> ITEM_BLOCK_SPEAKER = ITEM_DEFERRED_REGISTER.register("speaker", ItemBlockSpeaker::new);

    public static final RegistryObject<Item> ITEM_USB_FLASH_DISK = ITEM_DEFERRED_REGISTER.register("usb_flash_disk", ItemUSBFlashDisk::new);

    public static final RegistryObject<Item> ITEM_AUDIO_LINE = ITEM_DEFERRED_REGISTER.register("audio_line", ItemAudioLine::new);
}
