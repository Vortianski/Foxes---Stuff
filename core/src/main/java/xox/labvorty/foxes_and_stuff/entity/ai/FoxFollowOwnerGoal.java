package xox.labvorty.foxes_and_stuff.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.level.pathfinder.PathType;
import xox.labvorty.foxes_and_stuff.mixin_helpers.TameableFox;

import java.util.EnumSet;

public class FoxFollowOwnerGoal extends Goal {
    private static final double WAKE_DISTANCE = 7.0D;
    private static final double TELEPORT_DISTANCE_SQR = 144.0D;

    private final Fox fox;
    private final TameableFox tameable;
    private final double speedModifier;
    private final float startDistance;
    private final float stopDistance;
    private LivingEntity owner;
    private int timeToRecalcPath;
    private float oldWaterCost;

    public FoxFollowOwnerGoal(Fox fox, double speed, float startDist, float stopDist) {
        this.fox = fox;
        this.tameable = (TameableFox) fox;
        this.speedModifier = speed;
        this.startDistance = startDist;
        this.stopDistance = stopDist;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!tameable.foxesstuff$isFoxTame() || tameable.foxesstuff$isFoxOrderedToSit()) return false;
        if (fox.getTarget() != null) return false;
        LivingEntity o = tameable.foxesstuff$getFoxOwner();
        if (o == null || o.isSpectator()) return false;

        double start = fox.isSleeping() ? WAKE_DISTANCE : startDistance;
        if (fox.distanceToSqr(o) < start * start) return false;

        this.owner = o;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return owner != null
                && !fox.getNavigation().isDone()
                && !tameable.foxesstuff$isFoxOrderedToSit()
                && fox.getTarget() == null
                && fox.distanceToSqr(owner) > (double) (stopDistance * stopDistance);
    }

    @Override
    public void start() {
        timeToRecalcPath = 0;
        oldWaterCost = fox.getPathfindingMalus(PathType.WATER);
        fox.setPathfindingMalus(PathType.WATER, 0.0F);
    }

    @Override
    public void stop() {
        owner = null;
        fox.getNavigation().stop();
        fox.setPathfindingMalus(PathType.WATER, oldWaterCost);
    }

    @Override
    public void tick() {
        fox.getLookControl().setLookAt(owner, 10.0F, (float) fox.getMaxHeadXRot());
        if (--timeToRecalcPath <= 0) {
            timeToRecalcPath = adjustedTickDelay(10);
            if (fox.distanceToSqr(owner) >= TELEPORT_DISTANCE_SQR) {
                teleportToOwner();
            } else {
                fox.getNavigation().moveTo(owner, speedModifier);
            }
        }
    }

    private void teleportToOwner() {
        for (int i = 0; i < 10; i++) {
            double x = owner.getX() + fox.getRandom().nextInt(5) - 2;
            double y = owner.getY() + fox.getRandom().nextInt(3) - 1;
            double z = owner.getZ() + fox.getRandom().nextInt(5) - 2;
            if (fox.randomTeleport(x, y, z, true)) {
                fox.getNavigation().stop();
                return;
            }
        }
    }
}