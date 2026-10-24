package xox.labvorty.foxes_and_stuff.mixins;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Stack;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
    @Accessor("damageContainers")
    Stack<DamageContainer> foxesstuff$getDamageContainers();
}
