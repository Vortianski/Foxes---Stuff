package xox.labvorty.foxes_and_stuff.mixins;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.fox.Fox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xox.labvorty.foxes_and_stuff.mixin_helpers.TameableFox;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "dropEquipment(Lnet/minecraft/server/level/ServerLevel;)V", at = @At("TAIL"))
    private void foxesstuff$dropFoxInventory(ServerLevel level, CallbackInfo ci) {
        if ((Object) this instanceof Fox fox) {
            ((TameableFox) fox).foxesstuff$dropFoxInventory();
        }
    }
}
