package xox.labvorty.foxes_and_stuff.neoforge;

import net.neoforged.fml.common.Mod;

import xox.labvorty.foxes_and_stuff.FoxesAndStuffMod;

@Mod(FoxesAndStuffMod.MOD_ID)
public final class FoxesAndStuffModNeoForge {
    public FoxesAndStuffModNeoForge() {
        FoxesAndStuffMod.init(new NeoForgeFoxPlatform());
    }
}
