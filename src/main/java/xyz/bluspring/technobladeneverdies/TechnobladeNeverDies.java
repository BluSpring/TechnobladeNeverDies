package xyz.bluspring.technobladeneverdies;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TechnobladeNeverDies implements ModInitializer {
    public static SoundEvent BRUH_SOUND_EVENT = SoundEvent.of(new Identifier("technoneverdies:technoneverdies.bruh"));
    public static SoundEvent PHIL_SOUND_EVENT = SoundEvent.of(new Identifier("technoneverdies:technoneverdies.phil_look_out"));

    public static boolean shouldReplaceSplashes = true;
    public static boolean shouldReplaceTitleIcon = true;
    public static boolean shouldReplaceTechnoPigSounds = true;

    @Override
    public void onInitialize() {
        var configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "technoblade_never_dies.json");

        if (configFile.exists()) {
            try {
                var data = Files.readString(configFile.toPath());
                var json = JsonParser.parseString(data).getAsJsonObject();

                if (json.has("shouldReplaceSplashes")) {
                    shouldReplaceSplashes = json.get("shouldReplaceSplashes").getAsBoolean();
                }

                if (json.has("shouldReplaceTitleIcon")) {
                    shouldReplaceTitleIcon = json.get("shouldReplaceTitleIcon").getAsBoolean();
                }

                if (json.has("shouldReplaceTechnoPigSounds")) {
                    shouldReplaceTechnoPigSounds = json.get("shouldReplaceTechnoPigSounds").getAsBoolean();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            var json = new JsonObject();
            json.addProperty("shouldReplaceSplashes", shouldReplaceSplashes);
            json.addProperty("shouldReplaceTitleIcon", shouldReplaceTitleIcon);
            json.addProperty("shouldReplaceTechnoPigSounds", shouldReplaceTechnoPigSounds);

            try {
                configFile.createNewFile();
                Files.writeString(configFile.toPath(), new GsonBuilder().setPrettyPrinting().create().toJson(json));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
