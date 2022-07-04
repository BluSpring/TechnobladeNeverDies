package xyz.bluspring.technobladeneverdies;

import net.fabricmc.api.ModInitializer;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class TechnobladeNeverDies implements ModInitializer {
    public static SoundEvent BRUH_SOUND_EVENT = new SoundEvent(new Identifier("technoneverdies:technoneverdies.bruh"));
    public static SoundEvent PHIL_SOUND_EVENT = new SoundEvent(new Identifier("technoneverdies:technoneverdies.phil_look_out"));

    @Override
    public void onInitialize() {

    }
}
