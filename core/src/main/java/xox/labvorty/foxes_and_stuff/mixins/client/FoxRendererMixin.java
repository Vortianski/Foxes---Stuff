package xox.labvorty.foxes_and_stuff.mixins.client;

import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Fox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xox.labvorty.foxes_and_stuff.data.holder.FoxVariant;
import xox.labvorty.foxes_and_stuff.mixin_helpers.TameableFox;

@Mixin(FoxRenderer.class)
public class FoxRendererMixin {
    @Inject(
            method = "getTextureLocation(Lnet/minecraft/world/entity/animal/Fox;)Lnet/minecraft/resources/ResourceLocation;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void foxesstuff$moddedTexture(Fox fox, CallbackInfoReturnable<ResourceLocation> cir) {
        if (fox instanceof TameableFox tameableFox) {
            FoxVariant foxVariant = tameableFox.foxesstuff$getModdedFoxVariant();
            if (foxVariant != null) {
                cir.setReturnValue(fox.isSleeping() ? foxVariant.sleepingTexture() : foxVariant.defaultTexture());
            }
        }
    }
}
