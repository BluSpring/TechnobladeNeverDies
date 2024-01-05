package xyz.bluspring.technobladeneverdies.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LogoDrawer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.bluspring.technobladeneverdies.TechnobladeNeverDies;

@Mixin(LogoDrawer.class)
public class LogoDrawerMixin {
    @Shadow @Final private boolean ignoreAlpha;
    @Unique
    private static final Identifier TECHNO_TITLE_TEXTURE = new Identifier("technoneverdies", "textures/gui/title.png");

    @Inject(method = "draw(Lnet/minecraft/client/gui/DrawContext;IFI)V", at = @At("HEAD"), cancellable = true)
    private void drawTechnoLogo(DrawContext context, int screenWidth, float alpha, int y, CallbackInfo ci) {
        if (!TechnobladeNeverDies.shouldReplaceTitleIcon)
            return;

        context.setShaderColor(1.0F, 1.0F, 1.0F, this.ignoreAlpha ? 1.0F : alpha);
        int i = screenWidth / 2 - 128;
        context.drawTexture(TECHNO_TITLE_TEXTURE, i, y - 30, 0.0F, 0.0F, 256, 128, 256, 128);
        ci.cancel();
    }
}
