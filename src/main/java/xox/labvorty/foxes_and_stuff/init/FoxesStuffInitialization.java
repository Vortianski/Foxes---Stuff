package xox.labvorty.foxes_and_stuff.init;

import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import xox.labvorty.foxes_and_stuff.compat.vortylib.VortyLibCompat;
import xox.labvorty.foxes_and_stuff.data.holder.ModdedFoxVariants;

@EventBusSubscriber
public class FoxesStuffInitialization {
    @SubscribeEvent
    public static void client(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            if (ModList.get().isLoaded("vortylib")) {
                VortyLibCompat.init();
            }

            ModdedFoxVariants.registerVariant(
                    "amber",
                    ResourceLocation.fromNamespaceAndPath("foxesstuff", "textures/entity/variants/amber/fox.png"),
                    ResourceLocation.fromNamespaceAndPath("foxesstuff", "textures/entity/variants/amber/fox_sleep.png"),
                    ResourceLocation.fromNamespaceAndPath("foxesstuff", "textures/entity/variants/amber/fluff.png")
            );
            ModdedFoxVariants.registerVariant(
                    "gnarpy",
                    ResourceLocation.fromNamespaceAndPath("foxesstuff", "textures/entity/variants/gnarpy/fox.png"),
                    ResourceLocation.fromNamespaceAndPath("foxesstuff", "textures/entity/variants/gnarpy/fox_sleep.png"),
                    ResourceLocation.fromNamespaceAndPath("foxesstuff", "textures/entity/variants/gnarpy/fluff.png")
            );
            ModdedFoxVariants.registerVariant(
                    "expie",
                    ResourceLocation.fromNamespaceAndPath("foxesstuff", "textures/entity/variants/expie/fox.png"),
                    ResourceLocation.fromNamespaceAndPath("foxesstuff", "textures/entity/variants/expie/fox_sleep.png"),
                    ResourceLocation.fromNamespaceAndPath("foxesstuff", "textures/entity/variants/expie/fluff.png")
            );
            ModdedFoxVariants.registerVariant(
                    "silver",
                    ResourceLocation.fromNamespaceAndPath("foxesstuff", "textures/entity/variants/silver/fox.png"),
                    ResourceLocation.fromNamespaceAndPath("foxesstuff", "textures/entity/variants/silver/fox_sleep.png"),
                    ResourceLocation.fromNamespaceAndPath("foxesstuff", "textures/entity/variants/silver/fluff.png")
            );
            ModdedFoxVariants.registerVariant(
                    "marble",
                    ResourceLocation.fromNamespaceAndPath("foxesstuff", "textures/entity/variants/marble/fox.png"),
                    ResourceLocation.fromNamespaceAndPath("foxesstuff", "textures/entity/variants/marble/fox_sleep.png"),
                    ResourceLocation.fromNamespaceAndPath("foxesstuff", "textures/entity/variants/marble/fluff.png")
            );
            ModdedFoxVariants.registerVariant(
                    "very_red",
                    ResourceLocation.fromNamespaceAndPath("foxesstuff", "textures/entity/variants/very_red/fox.png"),
                    ResourceLocation.fromNamespaceAndPath("foxesstuff", "textures/entity/variants/very_red/fox_sleep.png"),
                    ResourceLocation.fromNamespaceAndPath("foxesstuff", "textures/entity/variants/very_red/fluff.png")
            );
            ModdedFoxVariants.registerVariant(
                    "fennec",
                    ResourceLocation.fromNamespaceAndPath("foxesstuff", "textures/entity/variants/fennec/fox.png"),
                    ResourceLocation.fromNamespaceAndPath("foxesstuff", "textures/entity/variants/fennec/fox_sleep.png"),
                    ResourceLocation.fromNamespaceAndPath("foxesstuff", "textures/entity/variants/fennec/fluff.png")
            );
        });
    }

    @SubscribeEvent
    public static void common(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {

        });
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(
                (itemStack, index) -> {
                    return 0xFF000000
                            | itemStack.getOrDefault(
                            DataComponents.DYED_COLOR,
                            new DyedItemColor(DyedItemColor.LEATHER_COLOR, false)
                    ).rgb();
                },
                FoxesStuffItems.LEATHER_FOX_ARMOR.get()
        );
    }
}
