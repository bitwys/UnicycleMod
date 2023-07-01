package com.bitwyz.unicyclemod.entity;
// Made with Blockbench 4.7.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.bitwyz.unicyclemod.UnicycleMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class IronUnicycleModel<T extends Unicycle> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity
    // renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(
                    new ResourceLocation(UnicycleMod.MOD_ID, "model/entity/iron_unicycle"), "main");
    private final ModelPart wheel;
    private final ModelPart seat;

    public IronUnicycleModel(ModelPart root) {
        this.wheel = root.getChild("wheel");
        this.seat = root.getChild("seat");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition wheel = partdefinition.addOrReplaceChild("wheel", CubeListBuilder.create().texOffs(30, 14).addBox(-1.0F, -5.0F, 5.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-1.0F, 5.0F, -7.0F, 2.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-1.0F, -5.0F, -7.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -7.0F, -7.0F, 2.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(18, 16).addBox(-0.5F, -1.0F, -5.0F, 1.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(8, 16).addBox(-0.5F, -5.0F, -1.0F, 1.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 32).addBox(-4.0F, -0.5F, -0.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(18, 19).addBox(-7.0F, -0.5F, 3.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(18, 16).addBox(4.0F, -0.5F, -5.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(27, 28).addBox(3.0F, -0.5F, -4.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 0.0F));

        PartDefinition seat = partdefinition.addOrReplaceChild("seat", CubeListBuilder.create().texOffs(7, 32).addBox(6.0F, -2.0F, -9.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(18, 0).addBox(7.0F, 10.0F, -9.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(18, 0).addBox(6.0F, 14.0F, -13.0F, 4.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(6, 0).addBox(10.0F, -2.0F, -9.0F, 1.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(5.0F, -2.0F, -9.0F, 1.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(18, 11).addBox(6.0F, 9.0F, -9.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 8.0F, 8.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        wheel.xRot = entity.getWheelRotation();
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        wheel.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        seat.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}