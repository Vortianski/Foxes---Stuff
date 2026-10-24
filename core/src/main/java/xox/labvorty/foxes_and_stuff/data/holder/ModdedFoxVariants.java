package xox.labvorty.foxes_and_stuff.data.holder;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModdedFoxVariants {
    private static final Map<String, FoxVariant> VARIANTS = new HashMap<>();

    public static void registerVariant(String type, ResourceLocation defaultTexture, ResourceLocation sleepingTexture, ResourceLocation fluffTexture) {
        registerVariant(type, new FoxVariant(defaultTexture, sleepingTexture, fluffTexture));
    }

    public static void registerVariant(String type, FoxVariant foxVariant) {
        VARIANTS.put(type, foxVariant);
    }

    @Nullable
    public static String getRandomVariant(RandomSource randomSource) {
        List<String> types = VARIANTS.keySet().stream().toList();

        if (types.isEmpty()) return null;

        return types.get(randomSource.nextIntBetweenInclusive(0, types.size() - 1));
    }

    @Nullable
    public static FoxVariant getVariant(String type) {
        return VARIANTS.get(type);
    }
}
