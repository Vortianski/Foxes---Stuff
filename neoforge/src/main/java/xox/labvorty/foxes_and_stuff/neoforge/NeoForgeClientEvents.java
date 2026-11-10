
package xox.labvorty.foxes_and_stuff.neoforge;

import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import xox.labvorty.foxes_and_stuff.FoxesAndStuffMod;
import xox.labvorty.foxes_and_stuff.client.FoxesStuffClient;
import xox.labvorty.foxes_and_stuff.init.FoxesStuffMenus;
import xox.labvorty.foxes_and_stuff.model.FoxFluffModel;
import xox.labvorty.foxes_and_stuff.screen.FoxInventoryScreen;

@EventBusSubscriber(
        value = Dist.CLIENT,
        modid = FoxesAndStuffMod.MOD_ID
)
public class NeoForgeClientEvents {
    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        if (event.getRenderer(EntityType.FOX) instanceof FoxRenderer renderer) {
            FoxesStuffClient.addLayers(renderer, renderer::addLayer);
        }
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(
            EntityRenderersEvent.RegisterLayerDefinitions event
    ) {
        event.registerLayerDefinition(
                FoxFluffModel.LAYER_LOCATION,
                FoxFluffModel::createBodyLayer
        );
    }

    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(
                FoxesStuffMenus.FOX_INVENTORY.get(),
                FoxInventoryScreen::new
        );
    }
}
