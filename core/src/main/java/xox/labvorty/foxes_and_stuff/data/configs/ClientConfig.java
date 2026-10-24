package xox.labvorty.foxes_and_stuff.data.configs;

import java.util.function.BooleanSupplier;

public final class ClientConfig {
    private static BooleanSupplier renderFluff = () -> true;

    public static boolean renderFluff() {
        return renderFluff.getAsBoolean();
    }

    public static void bind(BooleanSupplier supplier) {
        renderFluff = supplier;
    }
}
