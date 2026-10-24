package xox.labvorty.foxes_and_stuff.items.armor;

import net.minecraft.core.Holder;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.jetbrains.annotations.NotNull;
import xox.labvorty.foxes_and_stuff.FoxesStuffMod;

import java.util.List;

public class FoxArmorItem extends ArmorItem {
    private final ResourceLocation textureLocation;
    private final SoundEvent breakingSound;

    public FoxArmorItem(Holder<ArmorMaterial> material, Properties properties) {
        this(material, SoundEvents.ITEM_BREAK, properties);
    }

    public FoxArmorItem(Holder<ArmorMaterial> material, SoundEvent breakingSound, Properties properties) {
        super(material, Type.BODY, properties);
        this.breakingSound = breakingSound;

        String materialName = material.unwrapKey().orElseThrow().location().getPath();
        ResourceLocation base = FoxesStuffMod.location("textures/entity/fox/armor/" + materialName);
        this.textureLocation = base.withSuffix(".png");

        DispenserBlock.registerBehavior(this, new DefaultDispenseItemBehavior());
    }

    public ResourceLocation getTexture() {
        return this.textureLocation;
    }

    public @NotNull SoundEvent getBreakingSound() {
        return this.breakingSound;
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return true;
    }

    protected List<ResourceKey<Enchantment>> getSupportedEnchantments() {
        return List.of(
                Enchantments.UNBREAKING,
                Enchantments.PROTECTION,
                Enchantments.PROJECTILE_PROTECTION,
                Enchantments.BLAST_PROTECTION,
                Enchantments.FIRE_PROTECTION,
                Enchantments.VANISHING_CURSE
        );
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }
}
