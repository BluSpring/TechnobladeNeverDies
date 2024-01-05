package xyz.bluspring.technobladeneverdies.mixin;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.PigEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.entity.passive.PigEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.bluspring.technobladeneverdies.renderer.PigCrownFeatureRenderer;

@Mixin(PigEntityRenderer.class)
public abstract class PigEntityRendererMixin extends MobEntityRenderer<PigEntity, PigEntityModel<PigEntity>> {
    public PigEntityRendererMixin(EntityRendererFactory.Context context, PigEntityModel<PigEntity> entityModel, float f) {
        super(context, entityModel, f);
    }

    @Inject(at = @At("TAIL"), method = "<init>")
    public void addCrownFeature(EntityRendererFactory.Context context, CallbackInfo ci) {
        this.addFeature(new PigCrownFeatureRenderer<PigEntity, PigEntityModel<PigEntity>>(
                this,
                new PigEntityModel(context.getPart(EntityModelLayers.PIG_SADDLE)))
        );
    }
}
