package xox.labvorty.foxes_and_stuff.fabric;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import xox.labvorty.foxes_and_stuff.FoxesAndStuffMod;

public class FoxesStuffResources {
    public static void register() {
        ResourceManagerHelper.registerBuiltinResourcePack(
                FoxesAndStuffMod.location("better_vanilla_foxes"),
                FabricLoader.getInstance().getModContainer(FoxesAndStuffMod.MOD_ID).orElseThrow(),
                Component.literal("F&S|Better Vanilla Foxes"),
                ResourcePackActivationType.DEFAULT_ENABLED
        );
    }
}
