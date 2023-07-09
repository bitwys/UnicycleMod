package com.bitwyz.unicyclemod;

import com.bitwyz.unicyclemod.entity.IronUnicycleModel;
import com.bitwyz.unicyclemod.entity.ModEntities;
import com.bitwyz.unicyclemod.entity.UnicycleRenderer;
import com.bitwyz.unicyclemod.item.ModItems;
import com.bitwyz.unicyclemod.item.ModTabs;
import com.bitwyz.unicyclemod.sound.ModSounds;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(UnicycleMod.MOD_ID)
public class UnicycleMod {
  // Define mod id in a common place for everything to reference
  public static final String MOD_ID = "unicyclemod";
  // Directly reference a slf4j logger
  private static final Logger LOGGER = LogUtils.getLogger();
  // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod"
  // namespace
  /*
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
  // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
  // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

  // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
  public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("panel_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
  // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
  public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("panel_block", () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties()));

  // Creates a new food item with the id "examplemod:example_id", nutrition 1 and saturation 2
  public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("goober", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
          .alwaysEat().nutrition(1).saturationMod(2f).build())));

  // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
  public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
          .withTabsBefore(CreativeModeTabs.COMBAT)
          .icon(() -> EXAMPLE_ITEM.get().getDefaultInstance())
          .displayItems((parameters, output) -> {
              output.accept(EXAMPLE_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
              output.accept(EXAMPLE_BLOCK_ITEM.get());
          }).build());

   */

  public UnicycleMod() {
    IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    // Register the commonSetup method for modloading
    modEventBus.addListener(this::commonSetup);

    // Register the Deferred Register to the mod event bus so items get registered
    ModItems.register(modEventBus);
    // Register the Deferred Register to the mod event bus so tabs get registered
    ModTabs.register(modEventBus);
    ModEntities.register(modEventBus);
    ModSounds.register(modEventBus);

    // Register ourselves for server and other game events we are interested in
    MinecraftForge.EVENT_BUS.register(this);

    // Register the item to a creative tab
    modEventBus.addListener(this::addCreative);
  }

  private void commonSetup(final FMLCommonSetupEvent event) {
    // Some common setup code
    LOGGER.info("HELLO FROM COMMON SETUP");
    LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
  }

  // Add the example block item to the building blocks tab
  private void addCreative(BuildCreativeModeTabContentsEvent event) {
    /*
    if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
        event.accept(EXAMPLE_BLOCK_ITEM);
    */
  }

  // You can use SubscribeEvent and let the Event Bus discover methods to call
  @SubscribeEvent
  public void onServerStarting(ServerStartingEvent event) {
    // Do something when the server starts
    LOGGER.info("HELLO from server starting");
  }

  // You can use EventBusSubscriber to automatically register all static methods in the class
  // annotated with @SubscribeEvent
  @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
  public static class ClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
      // Some client setup code
      LOGGER.info("HELLO FROM CLIENT SETUP");
      LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
      event.registerLayerDefinition(
          IronUnicycleModel.LAYER_LOCATION, IronUnicycleModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
      event.registerEntityRenderer(ModEntities.UNICYCLE.get(), UnicycleRenderer::new);
    }
  }
}
