package com.sunset.discjockey.client.renderer.BlockEntity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.sunset.discjockey.block.BlockEntity.BlockEntityDDJ400;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.AbstractWidget.ControllerButton;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.AbstractWidget.ControllerFader;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidget;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.ControllerMixFader;
import com.sunset.discjockey.client.model.ModelDDJ400;
import com.sunset.discjockey.client.model.ModelManager;
import com.sunset.discjockey.util.ModReference;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;

public class BlockEntityRendererDDJ400 implements BlockEntityRenderer<BlockEntityDDJ400> {
    private BlockEntityRendererProvider.Context context;

    public static ModelDDJ400<?> MODEL;

    public static ModelManager MODEL_MANAGER;
    public static final ResourceLocation TEXTURE = new ResourceLocation(ModReference.MOD_ID, "textures/model/ddj_400.png");
    public static BlockEntityRendererDDJ400 instance;

    public BlockEntityRendererDDJ400(BlockEntityRendererProvider.Context context) {
        this.context = context;
        MODEL_MANAGER = new ModelManager(() -> context.bakeLayer(ModelDDJ400.LAYER_LOCATION));
//                MODEL = new ModelDDJ400<>(context.bakeLayer(ModelDDJ400.LAYER_LOCATION));
//        MODEL = new ModelDDJ400<>(ModelDDJ400.createBodyLayer().bakeRoot());
        MODEL = new ModelDDJ400<>(MODEL_MANAGER.getRoot());
        instance = this;
    }

    public void renderWidgets(BlockEntityDDJ400 blockEntity) {

        for (ControllerWidget controllerWidget : blockEntity.controllerWidgetManager.controllerWidgets) {
            if (controllerWidget.id.equals("mix_fader")) {
                MODEL_MANAGER.setRelative(
                        "mix_fader",
                        "x",
                        (float) (-1 * ((ControllerMixFader) controllerWidget).value.get())
                );
            } else if (controllerWidget instanceof ControllerButton controllerButton) {
                MODEL_MANAGER.setRelative(
                        controllerButton.id,
                        "y",
                        controllerButton.pressed.get() ? 0.1f : 0
                );
            } else if (controllerWidget.id.contains("bpm_fader")) {
                MODEL_MANAGER.setRelative(
                        controllerWidget.id,
                        "z",
                        (float) (((ControllerFader) controllerWidget).value.get() * 1.5)
                );
            } else if (controllerWidget.id.contains("volume_fader")) {
                MODEL_MANAGER.setRelative(
                        controllerWidget.id,
                        "z",
                        (float) ((((ControllerFader) controllerWidget).value.get() - 1) * 1.5)
                );
            }
        }
    }

    @Override
    public void render(BlockEntityDDJ400 pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        BlockPos pos = pBlockEntity.getBlockPos();
        this.renderWidgets(pBlockEntity);
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
