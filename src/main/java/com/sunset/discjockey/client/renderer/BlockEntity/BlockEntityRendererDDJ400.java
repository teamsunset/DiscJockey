package com.sunset.discjockey.client.renderer.BlockEntity;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import com.sunset.discjockey.block.BlockEntity.BlockEntityDDJ400;
import com.sunset.discjockey.client.model.ModelDDJ400;
import com.sunset.discjockey.util.Reference;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;

public class BlockEntityRendererDDJ400 implements BlockEntityRenderer<BlockEntityDDJ400>
{
    private BlockEntityRendererProvider.Context context;

    public static ModelDDJ400<?> MODEL;
    public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/model/ddj_400.png");
    public static BlockEntityRendererDDJ400 instance;


    public BlockEntityRendererDDJ400(BlockEntityRendererProvider.Context context) {
        this.context = context;
        MODEL = new ModelDDJ400<>(context.bakeLayer(ModelDDJ400.LAYER_LOCATION));
        instance = this;
    }

    @Override
    public void render(BlockEntityDDJ400 pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        BlockPos pos = pBlockEntity.getBlockPos();

//        ModelPart M_bt = MODEL.M.getChild("M_bt");
//        M_bt.x = (float) (-1 * pBlockEntity.getMiddleBladeFader().getVal());
        renderModel(pPoseStack, pBuffer, pPackedLight, pBlockEntity.getBlockState().getValue(HorizontalDirectionalBlock.FACING));
    }

    public void renderModel(PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, Direction facing) {
        matrixStack.pushPose();
        matrixStack.translate(0.5, 1.5, 0.5);

        switch (facing) {
            case NORTH -> matrixStack.mulPose(Axis.YP.rotationDegrees(0));
            case WEST -> matrixStack.mulPose(Axis.YP.rotationDegrees(90));
            case SOUTH -> matrixStack.mulPose(Axis.YP.rotationDegrees(180));
            case EAST -> matrixStack.mulPose(Axis.YP.rotationDegrees(270));
        }

        matrixStack.mulPose(Axis.ZP.rotationDegrees(180));
        VertexConsumer vertexBuilder = buffer.getBuffer(RenderType.entityTranslucent(TEXTURE));
        MODEL.renderToBuffer(matrixStack, vertexBuilder, combinedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        matrixStack.popPose();
    }
}
