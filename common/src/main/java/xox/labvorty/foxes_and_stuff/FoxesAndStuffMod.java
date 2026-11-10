package xox.labvorty.foxes_and_stuff;

import net.minecraft.resources.Identifier;
import xox.labvorty.foxes_and_stuff.data.events.FoxEvents;
import xox.labvorty.foxes_and_stuff.init.FoxesStuffInitialization;
import xox.labvorty.foxes_and_stuff.init.FoxesStuffItems;
import xox.labvorty.foxes_and_stuff.init.FoxesStuffMenus;

public final class FoxesAndStuffMod {
    public static final String MOD_ID = "foxesstuff";

    public static void init(FoxPlatform platform) {
        FoxPlatform.set(platform);
        FoxesStuffMenus.MENUS.register();
        FoxesStuffItems.ITEMS.register();
        FoxesStuffInitialization.init();
        FoxEvents.init();
    }

    public static Identifier location(String string) {
        return Identifier.fromNamespaceAndPath(MOD_ID, string);
    }
}
