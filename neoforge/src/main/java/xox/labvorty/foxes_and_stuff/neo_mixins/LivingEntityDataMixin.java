package xox.labvorty.foxes_and_stuff.neo_mixins;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xox.labvorty.foxes_and_stuff.neo_mixin_helpers.LivingEntityAccessor;

import java.util.Stack;

@Mixin(LivingEntity.class)
public class LivingEntityDataMixin implements LivingEntityAccessor {
    @Shadow
    private @Nullable Stack<DamageContainer> damageContainers;

    @Override
    public Stack<DamageContainer> foxesstuff$getDamageContainers() {
        return damageContainers;
    };
}
