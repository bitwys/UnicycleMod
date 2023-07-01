package com.bitwyz.unicyclemod.entity;

import com.bitwyz.unicyclemod.UnicycleMod;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, UnicycleMod.MOD_ID);

    public static RegistryObject<EntityType<Unicycle>> UNICYCLE =
            ENTITIES.register("unicycle", ModEntities::unicycleTypeFactory);

    private static EntityType<Unicycle> unicycleTypeFactory() {

        return new EntityType<Unicycle>(
                Unicycle::new,
                MobCategory.MISC,
                true,
                true,
                true,
                false,
                ImmutableSet.of(),
                EntityDimensions.fixed(1.0f, 1.0f),
                100,
                5);
    }

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
