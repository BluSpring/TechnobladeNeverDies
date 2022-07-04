package xyz.bluspring.technobladeneverdies.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.bluspring.technobladeneverdies.TechnobladeNeverDies;

import java.util.List;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FFJ)V"), method = "onPlaySound")
    public void replaceSoundIfTechnoblade(ClientWorld instance, PlayerEntity except, double x, double y, double z, SoundEvent sound, SoundCategory category, float volume, float pitch, long seed) {
        if (sound.equals(SoundEvents.ENTITY_PIG_AMBIENT)) {
            var pigEntitiesInGeneralArea = instance.getEntitiesByClass(PigEntity.class, new Box(x - 0.15, y - 0.15, z - 0.15, x + 0.15, y + 0.15, z + 0.15), livingEntity -> true);

            if (pigEntitiesInGeneralArea.isEmpty()) {
                instance.playSound(except, x, y, z, sound, category, volume, pitch);
                return;
            }

            var firstPig = pigEntitiesInGeneralArea.get(0);

            if (
                    firstPig.hasCustomName() &&
                    firstPig.getCustomName().getString().equalsIgnoreCase("technoblade")
            ) {
                instance.playSound(except, x, y, z, TechnobladeNeverDies.BRUH_SOUND_EVENT, category, volume, pitch);
                return;
            }
        }

        instance.playSound(except, x, y, z, sound, category, volume, pitch);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;playSoundFromEntity(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FFJ)V"), method = "onPlaySoundFromEntity")
    public void replaceSoundEntityIfTechnoblade(ClientWorld instance, PlayerEntity except, Entity entity, SoundEvent sound, SoundCategory category, float volume, float pitch, long seed) {
        if (
                entity.getType() == EntityType.PIG &&
                entity.hasCustomName() &&
                entity.getCustomName().getString().equalsIgnoreCase("technoblade")
        ) {
            if (sound.equals(SoundEvents.ENTITY_PIG_AMBIENT)) {
                instance.playSoundFromEntity(except, entity, TechnobladeNeverDies.BRUH_SOUND_EVENT, category, volume, pitch, seed);
            }
        } else {
            instance.playSoundFromEntity(except, entity, sound, category, volume, pitch, seed);
        }
    }
}
