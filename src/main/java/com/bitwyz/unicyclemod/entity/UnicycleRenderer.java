package com.bitwyz.unicyclemod.entity;

import com.bitwyz.unicyclemod.UnicycleMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;

public class UnicycleRenderer extends EntityRenderer<Unicycle> {

  private final ResourceLocation texture;
  private final IronUnicycleModel<Unicycle> model;

  public UnicycleRenderer(EntityRendererProvider.Context pContext) {
    super(pContext);

    texture = new ResourceLocation(UnicycleMod.MOD_ID, "textures/entity/iron_unicycle.png");
    model = new IronUnicycleModel<>(pContext.bakeLayer(IronUnicycleModel.LAYER_LOCATION));
  }

  @Override
  public void render(
      Unicycle pEntity,
      float pEntityYaw,
      float pPartialTick,
      PoseStack pMatrixStack,
      MultiBufferSource pBuffer,
      int pPackedLight) {

    pMatrixStack.pushPose();
    pMatrixStack.mulPose(Axis.YP.rotationDegrees(180.0f - pEntityYaw));

    model.setupAnim(pEntity, pPartialTick, 0.0f, -0.1f, 0.0f, 0.0f);
    VertexConsumer vertexConsumer = pBuffer.getBuffer(model.renderType(texture));

    model.renderToBuffer(
        pMatrixStack,
        vertexConsumer,
        pPackedLight,
        OverlayTexture.NO_OVERLAY,
        1.0f,
        1.0f,
        1.0f,
        1.0f);

    pMatrixStack.popPose();
    super.render(pEntity, pEntityYaw, pPartialTick, pMatrixStack, pBuffer, pPackedLight);
  }

  @Override
  public ResourceLocation getTextureLocation(Unicycle pEntity) {
    return texture;
  }
}
