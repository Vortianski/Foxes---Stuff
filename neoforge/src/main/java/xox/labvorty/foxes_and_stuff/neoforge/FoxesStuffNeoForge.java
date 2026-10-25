package xox.labvorty.foxes_and_stuff.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import xox.labvorty.foxes_and_stuff.FoxesStuffMod;

@Mod(FoxesStuffMod.MOD_ID)
public class FoxesStuffNeoForge {
    public FoxesStuffNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        FoxesStuffMod.init(new NeoForgeFoxPlatform());
        modContainer.registerConfig(ModConfig.Type.CLIENT, NeoForgeClientConfig.SPEC);
    }
}
