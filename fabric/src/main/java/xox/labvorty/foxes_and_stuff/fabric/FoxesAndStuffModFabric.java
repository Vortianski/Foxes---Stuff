package xox.labvorty.foxes_and_stuff.fabric;

import net.fabricmc.api.ModInitializer;

import xox.labvorty.foxes_and_stuff.FoxesAndStuffMod;

public final class FoxesAndStuffModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FoxesAndStuffMod.init(new FabricFoxPlatform());
    }
}
