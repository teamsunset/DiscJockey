package com.sunset.discjockey.client.model;// Made with Blockbench 4.8.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sunset.discjockey.util.Reference;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class ModelDDJ400<T extends Entity> extends EntityModel<T>
{
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Reference.MOD_ID, "ddj_400"), "main");
    public final ModelPart whole;
    public final ModelPart M;
    public final ModelPart R;
    public final ModelPart L;

    public ModelDDJ400(ModelPart root) {
        this.whole = root.getChild("whole");
        this.M = root.getChild("M");
        this.R = root.getChild("R");
        this.L = root.getChild("L");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition L = partdefinition.addOrReplaceChild("L", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition Lr = L.addOrReplaceChild("Lr", CubeListBuilder.create().texOffs(81, 37).addBox(-17.375F, -5.95F, 3.875F, 9.0F, 8.25F, 9.0F, new CubeDeformation(-3.375F))
                .texOffs(54, 82).addBox(-17.625F, -5.95F, 3.875F, 7.0F, 8.25F, 9.0F, new CubeDeformation(-3.375F))
                .texOffs(76, 108).addBox(-17.875F, -5.95F, 4.375F, 7.0F, 8.25F, 8.0F, new CubeDeformation(-3.375F))
                .texOffs(27, 91).addBox(-15.125F, -5.95F, 3.875F, 7.0F, 8.25F, 9.0F, new CubeDeformation(-3.375F))
                .texOffs(24, 108).addBox(-14.875F, -5.95F, 4.375F, 7.0F, 8.25F, 8.0F, new CubeDeformation(-3.375F))
                .texOffs(95, 21).addBox(-17.375F, -5.95F, 6.125F, 9.0F, 8.25F, 7.0F, new CubeDeformation(-3.375F))
                .texOffs(106, 116).addBox(-16.875F, -5.95F, 6.375F, 8.0F, 8.25F, 7.0F, new CubeDeformation(-3.375F))
                .texOffs(0, 101).addBox(-17.375F, -5.95F, 3.625F, 9.0F, 8.25F, 7.0F, new CubeDeformation(-3.375F))
                .texOffs(110, 84).addBox(-16.875F, -5.95F, 3.375F, 8.0F, 8.25F, 7.0F, new CubeDeformation(-3.375F)), PartPose.offset(8.0F, 0.0F, -8.0F));

        PartDefinition L_v = L.addOrReplaceChild("L_v", CubeListBuilder.create().texOffs(100, 91).addBox(-11.875F, -3.5F, 3.875F, 3.0F, 2.75F, 2.5F, new CubeDeformation(-1.125F))
                .texOffs(145, 124).addBox(-11.875F, -3.275F, 3.625F, 3.0F, 2.5F, 2.5F, new CubeDeformation(-1.125F))
                .texOffs(145, 78).addBox(-11.875F, -3.275F, 4.125F, 3.0F, 2.5F, 2.5F, new CubeDeformation(-1.125F)), PartPose.offset(7.5F, 0.0F, -8.2F));

        PartDefinition lb1 = L.addOrReplaceChild("lb1", CubeListBuilder.create().texOffs(54, 46).addBox(-18.5F, -6.35F, 1.5F, 9.0F, 9.0F, 8.75F, new CubeDeformation(-4.25F))
                .texOffs(121, 131).addBox(-16.0F, -3.85F, 2.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(105, 131).addBox(-13.75F, -3.85F, 2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(131, 62).addBox(-14.5F, -3.85F, 2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(131, 40).addBox(-15.25F, -3.85F, 2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(130, 112).addBox(-16.0F, -3.85F, 2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(127, 99).addBox(-15.25F, -3.85F, 2.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(93, 127).addBox(-14.5F, -3.85F, 2.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(125, 8).addBox(-13.75F, -3.85F, 2.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(77, 124).addBox(-17.0F, -3.85F, 2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(30, 124).addBox(-17.0F, -3.85F, 3.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(54, 28).addBox(-17.75F, -6.35F, 1.5F, 9.0F, 9.0F, 8.75F, new CubeDeformation(-4.25F))
                .texOffs(0, 46).addBox(-17.0F, -6.35F, 1.5F, 9.0F, 9.0F, 8.75F, new CubeDeformation(-4.25F))
                .texOffs(39, 1).addBox(-16.25F, -6.35F, 1.5F, 9.0F, 9.0F, 8.75F, new CubeDeformation(-4.25F)), PartPose.offset(8.0F, 0.0F, -8.0F));

        PartDefinition l_lamp = L.addOrReplaceChild("l_lamp", CubeListBuilder.create().texOffs(120, 20).addBox(-7.0F, 4.0F, 1.25F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(139, 32).addBox(-8.0F, 4.0F, 1.25F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(116, 0).addBox(-9.0F, 4.0F, 1.25F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(145, 131).addBox(-5.5F, 4.15F, 1.25F, 3.75F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(145, 84).addBox(-6.0F, 4.15F, 1.25F, 3.75F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(108, 36).addBox(-4.75F, 4.15F, 1.25F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F)), PartPose.offset(0.0F, -8.0F, 0.0F));

        PartDefinition R = partdefinition.addOrReplaceChild("R", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition r_lamp = R.addOrReplaceChild("r_lamp", CubeListBuilder.create().texOffs(133, 135).addBox(2.75F, 4.0F, 1.25F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(108, 139).addBox(1.75F, 4.0F, 1.25F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(92, 136).addBox(0.75F, 4.0F, 1.25F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(123, 32).addBox(5.0F, 4.15F, 1.25F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(108, 147).addBox(4.25F, 4.15F, 1.25F, 3.75F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(147, 40).addBox(3.75F, 4.15F, 1.25F, 3.75F, 4.0F, 4.0F, new CubeDeformation(-1.75F)), PartPose.offset(0.0F, -8.0F, 0.0F));

        PartDefinition rb1 = R.addOrReplaceChild("rb1", CubeListBuilder.create().texOffs(54, 64).addBox(-8.75F, -6.35F, 1.5F, 9.0F, 9.0F, 8.75F, new CubeDeformation(-4.25F))
                .texOffs(133, 80).addBox(-6.25F, -3.85F, 2.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(16, 132).addBox(-4.0F, -3.85F, 2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(132, 16).addBox(-4.75F, -3.85F, 2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(132, 24).addBox(-5.5F, -3.85F, 2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(32, 132).addBox(-6.25F, -3.85F, 2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(80, 132).addBox(-5.5F, -3.85F, 2.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(64, 132).addBox(-4.75F, -3.85F, 2.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(48, 132).addBox(-4.0F, -3.85F, 2.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(132, 0).addBox(-7.25F, -3.85F, 2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(0, 132).addBox(-7.25F, -3.85F, 3.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(66, 10).addBox(-8.0F, -6.35F, 1.5F, 9.0F, 9.0F, 8.75F, new CubeDeformation(-4.25F))
                .texOffs(27, 55).addBox(-7.25F, -6.35F, 1.5F, 9.0F, 9.0F, 8.75F, new CubeDeformation(-4.25F))
                .texOffs(0, 64).addBox(-6.5F, -6.35F, 1.5F, 9.0F, 9.0F, 8.75F, new CubeDeformation(-4.25F)), PartPose.offset(8.0F, 0.0F, -8.0F));

        PartDefinition R_r = R.addOrReplaceChild("R_r", CubeListBuilder.create().texOffs(81, 55).addBox(-4.525F, -3.25F, -4.625F, 9.0F, 8.25F, 9.0F, new CubeDeformation(-3.375F))
                .texOffs(93, 0).addBox(-4.775F, -3.25F, -4.625F, 7.0F, 8.25F, 9.0F, new CubeDeformation(-3.375F))
                .texOffs(109, 64).addBox(-5.025F, -3.25F, -4.125F, 7.0F, 8.25F, 8.0F, new CubeDeformation(-3.375F))
                .texOffs(77, 91).addBox(-2.275F, -3.25F, -4.625F, 7.0F, 8.25F, 9.0F, new CubeDeformation(-3.375F))
                .texOffs(109, 46).addBox(-2.025F, -3.25F, -4.125F, 7.0F, 8.25F, 8.0F, new CubeDeformation(-3.375F))
                .texOffs(52, 101).addBox(-4.525F, -3.25F, -2.375F, 9.0F, 8.25F, 7.0F, new CubeDeformation(-3.375F))
                .texOffs(47, 117).addBox(-4.025F, -3.25F, -2.125F, 8.0F, 8.25F, 7.0F, new CubeDeformation(-3.375F))
                .texOffs(102, 101).addBox(-4.525F, -3.25F, -4.875F, 9.0F, 8.25F, 7.0F, new CubeDeformation(-3.375F))
                .texOffs(0, 117).addBox(-4.025F, -3.25F, -5.125F, 8.0F, 8.25F, 7.0F, new CubeDeformation(-3.375F)), PartPose.offset(4.9F, -2.7F, 0.5F));

        PartDefinition R_v = R.addOrReplaceChild("R_v", CubeListBuilder.create().texOffs(142, 111).addBox(-2.125F, -3.5F, 4.375F, 3.0F, 2.75F, 2.5F, new CubeDeformation(-1.125F))
                .texOffs(0, 148).addBox(-2.125F, -3.275F, 4.125F, 3.0F, 2.5F, 2.5F, new CubeDeformation(-1.125F))
                .texOffs(147, 64).addBox(-2.125F, -3.275F, 4.625F, 3.0F, 2.5F, 2.5F, new CubeDeformation(-1.125F)), PartPose.offset(7.5F, 0.0F, -8.8F));

        PartDefinition M = partdefinition.addOrReplaceChild("M", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition M_bt = M.addOrReplaceChild("M_bt", CubeListBuilder.create().texOffs(27, 10).addBox(-1.325F, 4.4F, -5.625F, 2.5F, 2.75F, 3.0F, new CubeDeformation(-1.125F)), PartPose.offset(0.0F, -8.0F, 0.0F));

        PartDefinition mh = M.addOrReplaceChild("mh", CubeListBuilder.create().texOffs(139, 103).addBox(-16.5F, -2.75F, 9.925F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(139, 95).addBox(-15.0F, -2.75F, 9.9F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(139, 48).addBox(-14.25F, -2.75F, 9.9F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(139, 70).addBox(-15.75F, -2.75F, 9.9F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F)), PartPose.offset(8.0F, 0.0F, -8.0F));

        PartDefinition m_v = M.addOrReplaceChild("m_v", CubeListBuilder.create().texOffs(81, 28).addBox(-1.125F, 4.4F, -4.125F, 3.0F, 2.75F, 2.5F, new CubeDeformation(-1.125F))
                .texOffs(54, 19).addBox(-2.125F, 4.4F, -4.125F, 3.0F, 2.75F, 2.5F, new CubeDeformation(-1.125F)), PartPose.offset(0.0F, -8.0F, 0.0F));

        PartDefinition m_lamp = M.addOrReplaceChild("m_lamp", CubeListBuilder.create().texOffs(133, 127).addBox(-0.5F, 4.1F, -5.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
                .texOffs(32, 140).addBox(-2.05F, 3.525F, 1.575F, 3.85F, 4.275F, 3.85F, new CubeDeformation(-1.75F))
                .texOffs(0, 28).addBox(-3.75F, 1.65F, -0.75F, 9.0F, 9.0F, 8.75F, new CubeDeformation(-4.25F))
                .texOffs(27, 19).addBox(-5.5F, 1.65F, -0.75F, 9.0F, 9.0F, 8.75F, new CubeDeformation(-4.25F)), PartPose.offset(0.0F, -8.0F, 0.0F));

        PartDefinition m_knob = M.addOrReplaceChild("m_knob", CubeListBuilder.create().texOffs(16, 140).addBox(-10.675F, -4.525F, 8.825F, 3.85F, 4.275F, 3.85F, new CubeDeformation(-1.75F))
                .texOffs(0, 140).addBox(-9.425F, -4.525F, 8.825F, 3.85F, 4.275F, 3.85F, new CubeDeformation(-1.75F))
                .texOffs(92, 144).addBox(-10.675F, -4.475F, 7.825F, 3.85F, 4.275F, 3.85F, new CubeDeformation(-1.75F))
                .texOffs(143, 56).addBox(-9.425F, -4.475F, 7.825F, 3.85F, 4.275F, 3.85F, new CubeDeformation(-1.75F))
                .texOffs(144, 20).addBox(-10.675F, -4.475F, 7.075F, 3.85F, 4.275F, 3.85F, new CubeDeformation(-1.75F))
                .texOffs(142, 116).addBox(-9.425F, -4.525F, 7.075F, 3.85F, 4.275F, 3.85F, new CubeDeformation(-1.75F))
                .texOffs(136, 143).addBox(-10.675F, -4.525F, 6.325F, 3.85F, 4.275F, 3.85F, new CubeDeformation(-1.75F))
                .texOffs(80, 140).addBox(-9.425F, -4.525F, 6.325F, 3.85F, 4.275F, 3.85F, new CubeDeformation(-1.75F))
                .texOffs(120, 143).addBox(-10.675F, -4.525F, 5.575F, 3.85F, 4.275F, 3.85F, new CubeDeformation(-1.75F))
                .texOffs(64, 140).addBox(-9.425F, -4.525F, 5.575F, 3.85F, 4.275F, 3.85F, new CubeDeformation(-1.75F))
                .texOffs(141, 8).addBox(-11.675F, -4.525F, 6.075F, 3.85F, 4.275F, 3.85F, new CubeDeformation(-1.75F))
                .texOffs(48, 140).addBox(-11.675F, -4.525F, 4.825F, 3.85F, 4.275F, 3.85F, new CubeDeformation(-1.75F)), PartPose.offset(8.0F, 0.0F, -8.0F));

        PartDefinition m_bl = M.addOrReplaceChild("m_bl", CubeListBuilder.create().texOffs(27, 73).addBox(-11.0F, -6.35F, 3.5F, 9.0F, 9.0F, 8.75F, new CubeDeformation(-4.25F))
                .texOffs(27, 37).addBox(-11.0F, -6.35F, 3.0F, 9.0F, 9.0F, 8.75F, new CubeDeformation(-4.25F))
                .texOffs(0, 82).addBox(-11.225F, -6.35F, 4.45F, 8.875F, 9.0F, 8.75F, new CubeDeformation(-4.25F))
                .texOffs(81, 73).addBox(-10.625F, -6.35F, 4.45F, 8.875F, 9.0F, 8.75F, new CubeDeformation(-4.25F))
                .texOffs(0, 10).addBox(-11.0F, -6.35F, 5.5F, 9.0F, 9.0F, 8.75F, new CubeDeformation(-4.25F)), PartPose.offset(8.0F, 0.0F, -8.0F));

        PartDefinition whole = partdefinition.addOrReplaceChild("whole", CubeListBuilder.create().texOffs(0, 0).addBox(-7.5F, -0.5F, -4.5F, 15.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.5F, -0.5F));

        PartDefinition support = whole.addOrReplaceChild("support", CubeListBuilder.create().texOffs(66, 3).addBox(-15.5F, -1.0F, -2.0F, 15.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 1.5F, -2.5F));

        PartDefinition backoblique_r1 = support.addOrReplaceChild("backoblique_r1", CubeListBuilder.create().texOffs(102, 17).addBox(-15.5F, -1.0F, 0.0F, 15.0F, 1.0F, 1.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition group = support.addOrReplaceChild("group", CubeListBuilder.create().texOffs(66, 0).addBox(-15.5F, -1.0F, 5.0F, 15.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition headoblique_r1 = group.addOrReplaceChild("headoblique_r1", CubeListBuilder.create().texOffs(66, 6).addBox(-15.5F, -0.6173F, -2.9239F, 15.0F, 1.0F, 1.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.7362F, 6.2774F, -0.7854F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        whole.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        M.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        R.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        L.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}