package xox.labvorty.foxes_and_stuff.fabric;

import net.fabricmc.api.ClientModInitializer;
import dev.architectury.registry.client.gui.MenuScreenRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityRenderLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.world.entity.EntityType;
import xox.labvorty.foxes_and_stuff.client.FoxesStuffClient;
import xox.labvorty.foxes_and_stuff.init.FoxesStuffResources;
import xox.labvorty.foxes_and_stuff.init.FoxesStuffMenus;
import xox.labvorty.foxes_and_stuff.model.FoxFluffModel;
import xox.labvorty.foxes_and_stuff.screen.FoxInventoryScreen;

public class FoxesStuffFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FabricClientConfig.load();
        MenuScreenRegistry.registerScreenFactory(FoxesStuffMenus.FOX_INVENTORY.get(), FoxInventoryScreen::new);
        ModelLayerRegistry.registerModelLayer(FoxFluffModel.LAYER_LOCATION, FoxFluffModel::createBodyLayer);
        FoxesStuffResources.register();

        LivingEntityRenderLayerRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityType == EntityType.FOX && entityRenderer instanceof FoxRenderer renderer) {
                FoxesStuffClient.addLayers(renderer, registrationHelper::register);
            }
        });
    }
}
