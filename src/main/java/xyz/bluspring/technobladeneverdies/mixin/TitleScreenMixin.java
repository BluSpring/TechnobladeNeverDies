package xyz.bluspring.technobladeneverdies.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.function.BiConsumer;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @Shadow @Final private boolean doBackgroundFade;
    @Shadow private long backgroundFadeStart;
    private static final Identifier TECHNO_TITLE_TEXTURE = new Identifier("technoneverdies", "textures/gui/title.png");

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;drawWithOutline(IILjava/util/function/BiConsumer;)V"), method = "render", cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    public void modifyTitleTexture(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci, float f, int i, int j) {
        var titleScreen = (TitleScreen) (Object) this;

        RenderSystem.setShaderTexture(0, TECHNO_TITLE_TEXTURE);

        titleScreen.drawWithOutline(j, 30, (x, y) -> {
            TitleScreen.drawTexture(matrices, x + 10, y - 35, 0, 0, 256, 128, 256, 128);
        });
    }

    @Inject(at = @At("TAIL"), method = "render")
    public void addCreditText(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        float f = this.doBackgroundFade ? (float)(Util.getMeasuringTimeMs() - this.backgroundFadeStart) / 1000.0f : 1.0f;
        float g = this.doBackgroundFade ? MathHelper.clamp(f - 1.0f, 0.0f, 1.0f) : 1.0f;
        int l = MathHelper.ceil(g * 255.0f) << 24;

        TitleScreen.drawStringWithShadow(matrices, MinecraftClient.getInstance().textRenderer, "Title text made by @MrBrose_ on Twitter", 2, ((TitleScreen) (Object) this).height - 20, 0xFFFFFF | l);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;drawWithOutline(IILjava/util/function/BiConsumer;)V"), method = "render")
    public void removeMinecraftText(TitleScreen instance, int i, int j, BiConsumer biConsumer) {}

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIFFIIII)V"), method = "render")
    public void removeEditionText(MatrixStack matrixStack, int i, int j, float m, float n, int o, int p, int q, int r) {}
}
