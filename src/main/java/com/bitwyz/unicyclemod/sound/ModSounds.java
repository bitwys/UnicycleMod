package com.bitwyz.unicyclemod.sound;

import com.bitwyz.unicyclemod.UnicycleMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, UnicycleMod.MOD_ID);

    public static final RegistryObject<SoundEvent> MUSIC_BAD =
            SOUNDS.register("music_bad", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(UnicycleMod.MOD_ID, "music_bad")));
    public static final RegistryObject<SoundEvent> MUSIC_POLAND =
            SOUNDS.register("music_poland", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(UnicycleMod.MOD_ID, "music_poland")));
    public static final RegistryObject<SoundEvent> MUSIC_TITANIC =
            SOUNDS.register("music_titanic", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(UnicycleMod.MOD_ID, "music_titanic")));
    public static final RegistryObject<SoundEvent> MUSIC_GRAIL =
            SOUNDS.register("music_grail", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(UnicycleMod.MOD_ID, "music_grail")));
    public static final RegistryObject<SoundEvent> MUSIC_WII =
            SOUNDS.register("music_wii", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(UnicycleMod.MOD_ID, "music_wii")));
    public static final RegistryObject<SoundEvent> MUSIC_TUBES =
            SOUNDS.register("music_tubes", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(UnicycleMod.MOD_ID, "music_tubes")));

    public static void register(IEventBus eventBus) {
        SOUNDS.register(eventBus);
    }
}
