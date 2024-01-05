package xyz.bluspring.technobladeneverdies.mixin;

import net.minecraft.client.resource.SplashTextResourceSupplier;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import xyz.bluspring.technobladeneverdies.TechnobladeNeverDies;

import java.util.List;

@Mixin(SplashTextResourceSupplier.class)
public class SplashTextResourceSupplierMixin {
    @Inject(at = @At("RETURN"), method = "prepare(Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)Ljava/util/List;", locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    public void customAddTechnoSplash(ResourceManager resourceManager, Profiler profiler, CallbackInfoReturnable<List<String>> cir) {
        boolean technobladeOnly = TechnobladeNeverDies.shouldReplaceSplashes;
        List<String> list = new java.util.ArrayList<>(technobladeOnly ? List.of() : cir.getReturnValue());

        list.add("Technoblade never dies!");
        list.add("Officer, I drop kicked that child in self-defense!");
        list.add("Not even close!");
        list.add("The second worst thing to ever happen to those orphans!");
        list.add("Bruhhhh");
        list.add("If you wish to defeat me, train for another million years!");
        list.add("Whatever you do, don't reveal all your techniques in a YouTube video, you fool, you moron.");
        list.add("We win these!");
        list.add("Keep your chin up king, your crown is falling!");

        cir.setReturnValue(list);
    }
}
