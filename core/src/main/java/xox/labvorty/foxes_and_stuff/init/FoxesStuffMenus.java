package xox.labvorty.foxes_and_stuff.init;

import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import xox.labvorty.foxes_and_stuff.FoxesStuffMod;
import xox.labvorty.foxes_and_stuff.inventory.FoxInventoryMenu;

public class FoxesStuffMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(FoxesStuffMod.MOD_ID, Registries.MENU);
    public static final RegistrySupplier<MenuType<FoxInventoryMenu>> FOX_INVENTORY = MENUS.register(
            "fox_inventory",
            () -> MenuRegistry.ofExtended(FoxInventoryMenu::createClientMenu)
    );
}
