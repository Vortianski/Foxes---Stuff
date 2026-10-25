package xox.labvorty.foxes_and_stuff.fabric;

import net.fabricmc.api.ModInitializer;
import xox.labvorty.foxes_and_stuff.FoxesStuffMod;

public class FoxesStuffFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FoxesStuffMod.init(new FabricFoxPlatform());
    }
}
