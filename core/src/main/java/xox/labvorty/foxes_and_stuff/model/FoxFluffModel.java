package xox.labvorty.foxes_and_stuff.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class FoxFluffModel<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("foxesstuff", "fox_fluff_model"), "main");

    private static final float BABY_HEAD_SCALE = 2.0F;
    private static final float BABY_BODY_SCALE = 2.0F;
    private static final float BABY_HEAD_Y_OFFSET = 8.0F;
    private static final float BABY_HEAD_Z_OFFSET = 3.35F;
    private static final float BODY_Y_OFFSET = 24.0F;

    public final ModelPart head;
    public final ModelPart body;
    public final ModelPart tail;

    public FoxFluffModel(ModelPart root) {
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.tail = this.body.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 14).addBox(-3.0F, -0.9F, -5.0F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offset(-1.0F, 16.5F, -3.0F));
        head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(18, 0).addBox(7.0F, -6.0F, -1.0F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9215F, 4.0F, 0.8646F, 0.0F, 0.4363F, 0.0F));
        head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 18).addBox(-7.0F, -6.0F, -1.0F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9215F, 4.0F, 0.8646F, 0.0F, -0.4363F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 16.0F, -6.0F, ((float) Math.PI / 2F), 0.0F, 0.0F));

        body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 0).addBox(2.0F, 0.0F, -1.0F, 4.0F, 9.0F, 5.0F, new CubeDeformation(0.25F)),
                PartPose.offsetAndRotation(-4.0F, 15.0F, -1.0F, -0.05235988F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        if (this.young) {
            poseStack.pushPose();
            float headScale = 1.5F / BABY_HEAD_SCALE;
            poseStack.scale(headScale, headScale, headScale);
            poseStack.translate(0.0F, BABY_HEAD_Y_OFFSET / 16.0F, BABY_HEAD_Z_OFFSET / 16.0F);
            head.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
            poseStack.popPose();

            poseStack.pushPose();
            float bodyScale = 1.0F / BABY_BODY_SCALE;
            poseStack.scale(bodyScale, bodyScale, bodyScale);
            poseStack.translate(0.0F, BODY_Y_OFFSET / 16.0F, 0.0F);
            body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
            poseStack.popPose();
        } else {
            head.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
            body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        }
    }
}