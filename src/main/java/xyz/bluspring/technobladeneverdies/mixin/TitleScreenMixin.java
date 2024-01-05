package xyz.bluspring.technobladeneverdies.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import xyz.bluspring.technobladeneverdies.TechnobladeNeverDies;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @Shadow @Final private boolean doBackgroundFade;
    @Shadow private long backgroundFadeStart;
    @Unique
    private static final Identifier TECHNO_TITLE_TEXTURE = new Identifier("technoneverdies", "textures/gui/title.png");

    @Inject(method = "loadTexturesAsync", at = @At("RETURN"), cancellable = true)
    private static void appendTechnoTexture(TextureManager textureManager, Executor executor, CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        cir.setReturnValue(CompletableFuture.allOf(cir.getReturnValue(), textureManager.loadTextureAsync(TECHNO_TITLE_TEXTURE, executor)));
    }

    @Inject(at = @At("TAIL"), method = "render", locals = LocalCapture.CAPTURE_FAILHARD)
    public void addCreditText(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci, float f, float g) {
        if (TechnobladeNeverDies.shouldReplaceTitleIcon) {
            int l = MathHelper.ceil(g * 255.0F) << 24;
            context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, "Title text made by @MrBrose_ on Twitter", 2, ((TitleScreen) (Object) this).height - 20, 0xFFFFFF | l);
        }
    }
}
