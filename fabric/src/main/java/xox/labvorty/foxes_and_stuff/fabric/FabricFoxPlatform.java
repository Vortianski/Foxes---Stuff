package xox.labvorty.foxes_and_stuff.fabric;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import xox.labvorty.foxes_and_stuff.FoxPlatform;
import xox.labvorty.foxes_and_stuff.items.armor.FoxArmorItem;

public class FabricFoxPlatform extends FoxPlatform {
    @Override
    public FoxArmorItem createFoxArmor(Holder<ArmorMaterial> material, Item.Properties properties) {
        return new FoxArmorItem(material, properties);
    }
}
