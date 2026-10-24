package xox.labvorty.foxes_and_stuff.init;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import xox.labvorty.foxes_and_stuff.FoxesStuffMod;

@EventBusSubscriber(modid = FoxesStuffMod.MOD_ID)
public class FoxesStuffResources {
    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        event.addPackFinders(
                ResourceLocation.fromNamespaceAndPath(FoxesStuffMod.MOD_ID, "resourcepacks/better_vanilla_foxes"),
                PackType.CLIENT_RESOURCES,
                Component.literal("F&S|Better Vanilla Foxes"),
                PackSource.BUILT_IN,
                false,
                Pack.Position.TOP
        );
    }
}
