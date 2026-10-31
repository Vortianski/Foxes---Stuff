package xox.labvorty.foxes_and_stuff.data.holder;

import net.minecraft.resources.Identifier;

public record FoxVariant(
        Identifier defaultTexture,
        Identifier sleepingTexture,
        Identifier fluffTexture
) {}
