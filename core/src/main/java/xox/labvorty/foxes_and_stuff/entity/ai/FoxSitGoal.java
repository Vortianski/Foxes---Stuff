package xox.labvorty.foxes_and_stuff.entity.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Fox;
import xox.labvorty.foxes_and_stuff.mixin_helpers.TameableFox;

import java.util.EnumSet;

public class FoxSitGoal extends Goal {
    private final Fox fox;
    private final TameableFox tameable;

    public FoxSitGoal(Fox fox) {
        this.fox = fox;
        this.tameable = (TameableFox) fox;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        return tameable.foxesstuff$isFoxTame()
                && tameable.foxesstuff$isFoxOrderedToSit()
                && !fox.isInWater()
                && fox.getTarget() == null;
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public void start() {
        fox.getNavigation().stop();
        fox.setJumping(false);
    }
}