package xox.labvorty.foxes_and_stuff.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import net.minecraft.client.model.FoxModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import org.jetbrains.annotations.NotNull;
import xox.labvorty.foxes_and_stuff.init.FoxesStuffItems;
import xox.labvorty.foxes_and_stuff.items.armor.FoxArmorItem;
import xox.labvorty.foxes_and_stuff.mixin_helpers.TameableFox;

public class FoxArmorLayer extends RenderLayer<Fox, FoxModel<Fox>> {
    private static final float PIVOT_Y = 1.5F;
    private static final float INFLATE = 1.08F;

    public FoxArmorLayer(RenderLayerParent<Fox, FoxModel<Fox>> renderer) {
        super(renderer);
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
        if (fox instanceof TameableFox tameableFox) {
            ItemStack itemStack = tameableFox.foxesstuff$getFoxInventory().getItem(0);
            if (itemStack.getItem() instanceof FoxArmorItem foxArmorItem) {
                poseStack.pushPose();

                poseStack.translate(0.0F, PIVOT_Y, 0.0F);
                poseStack.scale(INFLATE, INFLATE, INFLATE);
                poseStack.translate(0.0F, -PIVOT_Y, 0.0F);

                int color = -1;
                if (itemStack.getItem() == FoxesStuffItems.LEATHER_FOX_ARMOR.get()) {
                    color = itemStack.getOrDefault(DataComponents.DYED_COLOR, new DyedItemColor(DyedItemColor.LEATHER_COLOR, false)).rgb();
                }

                VertexConsumer vertexconsumer = itemStack.isEnchanted() ? VertexMultiConsumer.create(multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(foxArmorItem.getTexture())), multiBufferSource.getBuffer(RenderType.entityGlint())) : multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(foxArmorItem.getTexture()));
                getParentModel().renderToBuffer(poseStack, vertexconsumer, packedLight, LivingEntityRenderer.getOverlayCoords(fox, 0.0F), color);

                poseStack.popPose();
            }
        }
    }
}