package com.vex.theblackfog.client;

import com.vex.theblackfog.entity.BlackFogEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class BlackFogRenderer extends MobRenderer<BlackFogEntity, HumanoidModel<BlackFogEntity>> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation("minecraft", "textures/entity/player/wide/steve.png");

    public BlackFogRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.45F);
    }

    @Override
    public ResourceLocation getTextureLocation(BlackFogEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(BlackFogEntity entity, float entityYaw, float partialTick,
                      PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        // Keep normal limb/head width while making the silhouette slightly taller.
        // The previous 0.88 horizontal scale made the arms, legs and head look too thin.
        poseStack.scale(1.0F, 1.12F, 1.0F);
        model.prepareMobModel(entity, 0.0F, 0.0F, partialTick);
        model.setupAnim(entity, 0.0F, 0.0F, entity.tickCount + partialTick,
                entity.getYRot(), entity.getXRot());
        model.renderToBuffer(poseStack,
                buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)),
                packedLight, OverlayTexture.NO_OVERLAY,
                0.0F, 0.0F, 0.0F, 1.0F);
        poseStack.popPose();
    }
}
