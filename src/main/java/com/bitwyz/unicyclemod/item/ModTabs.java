package com.bitwyz.unicyclemod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.bitwyz.unicyclemod.UnicycleMod.MOD_ID;

public class ModTabs {

  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
      DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

  public static final RegistryObject<CreativeModeTab> UNICYCLE_TAB =
      CREATIVE_MODE_TABS.register(
          "unicycle_tab",
          () ->
              CreativeModeTab.builder()
                  .withTabsBefore(CreativeModeTabs.COMBAT)
                  .title(Component.translatable("itemGroup.unicycle_tab"))
                  .icon(() -> ModItems.IRON_UNICYCLE_ITEM.get().getDefaultInstance())
                  .displayItems(
                      (parameters, output) -> {
                        output.accept(ModItems.IRON_UNICYCLE_ITEM.get());
                        output.accept(ModItems.BAD_DISK_ITEM.get());
                        output.accept(ModItems.POLAND_DISK_ITEM.get());
                        output.accept(ModItems.TITANIC_DISK_ITEM.get());
                        output.accept(ModItems.WII_DISK_ITEM.get());
                        output.accept(ModItems.TUBES_DISK_ITEM.get());
                        output.accept(ModItems.GRAIL_DISK_ITEM.get());
                      })
                  .build());

  public static void register(IEventBus eventBus) {
    CREATIVE_MODE_TABS.register(eventBus);
  }
}
