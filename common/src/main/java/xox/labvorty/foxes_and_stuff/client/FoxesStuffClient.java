package xox.labvorty.foxes_and_stuff.client;

import net.minecraft.client.model.animal.fox.FoxModel;
import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.FoxRenderState;
import xox.labvorty.foxes_and_stuff.renderer.FoxArmorLayer;
import xox.labvorty.foxes_and_stuff.renderer.FoxCollarLayer;
import xox.labvorty.foxes_and_stuff.renderer.FoxFluffLayer;

import java.util.function.Consumer;

public final class FoxesStuffClient {
    public static void addLayers(FoxRenderer renderer, Consumer<RenderLayer<FoxRenderState, FoxModel>> registrar) {
        registrar.accept(new FoxFluffLayer(renderer));
        registrar.accept(new FoxCollarLayer(renderer));
        registrar.accept(new FoxArmorLayer(renderer));
    }
}
