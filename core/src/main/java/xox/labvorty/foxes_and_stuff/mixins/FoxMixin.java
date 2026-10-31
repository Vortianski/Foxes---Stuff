package xox.labvorty.foxes_and_stuff.mixins;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xox.labvorty.foxes_and_stuff.FoxPlatform;
import xox.labvorty.foxes_and_stuff.FoxesStuffMod;
import xox.labvorty.foxes_and_stuff.data.holder.FoxVariant;
import xox.labvorty.foxes_and_stuff.data.holder.ModdedFoxVariants;
import xox.labvorty.foxes_and_stuff.entity.ai.FoxDefendOwnerGoal;
import xox.labvorty.foxes_and_stuff.entity.ai.FoxFollowOwnerGoal;
import xox.labvorty.foxes_and_stuff.entity.ai.FoxRetaliateGoal;
import xox.labvorty.foxes_and_stuff.entity.ai.FoxSitGoal;
import xox.labvorty.foxes_and_stuff.items.armor.FoxArmorItem;
import xox.labvorty.foxes_and_stuff.mixin_helpers.TameableFox;

import java.util.List;
import java.util.UUID;

@Mixin(Fox.class)
public abstract class FoxMixin extends Animal implements TameableFox {
    @Unique
    private static final EntityDataAccessor<String> foxesstuff$OWNER = SynchedEntityData.defineId(Fox.class, EntityDataSerializers.STRING);
    @Unique
    private static final EntityDataAccessor<Integer> foxesstuff$COLLAR = SynchedEntityData.defineId(Fox.class, EntityDataSerializers.INT);
    @Unique
    private static final EntityDataAccessor<Boolean> foxesstuff$SIT = SynchedEntityData.defineId(Fox.class, EntityDataSerializers.BOOLEAN);
    @Unique
    private static final EntityDataAccessor<String> foxesstuff$VARIANT = SynchedEntityData.defineId(Fox.class, EntityDataSerializers.STRING);
    @Unique
    private static final EntityDataAccessor<ItemStack> foxesstuff$ARMOR_STACK = SynchedEntityData.defineId(Fox.class, EntityDataSerializers.ITEM_STACK);
    @Unique
    private static final Identifier foxesstuff$HEALTH_ID = FoxesStuffMod.location("fox_tamed_health");
    @Unique
    private static final Identifier foxesstuff$ARMOR_ID = FoxesStuffMod.location("fox_armor_bonus");
    @Unique
    private SimpleContainer foxesstuff$inventory;
    @Shadow
    private void addTrustedEntity(LivingEntity entity) {}
    @Shadow
    private boolean trusts(LivingEntity entity) { return false; }
    @Shadow
    private void clearStates() {}
    @Shadow
    private void setDefending(boolean defending) {}
    @Shadow
    private void wakeUp() {}
    @Shadow
    protected abstract void playEatingSound();
    @Shadow
    private int ticksSinceEaten;
    @Unique
    private int foxesstuff$eatTimer;

    protected FoxMixin(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void foxesstuff$eatWhenHurt(CallbackInfo ci) {
        if (this.level().isClientSide() || !this.isAlive() || !this.foxesstuff$isFoxTame()) return;

        ItemStack held = this.getItemBySlot(EquipmentSlot.MAINHAND);
        FoodProperties food = held.get(DataComponents.FOOD);

        boolean wantsToEat = food != null
                && this.getHealth() < this.getMaxHealth()
                && this.getTarget() == null
                && this.onGround()
                && !this.isSleeping();

        if (!wantsToEat) {
            this.foxesstuff$eatTimer = 0;
            return;
        }

        this.foxesstuff$eatTimer++;
        if (this.foxesstuff$eatTimer % 5 == 0) {
            this.level().broadcastEntityEvent(this, (byte) 45);
        }

        if (this.foxesstuff$eatTimer >= 32) {
            this.foxesstuff$eatTimer = 0;
            this.heal(Math.max(1, food.nutrition()));
            ItemStack result = held.finishUsingItem(this.level(), this);
            if (result != held && !result.isEmpty()) {
                this.setItemSlot(EquipmentSlot.MAINHAND, result);
            }
            this.foxesstuff$inventory.setChanged();
            this.ticksSinceEaten = 0;
        }
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void foxesstuff$init(EntityType<? extends Fox> type, Level level, CallbackInfo ci) {
        this.foxesstuff$inventory = new SimpleContainer(2) {
            @Override
            public void setChanged() {
                super.setChanged();
                foxesstuff$onInventoryChanged();
            }
        };
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void foxesstuff$defineData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(foxesstuff$OWNER, "");
        builder.define(foxesstuff$COLLAR, DyeColor.RED.getId());
        builder.define(foxesstuff$SIT, false);
        builder.define(foxesstuff$VARIANT, "");
        builder.define(foxesstuff$ARMOR_STACK, ItemStack.EMPTY);
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void foxesstuff$registerGoals(CallbackInfo ci) {
        Fox self = (Fox) (Object) this;
        this.goalSelector.addGoal(-1, new FoxSitGoal(self));
        this.goalSelector.addGoal(5, new FoxFollowOwnerGoal(self, 1.1D, 10.0F, 2.5F));
        this.targetSelector.addGoal(1, new FoxRetaliateGoal(self));
        this.targetSelector.addGoal(2, new FoxDefendOwnerGoal(self));
    }

    @Inject(method = "trusts", at = @At("HEAD"), cancellable = true)
    private void foxesstuff$trusts(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if (this.foxesstuff$isFoxTame()
                && entity instanceof Player
                && this.level().getPlayerByUUID(entity.getUUID()) != null) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "isSitting", at = @At("HEAD"), cancellable = true)
    private void foxesstuff$isSitting(CallbackInfoReturnable<Boolean> cir) {
        if (this.entityData.get(foxesstuff$SIT) && !this.isInWater()) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "getBreedOffspring(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/AgeableMob;)Lnet/minecraft/world/entity/animal/fox/Fox;", at = @At("RETURN"))
    private void foxesstuff$offspring(ServerLevel level, AgeableMob other, CallbackInfoReturnable<Fox> cir) {
        Fox baby = cir.getReturnValue();
        UUID owner = this.foxesstuff$getFoxOwnerUUID();
        if (baby != null && owner != null) {
            ((TameableFox) baby).foxesstuff$setFoxOwner(owner);
        }
    }

    @Override
    public void foxesstuff$dropFoxInventory() {
        ItemStack armor = this.foxesstuff$inventory.getItem(0);
        if (!armor.isEmpty()) {
            this.spawnAtLocation((ServerLevel) this.level(), armor);
            this.foxesstuff$inventory.setItem(0, ItemStack.EMPTY);
        }
        this.foxesstuff$inventory.setItem(1, ItemStack.EMPTY);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void foxesstuff$save(net.minecraft.world.level.storage.ValueOutput tag, CallbackInfo ci) {
        net.minecraft.world.level.storage.ValueOutput data = tag.child("FoxesAndStuff");
        UUID owner = this.foxesstuff$getFoxOwnerUUID();
        if (owner != null) data.store("Owner", UUIDUtil.CODEC, owner);
        data.putByte("CollarColor", (byte) this.foxesstuff$getCollarColor().getId());
        data.putBoolean("OrderedToSit", this.foxesstuff$isFoxOrderedToSit());
        net.minecraft.world.level.storage.ValueOutput inventory = data.child("Inventory");
        for (int slot = 0; slot < this.foxesstuff$inventory.getContainerSize(); slot++) {
            ItemStack stack = this.foxesstuff$inventory.getItem(slot);
            if (!stack.isEmpty()) inventory.store("Slot" + slot, ItemStack.CODEC, stack);
        }
        tag.putString("ModdedVariant", this.foxesstuff$getModdedVariant());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void foxesstuff$load(net.minecraft.world.level.storage.ValueInput tag, CallbackInfo ci) {
        tag.getString("ModdedVariant").ifPresent(this::foxesstuff$setModdedVariant);
        tag.child("FoxesAndStuff").ifPresent(data -> {
            this.foxesstuff$setFoxOwner(data.read("Owner", UUIDUtil.CODEC).orElse(null));
            this.foxesstuff$setCollarColor(DyeColor.byId(data.getByteOr("CollarColor", (byte) 0)));
            this.entityData.set(foxesstuff$SIT, data.getBooleanOr("OrderedToSit", false));
            data.child("Inventory").ifPresent(inventory -> {
                for (int slot = 0; slot < this.foxesstuff$inventory.getContainerSize(); slot++) {
                    ItemStack stack = inventory.read("Slot" + slot, ItemStack.CODEC).orElse(ItemStack.EMPTY);
                    this.foxesstuff$inventory.setItem(slot, stack);
                }
            });
        });
    }

    @Unique
    private void foxesstuff$onInventoryChanged() {
        if (this.level().isClientSide()) return;

        ItemStack armorStack = this.foxesstuff$inventory.getItem(0);
        if (!ItemStack.matches(this.entityData.get(foxesstuff$ARMOR_STACK), armorStack)) {
            this.entityData.set(foxesstuff$ARMOR_STACK, armorStack.copy());
        }

        if (!this.foxesstuff$isFoxTame()) return;

        AttributeInstance armorAttr = this.getAttribute(Attributes.ARMOR);
        if (armorAttr != null) {
            armorAttr.removeModifier(foxesstuff$ARMOR_ID);
            if (armorStack.getItem() instanceof FoxArmorItem armor) {
                armorAttr.addTransientModifier(new AttributeModifier(
                        foxesstuff$ARMOR_ID, armor.getDefense(), AttributeModifier.Operation.ADD_VALUE));
            }
        }

        ItemStack weapon = this.foxesstuff$inventory.getItem(1);
        if (!ItemStack.matches(this.getItemBySlot(EquipmentSlot.MAINHAND), weapon)) {
            this.setItemSlot(EquipmentSlot.MAINHAND, weapon.copy());
        }
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (foxesstuff$ARMOR_STACK.equals(key) && this.level().isClientSide() && this.foxesstuff$inventory != null) {
            ItemStack synced = this.entityData.get(foxesstuff$ARMOR_STACK);
            if (!ItemStack.matches(synced, this.foxesstuff$inventory.getItem(0))) {
                this.foxesstuff$inventory.setItem(0, synced.copy());
            }
        }
    }

    @Override
    public void hurtArmor(@NotNull DamageSource source, float damage) {
        if (!this.foxesstuff$isFoxTame()) {
            super.hurtArmor(source, damage);
            return;
        }

        if (damage <= 0.0F) return;

        damage /= 4.0F;
        if (damage < 1.0F) damage = 1.0F;

        ItemStack armor = this.foxesstuff$inventory.getItem(0);
        if (armor.getItem() instanceof FoxArmorItem && (!source.is(DamageTypeTags.IS_FIRE) || !armor.has(DataComponents.DAMAGE_RESISTANT))) {
            armor.hurtAndBreak((int) damage, (LivingEntity) (Object) this, EquipmentSlot.BODY);
            this.foxesstuff$inventory.setChanged();
        }
    }

    @Override
    protected float getDamageAfterMagicAbsorb(@NotNull DamageSource source, float amount) {
        float result = super.getDamageAfterMagicAbsorb(source, amount);

        if (result <= 0.0F || !this.foxesstuff$isFoxTame()) return result;
        if (source.is(DamageTypeTags.BYPASSES_EFFECTS) || source.is(DamageTypeTags.BYPASSES_ENCHANTMENTS)) return result;
        if (!(this.level() instanceof ServerLevel serverLevel)) return result;

        ItemStack armor = this.foxesstuff$inventory.getItem(0);
        if (armor.isEmpty()) return result;

        MutableFloat protection = new MutableFloat(0.0F);
        FoxPlatform.get().modifyDamageProtection(armor, serverLevel, this, source, protection);

        if (protection.floatValue() > 0.0F) {
            float reduced = CombatRules.getDamageAfterMagicAbsorb(result, protection.floatValue());
            FoxPlatform.get().recordEnchantmentReduction(this, result - reduced);
            result = reduced;
        }
        return result;
    }

    @Override
    public boolean foxesstuff$isFoxTame() { return !this.entityData.get(foxesstuff$OWNER).isEmpty(); }

    @Override
    @Nullable
    public UUID foxesstuff$getFoxOwnerUUID() {
        String owner = this.entityData.get(foxesstuff$OWNER);
        return owner.isEmpty() ? null : UUID.fromString(owner);
    }

    @Override
    public void foxesstuff$playEatingSound() {
        this.playEatingSound();
    }

    @Override
    @Nullable
    public LivingEntity foxesstuff$getFoxOwner() {
        UUID id = this.foxesstuff$getFoxOwnerUUID();
        return id == null ? null : this.level().getPlayerByUUID(id);
    }

    @Override
    public void foxesstuff$setFoxOwner(@Nullable UUID uuid) {
        this.entityData.set(foxesstuff$OWNER, uuid == null ? "" : uuid.toString());

        AttributeInstance healthAttr = this.getAttribute(Attributes.MAX_HEALTH);
        if (healthAttr != null) {
            healthAttr.removeModifier(foxesstuff$HEALTH_ID);
            if (uuid != null) {
                healthAttr.addTransientModifier(new AttributeModifier(foxesstuff$HEALTH_ID, 20.0D, AttributeModifier.Operation.ADD_VALUE));
                setHealth(getMaxHealth());
            }
        }

        if (uuid != null) {
            this.setCanPickUpLoot(false);
            this.setPersistenceRequired();
            Player owner = this.level().getPlayerByUUID(uuid);
            if (owner != null) this.addTrustedEntity(owner);
        } else {
            this.setCanPickUpLoot(true);
        }
    }

    @Override
    public void foxesstuff$tameFox(Player player) {
        this.foxesstuff$setFoxOwner(player.getUUID());
        ItemStack held = this.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!held.isEmpty()) {
            this.spawnAtLocation((ServerLevel) this.level(), held);
            this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }

        this.clearStates();
        this.setTarget(null);
        this.foxesstuff$setFoxOrderedToSit(true);
    }

    @Override
    public void foxesstuff$setFoxDefending(boolean defending) {
        this.setDefending(defending);
        if (defending) this.wakeUp();
    }

    @Override
    public DyeColor foxesstuff$getCollarColor() {
        return DyeColor.byId(this.entityData.get(foxesstuff$COLLAR));
    }

    @Override
    public void foxesstuff$setCollarColor(DyeColor color) {
        this.entityData.set(foxesstuff$COLLAR, color.getId());
    }

    @Override
    public boolean foxesstuff$isFoxOrderedToSit() {
        return this.entityData.get(foxesstuff$SIT);
    }

    @Override
    public void foxesstuff$setFoxOrderedToSit(boolean sit) {
        this.entityData.set(foxesstuff$SIT, sit);
        ((Fox) (Object) this).setSitting(sit);
        if (sit) {
            this.getNavigation().stop();
            this.setTarget(null);
            this.setJumping(false);
            this.setDeltaMovement(0.0D, this.getDeltaMovement().y, 0.0D);
        }
    }

    @Override
    @Nullable
    public FoxVariant foxesstuff$getModdedFoxVariant() {
        return ModdedFoxVariants.getVariant(this.entityData.get(foxesstuff$VARIANT));
    }

    @Unique
    public String foxesstuff$getModdedVariant() {
        return this.entityData.get(foxesstuff$VARIANT);
    }

    @Override
    public void foxesstuff$setModdedVariant(String variant) {
        this.entityData.set(foxesstuff$VARIANT, variant);
    }

    @Override
    public SimpleContainer foxesstuff$getFoxInventory() {
        return this.foxesstuff$inventory;
    }

    @Override
    public boolean doHurtTarget(@NotNull ServerLevel level, @NotNull Entity target) {
        boolean hit = super.doHurtTarget(level, target);
        if (hit && !this.level().isClientSide() && this.foxesstuff$isFoxTame()) {
            ItemStack weapon = this.foxesstuff$inventory.getItem(1);
            if (!weapon.isEmpty() && weapon.isDamageableItem()) {
                weapon.hurtAndBreak(1, (LivingEntity) (Object) this, EquipmentSlot.MAINHAND);

                this.foxesstuff$inventory.setChanged();
            }
        }
        return hit;
    }

    @Override
    public @NotNull ItemStack getItemBySlot(@NotNull EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND && !this.level().isClientSide() && this.foxesstuff$isFoxTame()) {
            return this.foxesstuff$inventory.getItem(1);
        }

        return super.getItemBySlot(slot);
    }

    @Override
    public void setItemSlot(@NotNull EquipmentSlot slot, @NotNull ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND && !this.level().isClientSide() && this.foxesstuff$isFoxTame()) {
            super.setItemSlot(slot, stack);
            this.foxesstuff$inventory.setItem(1, stack);
            return;
        }

        super.setItemSlot(slot, stack);
    }
}
