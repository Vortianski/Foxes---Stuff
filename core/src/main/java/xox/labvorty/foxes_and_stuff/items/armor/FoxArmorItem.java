package xox.labvorty.foxes_and_stuff.items.armor;

import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.DispenserBlock;
import xox.labvorty.foxes_and_stuff.FoxesStuffMod;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FoxArmorItem extends Item {
    private static final List<ResourceKey<Enchantment>> SUPPORTED_ENCHANTMENTS = List.of(
            Enchantments.UNBREAKING,
            Enchantments.PROTECTION,
            Enchantments.PROJECTILE_PROTECTION,
            Enchantments.BLAST_PROTECTION,
            Enchantments.FIRE_PROTECTION,
            Enchantments.VANISHING_CURSE
    );
    private final Identifier textureLocation;
    private final Identifier babyTextureLocation;
    private final SoundEvent breakingSound;
    private final int defense;

    public FoxArmorItem(ArmorMaterial material, Properties properties) {
        this(material, SoundEvents.ITEM_BREAK.value(), properties);
    }

    public FoxArmorItem(ArmorMaterial material, SoundEvent breakingSound, Properties properties) {
        super(properties);
        this.breakingSound = breakingSound;
        this.defense = material.defense().getOrDefault(ArmorType.BODY, 0);

        String materialName = material.assetId().identifier().getPath();
        Identifier base = FoxesStuffMod.location("textures/entity/fox/armor/" + materialName);
        this.textureLocation = base.withSuffix(".png");
        this.babyTextureLocation = base.withSuffix("_baby.png");

        DispenserBlock.registerBehavior(this, new DefaultDispenseItemBehavior());
    }

    public Identifier getTexture() {
        return this.textureLocation;
    }

    public Identifier getTexture(boolean isBaby) {
        return isBaby ? this.babyTextureLocation : this.textureLocation;
    }

    public @NotNull SoundEvent getBreakingSound() {
        return this.breakingSound;
    }

    public int getDefense() {
        return this.defense;
    }

    public List<ResourceKey<Enchantment>> getSupportedEnchantments() {
        return SUPPORTED_ENCHANTMENTS;
    }
}
