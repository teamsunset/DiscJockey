package com.sunset.discjockey.client.renderer.BlockEntity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sunset.discjockey.block.BlockEntity.BlockEntityDDJ400;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.resources.model.BakedModel;

public class BlockEntityRendererDDJ400 implements BlockEntityRenderer<BlockEntityDDJ400>
{
    private BlockEntityRendererProvider.Context context;

    public BlockEntityRendererDDJ400(BlockEntityRendererProvider.Context pContext){this.context=pContext;}

    @Override
    public void render(BlockEntityDDJ400 pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        BlockRenderDispatcher dispatcher = this.context.getBlockRenderDispatcher();
        VertexConsumer buffer = pBuffer.getBuffer(RenderType.translucentNoCrumbling());

    }
}
