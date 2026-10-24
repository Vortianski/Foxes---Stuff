package xox.labvorty.foxes_and_stuff.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.FoxModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Fox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xox.labvorty.foxes_and_stuff.data.configs.ClientConfig;
import xox.labvorty.foxes_and_stuff.data.holder.FoxVariant;
import xox.labvorty.foxes_and_stuff.mixin_helpers.FoxModelAccessor;
import xox.labvorty.foxes_and_stuff.mixin_helpers.TameableFox;
import xox.labvorty.foxes_and_stuff.model.FoxFluffModel;

public class FoxFluffLayer extends RenderLayer<Fox, FoxModel<Fox>> {
    private static final ResourceLocation RED_FLUFF = ResourceLocation.fromNamespaceAndPath("foxesstuff", "textures/entity/variants/red/fluff.png");
    private static final ResourceLocation ARCTIC_FLUFF = ResourceLocation.fromNamespaceAndPath("foxesstuff", "textures/entity/variants/arctic/fluff.png");

    private final FoxFluffModel<?> foxFluffModel;

    public FoxFluffLayer(RenderLayerParent<Fox, FoxModel<Fox>> renderer) {
        super(renderer);
        this.foxFluffModel = new FoxFluffModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(FoxFluffModel.LAYER_LOCATION));
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
        ResourceLocation texture = resolveTexture(fox);
        if (texture == null || !ClientConfig.renderFluff()) return;

        FoxModel<Fox> foxModel = this.getParentModel();
        FoxModelAccessor accessor = (FoxModelAccessor) foxModel;
        foxFluffModel.head.copyFrom(foxModel.head);
        foxFluffModel.body.copyFrom(accessor.foxesstuff$getBody());
        foxFluffModel.tail.copyFrom(accessor.foxesstuff$getTail());
        foxFluffModel.young = foxModel.young;

        int packedOverlay = LivingEntityRenderer.getOverlayCoords(fox, 0.0F);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(texture));
        foxFluffModel.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
    }

    @Nullable
    private ResourceLocation resolveTexture(Fox fox) {
        if (fox instanceof TameableFox tameableFox) {
            FoxVariant foxVariant = tameableFox.foxesstuff$getModdedFoxVariant();
            if (foxVariant != null) {
                return foxVariant.fluffTexture();
            }
        }

        Fox.Type type = fox.getVariant();

        return type == Fox.Type.RED ? RED_FLUFF : type == Fox.Type.SNOW ? ARCTIC_FLUFF : null;
    }
}