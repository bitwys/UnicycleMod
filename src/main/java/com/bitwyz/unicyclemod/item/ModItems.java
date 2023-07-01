package com.bitwyz.unicyclemod.item;

import com.bitwyz.unicyclemod.UnicycleMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
  public static final DeferredRegister<Item> ITEMS =
      DeferredRegister.create(ForgeRegistries.ITEMS, UnicycleMod.MOD_ID);

  public static final RegistryObject<Item> IRON_UNICYCLE_ITEM =
      ITEMS.register("iron_unicycle", () -> new UnicycleItem(new Item.Properties().stacksTo(1)));

  public static void register(IEventBus eventBus) {
    ITEMS.register(eventBus);
  }
}
