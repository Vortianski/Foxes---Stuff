package xox.labvorty.foxes_and_stuff.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.FoxModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Fox;
import org.jetbrains.annotations.NotNull;
import xox.labvorty.foxes_and_stuff.FoxesStuffMod;
import xox.labvorty.foxes_and_stuff.mixin_helpers.TameableFox;

public class FoxCollarLayer extends RenderLayer<Fox, FoxModel<Fox>> {
    private static final ResourceLocation COLLAR_TEXTURE = FoxesStuffMod.location("textures/entity/fox_collar.png");

    public FoxCollarLayer(RenderLayerParent<Fox, FoxModel<Fox>> parent) {
        super(parent);
    }

    @Override
    public void render(
            @NotNull PoseStack poseStack,
            @NotNull MultiBufferSource multiBufferSource,
            int packedLight,
            @NotNull Fox fox,
            float limbSwing,
            float limbSwingAmount,
            float partialTick,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        if (!(fox instanceof TameableFox tf) || !tf.foxesstuff$isFoxTame() || fox.isInvisible()) return;
        int color = tf.foxesstuff$getCollarColor().getTextureDiffuseColor() | 0xFF000000;
        renderColoredCutoutModel(this.getParentModel(), COLLAR_TEXTURE, poseStack, multiBufferSource, packedLight, fox, color);
    }
}