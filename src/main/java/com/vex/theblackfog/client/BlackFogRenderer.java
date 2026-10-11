package com.vex.theblackfog.client;

import com.vex.theblackfog.TheBlackFog;
import com.vex.theblackfog.entity.BlackFogEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class BlackFogRenderer extends MobRenderer<BlackFogEntity, EndermanModel<BlackFogEntity>> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(TheBlackFog.MOD_ID, "textures/entity/black_fog.png");

    public BlackFogRenderer(EntityRendererProvider.Context context) {
        super(context, new EndermanModel<>(context.bakeLayer(ModelLayers.ENDERMAN)), 0.45F);
    }

    @Override
    public ResourceLocation getTextureLocation(BlackFogEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(BlackFogEntity entity, float entityYaw, float partialTick,
                      PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        model.prepareMobModel(entity, 0.0F, 0.0F, partialTick);
        model.setupAnim(entity, 0.0F, 0.0F, entity.tickCount + partialTick,
                entity.getYRot(), entity.getXRot());
        model.renderToBuffer(poseStack,
                buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)),
                packedLight, OverlayTexture.NO_OVERLAY,
                0.005F, 0.005F, 0.008F, 1.0F);
        poseStack.popPose();
    }
}