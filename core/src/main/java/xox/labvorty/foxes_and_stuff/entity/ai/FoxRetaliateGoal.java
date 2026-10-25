package xox.labvorty.foxes_and_stuff.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import xox.labvorty.foxes_and_stuff.mixin_helpers.TameableFox;

public class FoxRetaliateGoal extends HurtByTargetGoal {
    private final Fox fox;
    private final TameableFox tameable;

    public FoxRetaliateGoal(Fox fox) {
        super(fox);
        this.fox = fox;
        this.tameable = (TameableFox) fox;
    }

    @Override
    public boolean canUse() {
        if (!tameable.foxesstuff$isFoxTame()) return false;
        LivingEntity attacker = fox.getLastHurtByMob();
        if (attacker == null || attacker instanceof Player || tameable.foxesstuff$isFoxOwnedBy(attacker)) return false;
        return super.canUse();
    }

    @Override
    public void start() {
        tameable.foxesstuff$setFoxOrderedToSit(false);
        tameable.foxesstuff$setFoxDefending(true);
        super.start();
    }
}