package xox.labvorty.foxes_and_stuff.neo_mixin_helpers;

import net.neoforged.neoforge.common.damagesource.DamageContainer;

import java.util.Stack;

public interface LivingEntityAccessor {
    Stack<DamageContainer> foxesstuff$getDamageContainers();
}
