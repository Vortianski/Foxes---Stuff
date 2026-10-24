package xox.labvorty.foxes_and_stuff.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.DyedItemColor;
import xox.labvorty.foxes_and_stuff.FoxPlatform;
import xox.labvorty.foxes_and_stuff.FoxesStuffMod;
import xox.labvorty.foxes_and_stuff.items.GoldenBerriesItem;
import xox.labvorty.foxes_and_stuff.items.armor.FoxArmorItem;

public class FoxesStuffItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(FoxesStuffMod.MOD_ID, Registries.ITEM);
    public static final RegistrySupplier<Item> GOLDEN_BERRIES = ITEMS.register("golden_berries", GoldenBerriesItem::new);

    public static final RegistrySupplier<FoxArmorItem> LEATHER_FOX_ARMOR = ITEMS.register(
            "leather_fox_armor",
            () -> FoxPlatform.get().createFoxArmor(
                    ArmorMaterials.LEATHER,
                    new Item.Properties().durability(
                            ArmorItem.Type.BODY.getDurability(5)
                    ).component(
                            DataComponents.DYED_COLOR,
                            new DyedItemColor(DyedItemColor.LEATHER_COLOR, false)
                    )
            )
    );
    public static final RegistrySupplier<FoxArmorItem> IRON_FOX_ARMOR = ITEMS.register(
            "iron_fox_armor",
            () -> FoxPlatform.get().createFoxArmor(
                    ArmorMaterials.IRON,
                    new Item.Properties().durability(
                            ArmorItem.Type.BODY.getDurability(15)
                    )
            )
    );
    public static final RegistrySupplier<FoxArmorItem> GOLD_FOX_ARMOR = ITEMS.register(
            "gold_fox_armor",
            () -> FoxPlatform.get().createFoxArmor(
                    ArmorMaterials.GOLD,
                    new Item.Properties().durability(
                            ArmorItem.Type.BODY.getDurability(7)
                    )
            )
    );
    public static final RegistrySupplier<FoxArmorItem> DIAMOND_FOX_ARMOR = ITEMS.register(
            "diamond_fox_armor",
            () -> FoxPlatform.get().createFoxArmor(
                    ArmorMaterials.DIAMOND,
                    new Item.Properties().durability(
                            ArmorItem.Type.BODY.getDurability(33)
                    )
            )
    );
    public static final RegistrySupplier<FoxArmorItem> NETHERITE_FOX_ARMOR = ITEMS.register(
            "netherite_fox_armor",
            () -> FoxPlatform.get().createFoxArmor(
                    ArmorMaterials.NETHERITE,
                    new Item.Properties().durability(
                            ArmorItem.Type.BODY.getDurability(37)
                    ).fireResistant()
            )
    );
}
