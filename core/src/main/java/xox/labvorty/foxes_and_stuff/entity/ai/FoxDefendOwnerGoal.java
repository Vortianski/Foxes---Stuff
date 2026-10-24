package xox.labvorty.foxes_and_stuff.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import xox.labvorty.foxes_and_stuff.mixin_helpers.TameableFox;

import java.util.EnumSet;

public class FoxDefendOwnerGoal extends TargetGoal {
    private final Fox fox;
    private final TameableFox tameable;
    private LivingEntity candidate;
    private int hurtByStamp;
    private int hurtStamp;

    public FoxDefendOwnerGoal(Fox fox) {
        super(fox, false);
        this.fox = fox;
        this.tameable = (TameableFox) fox;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if (!tameable.foxesstuff$isFoxTame() || tameable.foxesstuff$isFoxOrderedToSit()) return false;
        LivingEntity owner = tameable.foxesstuff$getFoxOwner();
        if (owner == null) return false;

        LivingEntity attacker = owner.getLastHurtByMob();
        int attackerStamp = owner.getLastHurtByMobTimestamp();
        if (attacker != null && attackerStamp != hurtByStamp) {
            if (isValidTarget(owner, attacker)) {
                candidate = attacker;
                return true;
            }
            hurtByStamp = attackerStamp;
        }

        LivingEntity victim = owner.getLastHurtMob();
        int victimStamp = owner.getLastHurtMobTimestamp();
        if (victim != null && victimStamp != hurtStamp) {
            if (isValidTarget(owner, victim)) {
                candidate = victim;
                return true;
            }

            hurtStamp = victimStamp;
        }
        return false;
    }

    private boolean isValidTarget(LivingEntity owner, LivingEntity c) {
        if (c == owner || c == fox || !c.isAlive()) return false;
        if (c instanceof Fox other && owner.getUUID().equals(((TameableFox) other).foxesstuff$getFoxOwnerUUID())) return false;
        if (c instanceof TamableAnimal pet && pet.isOwnedBy(owner)) return false;
        if (c instanceof Player p && owner instanceof Player o && !o.canHarmPlayer(p)) return false;
        return canAttack(c, TargetingConditions.DEFAULT);
    }

    @Override
    public void start() {
        LivingEntity owner = tameable.foxesstuff$getFoxOwner();
        if (owner != null) {
            hurtByStamp = owner.getLastHurtByMobTimestamp();
            hurtStamp = owner.getLastHurtMobTimestamp();
        }
        fox.setTarget(candidate);
        tameable.foxesstuff$setFoxDefending(true);
        super.start();
    }
}