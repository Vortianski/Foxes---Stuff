package xox.labvorty.foxes_and_stuff.init;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import xox.labvorty.foxes_and_stuff.FoxesStuffMod;

public class FoxesStuffResources {
    public static void register() {
        ResourceManagerHelper.registerBuiltinResourcePack(
                FoxesStuffMod.location("better_vanilla_foxes"),
                FabricLoader.getInstance().getModContainer(FoxesStuffMod.MOD_ID).orElseThrow(),
                Component.literal("F&S|Better Vanilla Foxes"),
                ResourcePackActivationType.DEFAULT_ENABLED
        );
    }
}
