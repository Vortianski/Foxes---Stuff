package xox.labvorty.foxes_and_stuff.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.state.FoxRenderState;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class FoxFluffModel extends EntityModel<FoxRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath("foxesstuff", "fox_fluff_model"), "main");

    private static final float BABY_HEAD_SCALE = 2.0F;
    private static final float BABY_BODY_SCALE = 2.0F;
    private static final float BABY_HEAD_Y_OFFSET = 8.0F;
    private static final float BABY_HEAD_Z_OFFSET = 3.35F;
    private static final float BODY_Y_OFFSET = 24.0F;

    public final ModelPart head;
    public final ModelPart body;
    public final ModelPart tail;
    public final ModelPart babyBody;
    public final ModelPart babyTail;

    public FoxFluffModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.tail = this.body.getChild("tail");
        this.babyBody = root.getChild("baby_body");
        this.babyTail = this.babyBody.getChild("tail");
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

        // Хвост лисёнка: бокс 3x3x6 (w=3, h=3, d=6), texOffs(10, 20), текстура 32x32.
        // UV (U, V): верх 16-18 / 20-25, низ 19-21 / 20-25,
        //   левый бок 10-15 / 26-28 (кончик хвоста = U10), перед (основание) 16-18 / 26-28,
        //   правый бок 19-24 / 26-28 (кончик = U24), зад (кончик) 25-27 / 26-28.
        // Мех в fluff.png лежит на верхе, низе и боках первыми 3 рядами от кончика (2 сплошных + ряд зубцов).
        PartDefinition babyBody = partdefinition.addOrReplaceChild("baby_body", CubeListBuilder.create(), PartPose.ZERO);
        babyBody.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(10, 20).addBox(-1.5F, -1.48F, -1.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.125F)), PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(@NotNull FoxRenderState state) {
    }
}
