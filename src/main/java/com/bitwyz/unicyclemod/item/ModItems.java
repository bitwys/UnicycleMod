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

  public static final RegistryObject<Item> GOOBER =
      ITEMS.register("goober", () -> new Item(new Item.Properties()));

  public static final RegistryObject<Item> UNICYCLE =
          ITEMS.register("unicycle", () -> new UnicycleItem(new Item.Properties().stacksTo(1)));

  public static void register(IEventBus eventBus) {
    ITEMS.register(eventBus);
  }
}
