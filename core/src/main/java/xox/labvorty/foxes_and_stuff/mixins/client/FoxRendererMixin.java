package xox.labvorty.foxes_and_stuff.mixins.client;

import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.client.renderer.entity.state.FoxRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xox.labvorty.foxes_and_stuff.data.holder.FoxVariant;
import xox.labvorty.foxes_and_stuff.mixin_helpers.TameableFox;
import xox.labvorty.foxes_and_stuff.mixin_helpers.FoxRenderStateData;

@Mixin(FoxRenderer.class)
public class FoxRendererMixin {
    @Inject(
            method = "getTextureLocation(Lnet/minecraft/client/renderer/entity/state/FoxRenderState;)Lnet/minecraft/resources/Identifier;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void foxesstuff$moddedTexture(FoxRenderState state, CallbackInfoReturnable<Identifier> cir) {
        FoxVariant foxVariant = ((FoxRenderStateData) state).foxesstuff$getVariant();
        if (foxVariant != null) {
            cir.setReturnValue(state.isSleeping ? foxVariant.sleepingTexture() : foxVariant.defaultTexture());
        }
    }

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/animal/fox/Fox;Lnet/minecraft/client/renderer/entity/state/FoxRenderState;F)V", at = @At("TAIL"))
    private void foxesstuff$extract(Fox fox, FoxRenderState state, float partialTick, org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci) {
        TameableFox tameableFox = (TameableFox) fox;
        ItemStack armor = tameableFox.foxesstuff$getFoxInventory().getItem(0);
        ((FoxRenderStateData) state).foxesstuff$set(
                fox.getUUID(),
                tameableFox.foxesstuff$isFoxTame(),
                tameableFox.foxesstuff$getCollarColor(),
                armor,
                tameableFox.foxesstuff$getModdedFoxVariant()
        );
    }
}
