package xox.labvorty.foxes_and_stuff.mixin_helpers;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import xox.labvorty.foxes_and_stuff.data.holder.FoxVariant;
import xox.labvorty.foxes_and_stuff.items.armor.FoxArmorItem;

import java.util.UUID;

public interface TameableFox {
    boolean foxesstuff$isFoxTame();
    void foxesstuff$playEatingSound();
    @Nullable
    UUID foxesstuff$getFoxOwnerUUID();
    @Nullable
    LivingEntity foxesstuff$getFoxOwner();
    void foxesstuff$setFoxOwner(@Nullable UUID uuid);
    void foxesstuff$tameFox(Player player);
    default boolean foxesstuff$isFoxOwnedBy(LivingEntity entity) {
        return entity.getUUID().equals(foxesstuff$getFoxOwnerUUID());
    }
    DyeColor foxesstuff$getCollarColor();
    void foxesstuff$setCollarColor(DyeColor color);
    void foxesstuff$dropFoxInventory();
    boolean foxesstuff$isFoxOrderedToSit();
    void foxesstuff$setFoxOrderedToSit(boolean sit);
    SimpleContainer foxesstuff$getFoxInventory();
    static boolean foxesstuff$isValidArmor(ItemStack stack) {
        return stack.getItem() instanceof FoxArmorItem;
    }
    void foxesstuff$setFoxDefending(boolean defending);
    @Nullable
    FoxVariant foxesstuff$getModdedFoxVariant();
    void foxesstuff$setModdedVariant(String variant);
}
