package xox.labvorty.foxes_and_stuff.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.component.DyedItemColor;
import xox.labvorty.foxes_and_stuff.FoxesStuffMod;
import xox.labvorty.foxes_and_stuff.FoxPlatform;
import xox.labvorty.foxes_and_stuff.items.GoldenBerriesItem;
import xox.labvorty.foxes_and_stuff.items.armor.FoxArmorItem;

public class FoxesStuffItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(FoxesStuffMod.MOD_ID, Registries.ITEM);
    public static final RegistrySupplier<Item> GOLDEN_BERRIES = ITEMS.register("golden_berries", () -> new GoldenBerriesItem(properties("golden_berries")));

    public static final RegistrySupplier<FoxArmorItem> LEATHER_FOX_ARMOR = ITEMS.register(
            "leather_fox_armor",
            () -> FoxPlatform.get().createFoxArmor(ArmorMaterials.LEATHER, properties("leather_fox_armor")
                    .durability(ArmorType.BODY.getDurability(5))
                    .enchantable(ArmorMaterials.LEATHER.enchantmentValue())
                    .component(DataComponents.DYED_COLOR, new DyedItemColor(DyedItemColor.LEATHER_COLOR)))
    );
    public static final RegistrySupplier<FoxArmorItem> IRON_FOX_ARMOR = ITEMS.register(
            "iron_fox_armor",
            () -> FoxPlatform.get().createFoxArmor(ArmorMaterials.IRON, properties("iron_fox_armor")
                    .durability(ArmorType.BODY.getDurability(15))
                    .enchantable(ArmorMaterials.IRON.enchantmentValue()))
    );
    public static final RegistrySupplier<FoxArmorItem> GOLD_FOX_ARMOR = ITEMS.register(
            "gold_fox_armor",
            () -> FoxPlatform.get().createFoxArmor(ArmorMaterials.GOLD, properties("gold_fox_armor")
                    .durability(ArmorType.BODY.getDurability(7))
                    .enchantable(ArmorMaterials.GOLD.enchantmentValue()))
    );
    public static final RegistrySupplier<FoxArmorItem> DIAMOND_FOX_ARMOR = ITEMS.register(
            "diamond_fox_armor",
            () -> FoxPlatform.get().createFoxArmor(ArmorMaterials.DIAMOND, properties("diamond_fox_armor")
                    .durability(ArmorType.BODY.getDurability(33))
                    .enchantable(ArmorMaterials.DIAMOND.enchantmentValue()))
    );
    public static final RegistrySupplier<FoxArmorItem> NETHERITE_FOX_ARMOR = ITEMS.register(
            "netherite_fox_armor",
            () -> FoxPlatform.get().createFoxArmor(ArmorMaterials.NETHERITE, properties("netherite_fox_armor")
                    .durability(ArmorType.BODY.getDurability(37))
                    .enchantable(ArmorMaterials.NETHERITE.enchantmentValue())
                    .fireResistant())
    );

    private static Item.Properties properties(String name) {
        return new Item.Properties().setId(ResourceKey.create(Registries.ITEM, FoxesStuffMod.location(name)));
    }
}
