package xox.labvorty.foxes_and_stuff.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.animal.fox.FoxModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.FoxRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import xox.labvorty.foxes_and_stuff.init.FoxesStuffItems;
import xox.labvorty.foxes_and_stuff.items.armor.FoxArmorItem;
import xox.labvorty.foxes_and_stuff.mixin_helpers.FoxRenderStateData;

public class FoxArmorLayer extends RenderLayer<FoxRenderState, FoxModel> {
    private static final float PIVOT_Y = 1.5F;
    private static final float INFLATE = 1.08F;

    public FoxArmorLayer(RenderLayerParent<FoxRenderState, FoxModel> parent) {
        super(parent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int packedLight, FoxRenderState state, float limbSwing, float limbSwingAmount) {
        ItemStack itemStack = ((FoxRenderStateData) state).foxesstuff$getArmor();
        if (!(itemStack.getItem() instanceof FoxArmorItem foxArmorItem)) return;

        poseStack.pushPose();
        poseStack.translate(0.0F, PIVOT_Y, 0.0F);
        poseStack.scale(INFLATE, INFLATE, INFLATE);
        poseStack.translate(0.0F, -PIVOT_Y, 0.0F);

        int color = itemStack.getItem() == FoxesStuffItems.LEATHER_FOX_ARMOR.get()
                ? itemStack.getOrDefault(DataComponents.DYED_COLOR, new DyedItemColor(DyedItemColor.LEATHER_COLOR)).rgb()
                : -1;
        collector.submitModel(this.getParentModel(), state, poseStack, RenderTypes.entityCutout(foxArmorItem.getTexture(state.isBaby)), packedLight,
                OverlayTexture.pack(0.0F, state.hasRedOverlay), color, null, state.outlineColor, null);
        if (itemStack.isEnchanted()) {
            collector.submitModel(this.getParentModel(), state, poseStack, RenderTypes.entityGlint(), packedLight,
                    OverlayTexture.pack(0.0F, state.hasRedOverlay), -1, null, state.outlineColor, null);
        }
        poseStack.popPose();
    }
}
