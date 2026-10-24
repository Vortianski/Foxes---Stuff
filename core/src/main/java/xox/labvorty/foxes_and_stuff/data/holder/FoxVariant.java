package xox.labvorty.foxes_and_stuff.data.holder;

import net.minecraft.resources.ResourceLocation;

public record FoxVariant(
        ResourceLocation defaultTexture,
        ResourceLocation sleepingTexture,
        ResourceLocation fluffTexture
) {}
