package xox.labvorty.foxes_and_stuff.fabric;

import xox.labvorty.foxes_and_stuff.FoxPlatform;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import xox.labvorty.foxes_and_stuff.items.armor.FoxArmorItem;

public class FabricFoxPlatform extends FoxPlatform {
    @Override
    public FoxArmorItem createFoxArmor(ArmorMaterial material, Item.Properties properties) {
        return new FoxArmorItem(material, properties);
    }
}
