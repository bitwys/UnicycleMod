package com.bitwyz.unicyclemod.item;

import com.bitwyz.unicyclemod.UnicycleMod;
import com.bitwyz.unicyclemod.sound.ModSounds;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
  public static final DeferredRegister<Item> ITEMS =
      DeferredRegister.create(ForgeRegistries.ITEMS, UnicycleMod.MOD_ID);

  public static final RegistryObject<Item> IRON_UNICYCLE_ITEM =
      ITEMS.register("iron_unicycle", () -> new UnicycleItem(new Item.Properties().stacksTo(1)));

  public static final RegistryObject<Item> BAD_DISK_ITEM =
          ITEMS.register("music_disc_bad", () -> new RecordItem(8, ModSounds.MUSIC_BAD, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 3440));

  public static final RegistryObject<Item> POLAND_DISK_ITEM =
          ITEMS.register("music_disc_poland", () -> new RecordItem(8, ModSounds.MUSIC_POLAND, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 3180));

  public static final RegistryObject<Item> TITANIC_DISK_ITEM =
          ITEMS.register("music_disc_titanic", () -> new RecordItem(8, ModSounds.MUSIC_TITANIC, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 5120));

  public static final RegistryObject<Item> WII_DISK_ITEM =
          ITEMS.register("music_disc_wii", () -> new RecordItem(8, ModSounds.MUSIC_WII, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 2880));

  public static final RegistryObject<Item> GRAIL_DISK_ITEM =
          ITEMS.register("music_disc_grail", () -> new RecordItem(8, ModSounds.MUSIC_GRAIL, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 2740));

  public static final RegistryObject<Item> TUBES_DISK_ITEM =
          ITEMS.register("music_disc_tubes", () -> new RecordItem(8, ModSounds.MUSIC_TUBES, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 2560));

  public static void register(IEventBus eventBus) {
    ITEMS.register(eventBus);
  }
}
