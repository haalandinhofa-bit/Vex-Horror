package com.vex.theblackfog.client;

import com.vex.theblackfog.entity.BlackFogEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BlackFogRenderer extends MobRenderer<BlackFogEntity, HumanoidModel<BlackFogEntity>> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation("minecraft", "textures/entity/player/wide/steve.png");

    public BlackFogRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.35F);
    }

    @Override
    public ResourceLocation getTextureLocation(BlackFogEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(BlackFogEntity entity, PoseStack poseStack, float partialTick) {
        // Keep the vanilla humanoid renderer's normal limb and head geometry.
        // Only add a modest height increase; avoid manual model rendering.
        poseStack.scale(1.0F, 1.08F, 1.0F);
    }
}
