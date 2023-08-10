package com.sunset.discjockey.util.RegistryCollection;

import com.sunset.discjockey.util.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundEventCollection
{
    public static final DeferredRegister<SoundEvent> SOUND_EVENT_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Reference.MOD_ID);

    public static final RegistryObject<SoundEvent> SOUND_EVENT = SOUND_EVENT_DEFERRED_REGISTER.register("sound_event", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Reference.MOD_ID, "sound_event")));
}
