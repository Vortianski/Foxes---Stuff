package xox.labvorty.foxes_and_stuff.neoforge;

import net.neoforged.neoforge.common.ModConfigSpec;
import xox.labvorty.foxes_and_stuff.data.configs.ClientConfig;

public class NeoForgeClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue RENDER_FLUFF = BUILDER
            .comment("Should foxes have additional fluff texture")
            .define("renderFluff", true);

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static void bind() {
        ClientConfig.bind(() -> RENDER_FLUFF.get());
    }
}
