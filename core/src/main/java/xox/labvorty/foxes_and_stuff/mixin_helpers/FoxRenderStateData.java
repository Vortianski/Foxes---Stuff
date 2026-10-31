package xox.labvorty.foxes_and_stuff.mixin_helpers;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import xox.labvorty.foxes_and_stuff.data.holder.FoxVariant;

import java.util.UUID;

public interface FoxRenderStateData {
    boolean foxesstuff$isTame();
    DyeColor foxesstuff$getCollarColor();
    ItemStack foxesstuff$getArmor();
    @Nullable FoxVariant foxesstuff$getVariant();
    UUID foxesstuff$getUuid();
    void foxesstuff$set(UUID uuid, boolean tame, DyeColor collarColor, ItemStack armor, @Nullable FoxVariant variant);
}
