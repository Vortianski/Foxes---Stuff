package xox.labvorty.foxes_and_stuff.fabric;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import xox.labvorty.foxes_and_stuff.FoxesStuffMod;
import xox.labvorty.foxes_and_stuff.data.configs.ClientConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FabricClientConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("foxesstuff.json");
    private static boolean renderFluff = true;

    public static void load() {
        if (Files.exists(PATH)) {
            try {
                JsonObject json = JsonParser.parseString(Files.readString(PATH)).getAsJsonObject();
                if (json.has("renderFluff")) {
                    renderFluff = json.get("renderFluff").getAsBoolean();
                }
            } catch (IOException | JsonParseException exception) {
                FoxesStuffMod.LOGGER.warn("Failed to read client config", exception);
            }
        } else {
            save();
        }

        ClientConfig.bind(() -> renderFluff);
    }

    private static void save() {
        JsonObject json = new JsonObject();
        json.addProperty("renderFluff", renderFluff);

        try {
            Files.createDirectories(PATH.getParent());
            Files.writeString(PATH, GSON.toJson(json));
        } catch (IOException exception) {
            FoxesStuffMod.LOGGER.warn("Failed to write client config", exception);
        }
    }
}
