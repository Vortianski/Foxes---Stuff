package xox.labvorty.foxes_and_stuff.mixins.client;

import net.minecraft.client.renderer.entity.state.FoxRenderState;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import xox.labvorty.foxes_and_stuff.data.holder.FoxVariant;
import xox.labvorty.foxes_and_stuff.mixin_helpers.FoxRenderStateData;

import java.util.UUID;

@Mixin(FoxRenderState.class)
public class FoxRenderStateMixin implements FoxRenderStateData {
    @Unique private boolean foxesstuff$tame;
    @Unique private DyeColor foxesstuff$collarColor = DyeColor.RED;
    @Unique private ItemStack foxesstuff$armor = ItemStack.EMPTY;
    @Unique private FoxVariant foxesstuff$variant;
    @Unique private UUID foxesstuff$uuid;

    @Override
    public boolean foxesstuff$isTame() {
        return this.foxesstuff$tame;
    }

    @Override
    public DyeColor foxesstuff$getCollarColor() {
        return this.foxesstuff$collarColor;
    }

    @Override
    public ItemStack foxesstuff$getArmor() {
        return this.foxesstuff$armor;
    }

    @Override
    @Nullable
    public FoxVariant foxesstuff$getVariant() {
        return this.foxesstuff$variant;
    }

    @Override
    public UUID foxesstuff$getUuid() {
        return this.foxesstuff$uuid;
    }

    public void foxesstuff$set(UUID uuid, boolean tame, DyeColor collarColor, ItemStack armor, @Nullable FoxVariant variant) {
        this.foxesstuff$uuid = uuid;
        this.foxesstuff$tame = tame;
        this.foxesstuff$collarColor = collarColor;
        this.foxesstuff$armor = armor.copy();
        this.foxesstuff$variant = variant;
    }
}
