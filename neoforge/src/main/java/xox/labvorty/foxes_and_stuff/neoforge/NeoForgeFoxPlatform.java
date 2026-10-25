package xox.labvorty.foxes_and_stuff.neoforge;

import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.apache.commons.lang3.mutable.MutableFloat;
import xox.labvorty.foxes_and_stuff.FoxPlatform;
import xox.labvorty.foxes_and_stuff.items.armor.FoxArmorItem;
import xox.labvorty.foxes_and_stuff.mixins.LivingEntityAccessor;
import xox.labvorty.foxes_and_stuff.inventory.FoxInventoryMenu;

import java.util.Stack;

public class NeoForgeFoxPlatform extends FoxPlatform {
    @Override
    public FoxArmorItem createFoxArmor(Holder<ArmorMaterial> material, Item.Properties properties) {
        return new NeoForgeFoxArmorItem(material, properties);
    }

    @Override
    public void openFoxInventory(ServerPlayer player, Fox fox) {
        player.openMenu(new FoxInventoryMenu.Provider(fox), buf -> buf.writeInt(fox.getId()));
    }

    @Override
    public boolean usesArchitecturyEntityInteraction() {
        return false;
    }

    @Override
    public void modifyDamageProtection(
            ItemStack armor,
            ServerLevel level,
            LivingEntity entity,
            DamageSource source,
            MutableFloat protection
    ) {
        EnchantmentHelper.runIterationOnItem(armor, (holder, enchantmentLevel) ->
                holder.value().modifyDamageProtection(level, enchantmentLevel, armor, entity, source, protection));
    }

    @Override
    public void recordEnchantmentReduction(LivingEntity entity, float amount) {
        Stack<DamageContainer> damageContainers = ((LivingEntityAccessor) entity).foxesstuff$getDamageContainers();
        if (damageContainers == null || damageContainers.isEmpty()) return;

        DamageContainer container = damageContainers.peek();
        container.setReduction(
                DamageContainer.Reduction.ENCHANTMENTS,
                container.getReduction(DamageContainer.Reduction.ENCHANTMENTS) + amount
        );
    }
}
