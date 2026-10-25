package xox.labvorty.foxes_and_stuff.neoforge;

import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import xox.labvorty.foxes_and_stuff.FoxesStuffMod;
import xox.labvorty.foxes_and_stuff.client.FoxesStuffClient;
import xox.labvorty.foxes_and_stuff.compat.vortylib.VortyLibCompat;
import xox.labvorty.foxes_and_stuff.init.FoxesStuffMenus;
import xox.labvorty.foxes_and_stuff.model.FoxFluffModel;
import xox.labvorty.foxes_and_stuff.screen.FoxInventoryScreen;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT, modid = FoxesStuffMod.MOD_ID)
public class NeoForgeClientEvents {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            NeoForgeClientConfig.bind();
            FoxesStuffClient.init();

            if (ModList.get().isLoaded("vortylib")) {
                VortyLibCompat.init();
            }
        });
    }

    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        if (event.getRenderer(EntityType.FOX) instanceof FoxRenderer renderer) {
            FoxesStuffClient.addLayers(renderer, renderer::addLayer);
        }
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(FoxFluffModel.LAYER_LOCATION, FoxFluffModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(FoxesStuffMenus.FOX_INVENTORY.get(), FoxInventoryScreen::new);
    }
}
