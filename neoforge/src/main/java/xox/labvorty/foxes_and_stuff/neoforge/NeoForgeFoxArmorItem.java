package xox.labvorty.foxes_and_stuff.neoforge;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.enchantment.Enchantment;
import xox.labvorty.foxes_and_stuff.items.armor.FoxArmorItem;

public class NeoForgeFoxArmorItem extends FoxArmorItem {
    public NeoForgeFoxArmorItem(ArmorMaterial material, Properties properties) {
        super(material, properties);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return enchantment.unwrapKey().map(this.getSupportedEnchantments()::contains).orElse(false);
    }

    @Override
    public boolean isPrimaryItemFor(ItemStack stack, Holder<Enchantment> enchantment) {
        return this.supportsEnchantment(stack, enchantment);
    }
}
