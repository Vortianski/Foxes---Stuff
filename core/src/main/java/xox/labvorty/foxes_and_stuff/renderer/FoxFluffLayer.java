package xox.labvorty.foxes_and_stuff.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.animal.fox.FoxModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.FoxRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.fox.Fox;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.jetbrains.annotations.Nullable;
import xox.labvorty.foxes_and_stuff.data.configs.ClientConfig;
import xox.labvorty.foxes_and_stuff.data.holder.FoxVariant;
import xox.labvorty.foxes_and_stuff.mixin_helpers.FoxModelAccessor;
import xox.labvorty.foxes_and_stuff.mixin_helpers.FoxRenderStateData;
import xox.labvorty.foxes_and_stuff.model.FoxFluffModel;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class FoxFluffLayer extends RenderLayer<FoxRenderState, FoxModel> {
    private static final Identifier RED_FLUFF = Identifier.fromNamespaceAndPath("foxesstuff", "textures/entity/variants/red/fluff.png");
    private static final Identifier ARCTIC_FLUFF = Identifier.fromNamespaceAndPath("foxesstuff", "textures/entity/variants/arctic/fluff.png");
    private final Map<UUID, FoxFluffModel> fluffModels = new LinkedHashMap<>(128, 0.75F, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<UUID, FoxFluffModel> eldest) {
            return this.size() > 256;
        }
    };

    public FoxFluffLayer(RenderLayerParent<FoxRenderState, FoxModel> parent) {
        super(parent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int packedLight, FoxRenderState state, float limbSwing, float limbSwingAmount) {
        Identifier texture = this.resolveTexture(state);
        if (texture == null || !ClientConfig.renderFluff()) return;

        FoxModel parentModel = this.getParentModel();
        FoxModelAccessor accessor = (FoxModelAccessor) parentModel;
        FoxFluffModel fluffModel = this.fluffModels.computeIfAbsent(((FoxRenderStateData) state).foxesstuff$getUuid(), ignored -> new FoxFluffModel(Minecraft.getInstance().getEntityModels().bakeLayer(FoxFluffModel.LAYER_LOCATION)));
        fluffModel.head.loadPose(parentModel.head.storePose());
        fluffModel.body.loadPose(accessor.foxesstuff$getBody().storePose());
        fluffModel.tail.loadPose(accessor.foxesstuff$getTail().storePose());
        fluffModel.babyBody.loadPose(accessor.foxesstuff$getBody().storePose());
        fluffModel.babyTail.loadPose(accessor.foxesstuff$getTail().storePose());

        int overlay = OverlayTexture.pack(0.0F, state.hasRedOverlay);
        if (state.isBaby) {
            Vector3f shift = new Quaternionf().rotationZYX(fluffModel.head.zRot, fluffModel.head.yRot, fluffModel.head.xRot).transform(-0.75F, 0.0F, -1.0F, new Vector3f());
            fluffModel.head.x += shift.x;
            fluffModel.head.y += shift.y;
            fluffModel.head.z += shift.z;
            fluffModel.head.xScale = 0.75F;
            fluffModel.head.yScale = 0.75F;
            fluffModel.head.zScale = 0.75F;
        } else {
            fluffModel.head.xScale = 1.0F;
            fluffModel.head.yScale = 1.0F;
            fluffModel.head.zScale = 1.0F;
            fluffModel.body.xScale = 1.0F;
            fluffModel.body.yScale = 1.0F;
            fluffModel.body.zScale = 1.0F;
        }
        collector.submitModelPart(fluffModel.head, poseStack, RenderTypes.entityCutout(texture), packedLight, overlay, null);
        collector.submitModelPart(state.isBaby ? fluffModel.babyBody : fluffModel.body, poseStack, RenderTypes.entityCutout(texture), packedLight, overlay, null);
    }

    @Nullable
    private Identifier resolveTexture(FoxRenderState state) {
        FoxVariant foxVariant = ((FoxRenderStateData) state).foxesstuff$getVariant();
        if (foxVariant != null) return foxVariant.fluffTexture();
        return state.variant == Fox.Variant.RED ? RED_FLUFF : state.variant == Fox.Variant.SNOW ? ARCTIC_FLUFF : null;
    }
}
