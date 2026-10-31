package xox.labvorty.foxes_and_stuff.init;

import net.minecraft.resources.Identifier;
import xox.labvorty.foxes_and_stuff.FoxesStuffMod;
import xox.labvorty.foxes_and_stuff.data.holder.ModdedFoxVariants;

public class FoxesStuffInitialization {
    public static void init() {
        registerVariant("amber");
        registerVariant("gnarpy");
        registerVariant("expie");
        registerVariant("silver");
        registerVariant("marble");
        registerVariant("very_red");
        registerVariant("fennec");
    }

    private static void registerVariant(String type) {
        Identifier base = FoxesStuffMod.location("textures/entity/variants/" + type);
        ModdedFoxVariants.registerVariant(
                type,
                base.withSuffix("/fox.png"),
                base.withSuffix("/fox_sleep.png"),
                base.withSuffix("/fluff.png")
        );
    }
}
