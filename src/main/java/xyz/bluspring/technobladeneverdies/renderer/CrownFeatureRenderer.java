package xyz.bluspring.technobladeneverdies.renderer;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class CrownFeatureRenderer<T extends LivingEntity, M extends QuadrupedEntityModel<T>> extends FeatureRenderer<T, M> {
    private static final Identifier CROWN_TEXTURE = new Identifier("technoneverdies", "textures/entity/crown.png");

    private final M model;

    public CrownFeatureRenderer(FeatureRendererContext<T, M> context, M model) {
        super(context);

        this.model = model;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (entity.hasCustomName() && entity.getCustomName().getString().equalsIgnoreCase("technoblade")) {
            this.getContextModel().copyStateTo(this.model);

            this.model.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(CROWN_TEXTURE));

            this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
