package xox.labvorty.foxes_and_stuff;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import xox.labvorty.foxes_and_stuff.data.events.FoxEvents;
import xox.labvorty.foxes_and_stuff.init.FoxesStuffInitialization;
import xox.labvorty.foxes_and_stuff.init.FoxesStuffItems;
import xox.labvorty.foxes_and_stuff.init.FoxesStuffMenus;

public class FoxesStuffMod {
    public static final String MOD_ID = "foxesstuff";
    public static final Logger LOGGER = LogUtils.getLogger();

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
