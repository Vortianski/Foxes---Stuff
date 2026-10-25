package xox.labvorty.foxes_and_stuff.client;

import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import net.minecraft.client.model.FoxModel;
import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.item.component.DyedItemColor;
import xox.labvorty.foxes_and_stuff.init.FoxesStuffItems;
import xox.labvorty.foxes_and_stuff.model.FoxFluffModel;
import xox.labvorty.foxes_and_stuff.renderer.FoxArmorLayer;
import xox.labvorty.foxes_and_stuff.renderer.FoxCollarLayer;
import xox.labvorty.foxes_and_stuff.renderer.FoxFluffLayer;

import java.util.function.Consumer;

public final class FoxesStuffClient {
    public static void init() {
        ColorHandlerRegistry.registerItemColors(
                (itemStack, index) -> 0xFF000000 | itemStack.getOrDefault(
                        DataComponents.DYED_COLOR,
                        new DyedItemColor(DyedItemColor.LEATHER_COLOR, false)
                ).rgb(),
                FoxesStuffItems.LEATHER_FOX_ARMOR
        );
    }

    public static void addLayers(FoxRenderer renderer, Consumer<RenderLayer<Fox, FoxModel<Fox>>> registrar) {
        registrar.accept(new FoxCollarLayer(renderer));
        registrar.accept(new FoxFluffLayer(renderer));
        registrar.accept(new FoxArmorLayer(renderer));
    }
}
