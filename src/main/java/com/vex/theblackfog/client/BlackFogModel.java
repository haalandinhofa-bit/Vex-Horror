package com.vex.theblackfog.client;

import com.vex.theblackfog.entity.BlackFogEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

/** Custom Blockbench-inspired model for The Black Fog. */
public class BlackFogModel extends EntityModel<BlackFogEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation("theblackfog", "black_fog"), "main");

    private final ModelPart head;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart body;

    public BlackFogModel(ModelPart root) {
        this.head = root.getChild("head");
        this.leftArm = root.getChild("left_arm");
        this.rightArm = root.getChild("right_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // Based on the user's Blockbench geometry, with valid Java part names.
        root.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 12)
                        .addBox(-2.0F, -6.0F, -3.0F, 4.0F, 7.0F, 4.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));

        root.addOrReplaceChild("left_arm",
                CubeListBuilder.create().texOffs(22, 18)
                        .addBox(-1.0F, 0.0F, -1.0F, 1.0F, 16.0F, 2.0F),
                PartPose.offset(4.0F, -8.0F, 0.0F));

        root.addOrReplaceChild("right_arm",
                CubeListBuilder.create().texOffs(0, 23)
                        .addBox(0.0F, 0.0F, -1.0F, 1.0F, 16.0F, 2.0F),
                PartPose.offset(-4.0F, -8.0F, 0.0F));

        root.addOrReplaceChild("right_leg",
                CubeListBuilder.create().texOffs(16, 12)
                        .addBox(0.0F, -2.0F, -1.0F, 1.0F, 16.0F, 2.0F),
                PartPose.offset(-2.0F, 8.0F, 0.0F));

        root.addOrReplaceChild("left_leg",
                CubeListBuilder.create().texOffs(22, 0)
                        .addBox(-1.0F, -2.0F, -1.0F, 1.0F, 16.0F, 2.0F),
                PartPose.offset(2.0F, 8.0F, 0.0F));

        root.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-3.0F, -24.0F, -2.0F, 6.0F, 8.0F, 4.0F),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(BlackFogEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        head.xRot = headPitch * ((float)Math.PI / 180F);

        float swing = Mth.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
        float oppositeSwing = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * limbSwingAmount;
        rightArm.xRot = swing;
        leftArm.xRot = oppositeSwing;
        rightLeg.xRot = oppositeSwing;
        leftLeg.xRot = swing;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer,
                               int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
