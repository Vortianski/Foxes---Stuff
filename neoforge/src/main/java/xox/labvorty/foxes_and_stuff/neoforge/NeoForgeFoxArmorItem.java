package xox.labvorty.foxes_and_stuff.neoforge;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.jetbrains.annotations.NotNull;
import xox.labvorty.foxes_and_stuff.items.armor.FoxArmorItem;

import java.util.Objects;

public class NeoForgeFoxArmorItem extends FoxArmorItem {
    public NeoForgeFoxArmorItem(Holder<ArmorMaterial> material, Properties properties) {
        super(material, properties);
    }

    @Override
    public boolean supportsEnchantment(@NotNull ItemStack itemStack, Holder<Enchantment> enchantment) {
        return this.getSupportedEnchantments().contains(enchantment.getKey());
    }

    @Override
    public boolean isBookEnchantable(@NotNull ItemStack itemStack, @NotNull ItemStack bookStack) {
        return EnchantmentHelper.getEnchantmentsForCrafting(bookStack).keySet().stream()
                .map(Holder::getKey)
                .filter(Objects::nonNull)
                .anyMatch(this.getSupportedEnchantments()::contains);
    }
}
