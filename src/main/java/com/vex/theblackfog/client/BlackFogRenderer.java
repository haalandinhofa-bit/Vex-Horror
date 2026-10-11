package com.vex.theblackfog.client;

import com.vex.theblackfog.entity.BlackFogEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BlackFogRenderer extends MobRenderer<BlackFogEntity, BlackFogModel> {
    // The provided 64x64 Blockbench texture should be placed at this asset path.
    private static final ResourceLocation TEXTURE =
            new ResourceLocation("theblackfog", "textures/entity/black_fog.png");

    public BlackFogRenderer(EntityRendererProvider.Context context) {
        super(context, new BlackFogModel(context.bakeLayer(BlackFogModel.LAYER_LOCATION)), 0.35F);
    }

    @Override
    public ResourceLocation getTextureLocation(BlackFogEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(BlackFogEntity entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(1.0F, 1.08F, 1.0F);
    }
}
