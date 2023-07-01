package com.bitwyz.unicyclemod.entity;

import com.bitwyz.unicyclemod.UnicycleMod;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class UnicycleModel extends ListModel<Unicycle> {

    public static final String BODY = "body";
    public static final ModelLayerLocation UNICYCLE_LAYER =
            new ModelLayerLocation(
                    new ResourceLocation(UnicycleMod.MOD_ID, "models/entity/unicycle"), BODY);

    private final ImmutableList<ModelPart> parts;

    public UnicycleModel(ModelPart pRoot) {
        parts =
                ImmutableList.of(
                        pRoot.getChild("generated_0"),
                        pRoot.getChild("generated_1"),
                        pRoot.getChild("generated_2"),
                        pRoot.getChild("generated_3"),
                        pRoot.getChild("generated_4"),
                        pRoot.getChild("generated_5"),
                        pRoot.getChild("generated_6"),
                        pRoot.getChild("generated_7"),
                        pRoot.getChild("generated_8"),
                        pRoot.getChild("generated_9"),
                        pRoot.getChild("generated_10"),
                        pRoot.getChild("generated_11"),
                        pRoot.getChild("generated_12"));
    }

    public static LayerDefinition createBodyModel() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("generated_0",CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, 0.0F, 0.0F, 2F, 10F, 2F),PartPose.offsetAndRotation(-1F, 2F, 5F, 0.0f, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("generated_1",CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, 0.0F, 0.0F, 2F, 2F, 14F),PartPose.offsetAndRotation(-1F, 12F, -7F, 0.0f, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("generated_2",CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 2F, 10F, 2F),PartPose.offsetAndRotation(-1F, 2F, -7F, 0.0f, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("generated_3",CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 2F, 2F, 14F),PartPose.offsetAndRotation(-1F, 0F, -7F, 0.0f, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("generated_4",CubeListBuilder.create().texOffs(28, 28).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2F, 4F),PartPose.offsetAndRotation(-0.5F, 6F, 1F, 0.0f, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("generated_5",CubeListBuilder.create().texOffs(18, 16).addBox(0.0F, 0.0F, 0.0F, 1.0F, 10F, 2F),PartPose.offsetAndRotation(-0.5F, 2F, -1F, 0.0f, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("generated_6",CubeListBuilder.create().texOffs(20, 24).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2F, 4F),PartPose.offsetAndRotation(-0.5F, 6F, -5F, 0.0f, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("generated_7",CubeListBuilder.create().texOffs(24, 20).addBox(0.0F, 0.0F, 0.0F, 4F, 2F, 2F),PartPose.offsetAndRotation(-2F, 6F, -1F, 0.0f, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("generated_8",CubeListBuilder.create().texOffs(24, 16).addBox(0.0F, 0.0F, 0.0F, 4F, 2F, 2F),PartPose.offsetAndRotation(-2F, 15F, -1F, 0.0f, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("generated_9",CubeListBuilder.create().texOffs(18, 0).addBox(0.0F, 0.0F, 0.0F, 2F, 5F, 2F),PartPose.offsetAndRotation(-1F, 17F, -1F, 0.0f, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("generated_10",CubeListBuilder.create().texOffs(18, 0).addBox(0.0F, 0.0F, 0.0F, 4F, 2F, 9F),PartPose.offsetAndRotation(-2F, 22F, -5F, 0.0f, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("generated_11",CubeListBuilder.create().texOffs(8, 16).addBox(0.0F, 0.0F, 0.0F, 1F, 11F, 2F),PartPose.offsetAndRotation(-3F, 6F, -1F, 0.0f, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("generated_12",CubeListBuilder.create().texOffs(8, 0).addBox(0.0F, 0.0F, 0.0F, 1F, 11F, 2F),PartPose.offsetAndRotation(2F, 6F, -1F, 0.0f, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public Iterable<ModelPart> parts() {
        return this.parts;
    }

    @Override
    public void setupAnim(
            Unicycle pEntity,
            float pLimbSwing,
            float pLimbSwingAmount,
            float pAgeInTicks,
            float pNetHeadYaw,
            float pHeadPitch) {}
}
