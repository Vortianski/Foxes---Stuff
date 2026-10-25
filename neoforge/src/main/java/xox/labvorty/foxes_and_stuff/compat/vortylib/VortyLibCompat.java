package xox.labvorty.foxes_and_stuff.compat.vortylib;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xox.labvorty.foxes_and_stuff.FoxesStuffMod;
import xox.labvorty.foxes_and_stuff.neoforge.NeoForgeClientConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

public class VortyLibCompat {
    public static void init() {
        try {
            Class<?> registryClass = load("xox.labvorty.vortylib.data.config.ModRegistry");
            Class<?> entryClass = load("xox.labvorty.vortylib.data.config.ModEntry");
            Class<?> configHolderClass = load("xox.labvorty.vortylib.data.config.ConfigHolder");
            Class<?> socialTypeClass = load("xox.labvorty.vortylib.data.config.SocialType");

            Object entry = invokeStatic(entryClass, "builder", "foxesstuff", Component.literal("Foxes & Stuff"));
            entry = invoke(entry, "featuredItemsChosen", List.of(ResourceLocation.parse("minecraft:sweet_berries")));

            Object config = invokeStatic(configHolderClass, "builder", NeoForgeClientConfig.SPEC);
            config = invoke(
                    config,
                    "addBoolean",
                    Component.literal("Render Fluff"),
                    Component.literal("Should foxes have additional fluff texture"),
                    NeoForgeClientConfig.RENDER_FLUFF,
                    true
            );
            config = invoke(config, "build");
            entry = invoke(entry, "clientConfig", config);

            Object discord = invokeStatic(socialTypeClass, "discord", "https://discord.gg/ZesGqhGnAN");
            Object kofi = invokeStatic(socialTypeClass, "kofi", "https://ko-fi.com/vortianski");
            entry = invoke(entry, "addSocial", discord);
            entry = invoke(entry, "addSocial", kofi);
            entry = invoke(entry, "build");

            invokeStatic(registryClass, "register", entry);
        } catch (ReflectiveOperationException exception) {
            Throwable cause = exception instanceof InvocationTargetException invocationException
                    ? invocationException.getCause()
                    : exception;
            FoxesStuffMod.LOGGER.warn("Failed to register Foxes & Stuff in VortyLib", cause);
        }
    }

    private static Class<?> load(String className) throws ClassNotFoundException {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException exception) {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            if (contextClassLoader == null) throw exception;
            return Class.forName(className, true, contextClassLoader);
        }
    }

    private static Object invokeStatic(Class<?> type, String name, Object... arguments) throws ReflectiveOperationException {
        return invokeMethod(type, null, name, arguments, true);
    }

    private static Object invoke(Object target, String name, Object... arguments) throws ReflectiveOperationException {
        return invokeMethod(target.getClass(), target, name, arguments, false);
    }

    private static Object invokeMethod(
            Class<?> type,
            Object target,
            String name,
            Object[] arguments,
            boolean requireStatic
    ) throws ReflectiveOperationException {
        for (Method method : type.getMethods()) {
            if (!method.getName().equals(name) || Modifier.isStatic(method.getModifiers()) != requireStatic) continue;
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != arguments.length) continue;

            boolean matches = true;
            for (int index = 0; index < parameterTypes.length; index++) {
                if (!accepts(parameterTypes[index], arguments[index])) {
                    matches = false;
                    break;
                }
            }
            if (matches) return method.invoke(target, arguments);
        }

        throw new NoSuchMethodException(type.getName() + "." + name);
    }

    private static boolean accepts(Class<?> parameterType, Object argument) {
        if (argument == null) return !parameterType.isPrimitive();
        if (!parameterType.isPrimitive()) return parameterType.isInstance(argument);
        return parameterType == boolean.class && argument instanceof Boolean
                || parameterType == int.class && argument instanceof Integer
                || parameterType == long.class && argument instanceof Long
                || parameterType == float.class && argument instanceof Float
                || parameterType == double.class && argument instanceof Double
                || parameterType == short.class && argument instanceof Short
                || parameterType == byte.class && argument instanceof Byte
                || parameterType == char.class && argument instanceof Character;
    }
}
