package xox.labvorty.foxes_and_stuff.mixins.client;

import net.minecraft.client.renderer.entity.layers.FoxHeldItemLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(FoxHeldItemLayer.class)
public abstract class FoxHeldItemLayerMixin {
    @ModifyArgs(
            method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/FoxRenderState;FF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V",
                    ordinal = 1
            )
    )
    private void foxesstuff$adjustSleepingBabyItemOffset(Args args) {
        foxesstuff$shiftBabyItem(args);
    }

    @ModifyArgs(
            method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/FoxRenderState;FF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V",
                    ordinal = 2
            )
    )
    private void foxesstuff$adjustStandingBabyItemOffset(Args args) {
        foxesstuff$shiftBabyItem(args);
    }

    private static void foxesstuff$shiftBabyItem(Args args) {
        args.set(0, (Float) args.get(0) - 0.04F);
        args.set(2, (Float) args.get(2) - 0.08F);
    }
}
