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

public class ModelDDJ400<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Reference.MOD_ID, "ddj_400"), "main");
	public final ModelPart bone;

	public ModelDDJ400(ModelPart root) {
		this.bone = root.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition L = bone.addOrReplaceChild("L", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, 0.0F));

		PartDefinition L_v = L.addOrReplaceChild("L_v", CubeListBuilder.create().texOffs(54, 59).addBox(-1.375F, -3.5F, -1.625F, 3.0F, 2.75F, 2.5F, new CubeDeformation(-1.125F))
		.texOffs(44, 60).addBox(-1.375F, -3.275F, -1.875F, 3.0F, 2.5F, 2.5F, new CubeDeformation(-1.125F))
		.texOffs(44, 56).addBox(-1.375F, -3.275F, -1.375F, 3.0F, 2.5F, 2.5F, new CubeDeformation(-1.125F)), PartPose.offset(-3.0F, 8.0F, -3.525F));

		PartDefinition lb1 = L.addOrReplaceChild("lb1", CubeListBuilder.create().texOffs(9, 49).addBox(-16.0F, -3.85F, 2.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(9, 49).addBox(-13.75F, -3.85F, 2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(9, 49).addBox(-14.5F, -3.85F, 2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(9, 49).addBox(-15.25F, -3.85F, 2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(9, 49).addBox(-16.0F, -3.85F, 2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(9, 49).addBox(-15.25F, -3.85F, 2.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(9, 49).addBox(-14.5F, -3.85F, 2.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(9, 49).addBox(-13.75F, -3.85F, 2.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(0, 87).addBox(-17.15F, -3.85F, 3.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(0, 66).addBox(-17.15F, -3.85F, 2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F)), PartPose.offset(8.0F, 8.0F, -8.0F));

		PartDefinition 7_3_r1 = lb1.addOrReplaceChild("7_3_r1", CubeListBuilder.create().texOffs(89, 105).addBox(-7.6F, -2.05F, -5.975F, 3.85F, 4.1F, 4.125F, new CubeDeformation(-1.75F))
		.texOffs(89, 105).addBox(-7.6F, -2.05F, -6.725F, 3.85F, 4.1F, 4.125F, new CubeDeformation(-1.75F))
		.texOffs(89, 105).addBox(-7.6F, -2.05F, -7.5F, 3.85F, 4.1F, 4.125F, new CubeDeformation(-1.75F))
		.texOffs(89, 105).addBox(-7.6F, -2.05F, -5.225F, 3.85F, 4.1F, 4.125F, new CubeDeformation(-1.75F)), PartPose.offsetAndRotation(-17.2F, -1.9F, 11.375F, 0.0F, -1.5708F, 0.0F));

		PartDefinition R = bone.addOrReplaceChild("R", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, 0.0F));

		PartDefinition r_lamp = R.addOrReplaceChild("r_lamp", CubeListBuilder.create().texOffs(112, 120).addBox(2.75F, 4.0F, 1.375F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(0, 105).addBox(1.75F, 4.0F, 1.375F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(0, 105).addBox(0.75F, 4.0F, 1.375F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(89, 105).addBox(5.2F, 3.9F, 1.35F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(0, 105).addBox(-9.475F, 4.0F, 1.375F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(0, 105).addBox(-8.475F, 4.0F, 1.375F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(112, 120).addBox(-7.475F, 4.0F, 1.375F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(40, 35).addBox(-6.575F, 4.05F, 1.325F, 3.85F, 4.1F, 4.1F, new CubeDeformation(-1.75F))
		.texOffs(37, 36).addBox(-5.725F, 4.05F, 1.325F, 3.85F, 4.1F, 4.1F, new CubeDeformation(-1.75F))
		.texOffs(89, 105).addBox(-5.025F, 3.9F, 1.35F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(40, 35).addBox(3.65F, 4.05F, 1.325F, 3.85F, 4.1F, 4.1F, new CubeDeformation(-1.75F))
		.texOffs(37, 36).addBox(4.5F, 4.05F, 1.325F, 3.85F, 4.1F, 4.1F, new CubeDeformation(-1.75F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition 1_6_r1 = r_lamp.addOrReplaceChild("1_6_r1", CubeListBuilder.create().texOffs(33, 100).addBox(-2.025F, -2.05F, -0.175F, 3.85F, 4.1F, 4.125F, new CubeDeformation(-1.75F))
		.texOffs(40, 80).addBox(-4.975F, -2.05F, -2.975F, 3.85F, 4.1F, 4.125F, new CubeDeformation(-1.75F))
		.texOffs(112, 111).addBox(-3.75F, -2.05F, -3.0F, 3.85F, 4.1F, 4.125F, new CubeDeformation(-1.75F))
		.texOffs(45, 80).addBox(-4.375F, -2.05F, -2.675F, 3.85F, 4.1F, 4.125F, new CubeDeformation(-1.75F))
		.texOffs(33, 100).addBox(-2.025F, -2.05F, -2.3F, 3.85F, 4.1F, 4.125F, new CubeDeformation(-1.75F)), PartPose.offsetAndRotation(0.575F, 6.1F, 3.375F, 0.0F, -1.5708F, 0.0F));

		PartDefinition 3_4_r1 = r_lamp.addOrReplaceChild("3_4_r1", CubeListBuilder.create().texOffs(45, 80).addBox(-1.925F, -2.05F, -2.0625F, 3.85F, 4.1F, 4.125F, new CubeDeformation(-1.75F)), PartPose.offsetAndRotation(1.8125F, 6.1F, 0.925F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rb1 = R.addOrReplaceChild("rb1", CubeListBuilder.create().texOffs(9, 49).addBox(-6.25F, -3.85F, 2.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(9, 49).addBox(-4.0F, -3.85F, 2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(9, 49).addBox(-4.75F, -3.85F, 2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(9, 49).addBox(-5.5F, -3.85F, 2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(9, 49).addBox(-6.25F, -3.85F, 2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(9, 49).addBox(-5.5F, -3.85F, 2.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(9, 49).addBox(-4.75F, -3.85F, 2.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(9, 49).addBox(-4.0F, -3.85F, 2.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(0, 66).addBox(-7.25F, -3.85F, 2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(0, 87).addBox(-7.25F, -3.85F, 3.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(96, 94).addBox(-7.25F, -3.85F, 3.875F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(96, 94).addBox(-17.125F, -3.85F, 3.875F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F)), PartPose.offset(8.0F, 8.0F, -8.0F));

		PartDefinition 7_8_r1 = rb1.addOrReplaceChild("7_8_r1", CubeListBuilder.create().texOffs(89, 105).addBox(-7.6F, -2.05F, -5.975F, 3.85F, 4.1F, 4.125F, new CubeDeformation(-1.75F))
		.texOffs(89, 105).addBox(-7.6F, -2.05F, -6.725F, 3.85F, 4.1F, 4.125F, new CubeDeformation(-1.75F))
		.texOffs(89, 105).addBox(-7.6F, -2.05F, -7.5F, 3.85F, 4.1F, 4.125F, new CubeDeformation(-1.75F))
		.texOffs(89, 105).addBox(-7.6F, -2.05F, -5.225F, 3.85F, 4.1F, 4.125F, new CubeDeformation(-1.75F)), PartPose.offsetAndRotation(-7.425F, -1.9F, 11.375F, 0.0F, -1.5708F, 0.0F));

		PartDefinition R_r = R.addOrReplaceChild("R_r", CubeListBuilder.create().texOffs(92, 34).addBox(-7.625F, -5.95F, 3.875F, 9.0F, 8.25F, 9.0F, new CubeDeformation(-3.375F))
		.texOffs(96, 52).addBox(-7.875F, -5.95F, 3.875F, 7.0F, 8.25F, 9.0F, new CubeDeformation(-3.375F))
		.texOffs(98, 17).addBox(-8.125F, -5.95F, 4.375F, 7.0F, 8.25F, 8.0F, new CubeDeformation(-3.375F))
		.texOffs(96, 52).addBox(-5.375F, -5.95F, 3.875F, 7.0F, 8.25F, 9.0F, new CubeDeformation(-3.375F))
		.texOffs(68, 17).mirror().addBox(-5.125F, -5.95F, 4.375F, 7.0F, 8.25F, 8.0F, new CubeDeformation(-3.375F)).mirror(false)
		.texOffs(60, 36).addBox(-7.625F, -5.95F, 6.125F, 9.0F, 8.25F, 7.0F, new CubeDeformation(-3.375F))
		.texOffs(66, 54).addBox(-7.125F, -5.95F, 6.375F, 8.0F, 8.25F, 7.0F, new CubeDeformation(-3.375F))
		.texOffs(96, 87).mirror().addBox(-7.625F, -5.95F, 3.625F, 9.0F, 8.25F, 7.0F, new CubeDeformation(-3.375F)).mirror(false)
		.texOffs(98, 71).mirror().addBox(-7.125F, -5.95F, 3.375F, 8.0F, 8.25F, 7.0F, new CubeDeformation(-3.375F)).mirror(false), PartPose.offset(8.0F, 7.975F, -7.875F));

		PartDefinition R_r2 = R_r.addOrReplaceChild("R_r2", CubeListBuilder.create().texOffs(92, 34).addBox(-7.625F, -5.95F, 3.875F, 9.0F, 8.25F, 9.0F, new CubeDeformation(-3.375F))
		.texOffs(96, 52).addBox(-7.875F, -5.95F, 3.875F, 7.0F, 8.25F, 9.0F, new CubeDeformation(-3.375F))
		.texOffs(98, 17).addBox(-8.125F, -5.95F, 4.375F, 7.0F, 8.25F, 8.0F, new CubeDeformation(-3.375F))
		.texOffs(96, 52).addBox(-5.375F, -5.95F, 3.875F, 7.0F, 8.25F, 9.0F, new CubeDeformation(-3.375F))
		.texOffs(68, 17).mirror().addBox(-5.125F, -5.95F, 4.375F, 7.0F, 8.25F, 8.0F, new CubeDeformation(-3.375F)).mirror(false)
		.texOffs(60, 36).addBox(-7.625F, -5.95F, 6.125F, 9.0F, 8.25F, 7.0F, new CubeDeformation(-3.375F))
		.texOffs(66, 54).addBox(-7.125F, -5.95F, 6.375F, 8.0F, 8.25F, 7.0F, new CubeDeformation(-3.375F))
		.texOffs(96, 87).mirror().addBox(-7.625F, -5.95F, 3.625F, 9.0F, 8.25F, 7.0F, new CubeDeformation(-3.375F)).mirror(false)
		.texOffs(98, 71).mirror().addBox(-7.125F, -5.95F, 3.375F, 8.0F, 8.25F, 7.0F, new CubeDeformation(-3.375F)).mirror(false), PartPose.offset(-9.75F, 0.0F, 0.0F));

		PartDefinition R_v = R.addOrReplaceChild("R_v", CubeListBuilder.create().texOffs(54, 59).addBox(-2.125F, -3.5F, 4.375F, 3.0F, 2.75F, 2.5F, new CubeDeformation(-1.125F))
		.texOffs(44, 60).addBox(-2.125F, -3.275F, 4.125F, 3.0F, 2.5F, 2.5F, new CubeDeformation(-1.125F))
		.texOffs(44, 56).addBox(-2.125F, -3.275F, 4.625F, 3.0F, 2.5F, 2.5F, new CubeDeformation(-1.125F)), PartPose.offset(7.5F, 8.0F, -9.525F));

		PartDefinition whole = bone.addOrReplaceChild("whole", CubeListBuilder.create().texOffs(0, 88).addBox(-24.0F, -9.0F, -5.0F, 32.0F, 15.0F, 25.0F, new CubeDeformation(-8.0F)), PartPose.offset(8.0F, 0.0F, -8.0F));

		PartDefinition support = whole.addOrReplaceChild("support", CubeListBuilder.create().texOffs(0, 61).addBox(-16.0F, -1.0F, -2.0F, 16.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.0F));

		PartDefinition backoblique_r1 = support.addOrReplaceChild("backoblique_r1", CubeListBuilder.create().texOffs(0, 65).addBox(-16.0F, -1.0F, 0.0F, 16.0F, 1.0F, 1.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition group = support.addOrReplaceChild("group", CubeListBuilder.create().texOffs(0, 61).addBox(-16.0F, -1.0F, 5.0F, 16.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition headoblique_r1 = group.addOrReplaceChild("headoblique_r1", CubeListBuilder.create().texOffs(0, 65).addBox(-16.0F, -0.6173F, -2.9239F, 15.75F, 1.0F, 1.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.7362F, 6.2774F, -0.7854F, 0.0F, 0.0F));

		PartDefinition M = bone.addOrReplaceChild("M", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition M_bt = M.addOrReplaceChild("M_bt", CubeListBuilder.create().texOffs(34, 59).addBox(-0.725F, 4.4F, -5.625F, 2.5F, 2.75F, 3.0F, new CubeDeformation(-1.125F)), PartPose.offset(0.0F, -6.0F, 0.0F));

		PartDefinition mh = M.addOrReplaceChild("mh", CubeListBuilder.create().texOffs(35, 21).addBox(-16.0F, -2.75F, 9.925F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(33, 25).addBox(-14.5F, -2.75F, 9.9F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(23, 25).addBox(-13.75F, -2.75F, 9.9F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(35, 21).addBox(-15.25F, -2.75F, 9.9F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F)), PartPose.offset(8.0F, 2.0F, -8.0F));

		PartDefinition m_v = M.addOrReplaceChild("m_v", CubeListBuilder.create().texOffs(54, 59).addBox(-1.25F, 4.4F, -2.975F, 3.0F, 2.75F, 2.5F, new CubeDeformation(-1.125F))
		.texOffs(54, 59).addBox(-2.25F, 4.4F, -2.975F, 3.0F, 2.75F, 2.5F, new CubeDeformation(-1.125F)), PartPose.offset(0.0F, -6.0F, 0.0F));

		PartDefinition m_lamp = M.addOrReplaceChild("m_lamp", CubeListBuilder.create().texOffs(16, 71).addBox(-0.5F, 4.025F, -5.225F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.75F)), PartPose.offset(0.0F, -6.0F, 0.0F));

		PartDefinition m_knob = M.addOrReplaceChild("m_knob", CubeListBuilder.create().texOffs(111, 9).addBox(-10.875F, -4.55F, 8.3F, 4.0F, 4.3F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(111, 0).addBox(-10.875F, -4.55F, 7.125F, 4.0F, 4.3F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(111, 0).addBox(-10.875F, -4.55F, 6.375F, 4.0F, 4.3F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(111, 0).addBox(-11.875F, -4.55F, 6.1F, 4.0F, 4.3F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(111, 0).addBox(-10.875F, -4.55F, 5.575F, 4.0F, 4.3F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(111, 0).addBox(-11.875F, -4.55F, 4.725F, 4.0F, 4.3F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(111, 0).addBox(-8.5F, -4.55F, 4.725F, 4.0F, 4.3F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(111, 0).addBox(-9.625F, -4.55F, 6.375F, 4.0F, 4.3F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(111, 0).addBox(-9.625F, -4.55F, 7.125F, 4.0F, 4.3F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(111, 0).addBox(-8.475F, -4.55F, 8.3F, 4.0F, 4.3F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(111, 9).addBox(-9.625F, -4.55F, 8.3F, 4.0F, 4.3F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(111, 9).addBox(-10.25F, -4.55F, 9.35F, 4.0F, 4.3F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(111, 0).addBox(-9.625F, -4.55F, 5.575F, 4.0F, 4.3F, 4.0F, new CubeDeformation(-1.75F)), PartPose.offset(8.0F, 2.0F, -8.0F));

		PartDefinition m_bl = M.addOrReplaceChild("m_bl", CubeListBuilder.create().texOffs(0, 97).addBox(-7.25F, -3.751F, 9.675F, 4.0F, 3.525F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(4, 97).addBox(-6.25F, -3.751F, 9.675F, 4.0F, 3.525F, 4.0F, new CubeDeformation(-1.75F))
		.texOffs(4, 97).addBox(-8.5F, -3.751F, 3.25F, 4.0F, 3.525F, 4.0F, new CubeDeformation(-1.75F)), PartPose.offset(8.0F, 2.0F, -8.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}