package xox.labvorty.foxes_and_stuff;

import dev.architectury.registry.menu.MenuRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.apache.commons.lang3.mutable.MutableFloat;
import xox.labvorty.foxes_and_stuff.inventory.FoxInventoryMenu;
import xox.labvorty.foxes_and_stuff.items.armor.FoxArmorItem;

public abstract class FoxPlatform {
    private static FoxPlatform instance;

    public static void set(FoxPlatform platform) {
        instance = platform;
    }

    public static FoxPlatform get() {
        if (instance == null) throw new IllegalStateException("Fox platform is not initialized");
        return instance;
    }

    public abstract FoxArmorItem createFoxArmor(Holder<ArmorMaterial> material, Item.Properties properties);

    public void openFoxInventory(ServerPlayer player, Fox fox) {
        MenuRegistry.openExtendedMenu(player, new FoxInventoryMenu.Provider(fox), buf -> buf.writeInt(fox.getId()));
    }

    public boolean usesArchitecturyEntityInteraction() {
        return true;
    }

    public void modifyDamageProtection(
            ItemStack armor,
            ServerLevel level,
            LivingEntity entity,
            DamageSource source,
            MutableFloat protection
    ) {
        ItemEnchantments enchantments = armor.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        for (Holder<Enchantment> enchantment : enchantments.keySet()) {
            enchantment.value().modifyDamageProtection(
                    level,
                    enchantments.getLevel(enchantment),
                    armor,
                    entity,
                    source,
                    protection
            );
        }
    }

    public void recordEnchantmentReduction(LivingEntity entity, float amount) {
    }
}
