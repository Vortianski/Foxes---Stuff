package xox.labvorty.foxes_and_stuff.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.animal.fox.FoxModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.FoxRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import xox.labvorty.foxes_and_stuff.FoxesStuffMod;
import xox.labvorty.foxes_and_stuff.mixin_helpers.FoxRenderStateData;

public class FoxCollarLayer extends RenderLayer<FoxRenderState, FoxModel> {
    private static final Identifier COLLAR_TEXTURE = FoxesStuffMod.location("textures/entity/fox_collar.png");
    private static final Identifier BABY_COLLAR_TEXTURE = FoxesStuffMod.location("textures/entity/fox_collar_baby.png");

    public FoxCollarLayer(RenderLayerParent<FoxRenderState, FoxModel> parent) {
        super(parent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int packedLight, FoxRenderState state, float limbSwing, float limbSwingAmount) {
        FoxRenderStateData data = (FoxRenderStateData) state;
        if (!data.foxesstuff$isTame() || state.isInvisible) return;
        int color = data.foxesstuff$getCollarColor().getTextureDiffuseColor() | 0xFF000000;
        Identifier texture = state.isBaby ? BABY_COLLAR_TEXTURE : COLLAR_TEXTURE;
        collector.order(1).submitModel(this.getParentModel(), state, poseStack, RenderTypes.entityCutout(texture), packedLight,
                OverlayTexture.pack(0.0F, state.hasRedOverlay), color, null, state.outlineColor, null);
    }
}
