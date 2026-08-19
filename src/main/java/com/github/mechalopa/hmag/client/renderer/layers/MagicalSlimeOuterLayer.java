package com.github.mechalopa.hmag.client.renderer.layers;

import com.github.mechalopa.hmag.client.ModModelLayers;
import com.github.mechalopa.hmag.client.model.MagicalSlimeModel;
import com.github.mechalopa.hmag.client.util.ModClientUtils;
import com.github.mechalopa.hmag.world.entity.MagicalSlimeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MagicalSlimeOuterLayer<T extends LivingEntity> extends RenderLayer<T, MagicalSlimeModel<T>>
{
	private final MagicalSlimeModel<T> model;

	public MagicalSlimeOuterLayer(RenderLayerParent<T, MagicalSlimeModel<T>> renderLayerParent, EntityModelSet modelSet)
	{
		super(renderLayerParent);
		this.model = new MagicalSlimeModel<>(modelSet.bakeLayer(ModModelLayers.MAGICAL_SLIME_OUTER));
		this.model.setOuterLayer(true);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
	{
		Minecraft minecraft = Minecraft.getInstance();
		boolean flag = minecraft.shouldEntityAppearGlowing(livingEntity) && livingEntity.isInvisible();

		if (!livingEntity.isInvisible() || flag)
		{
			VertexConsumer vertexconsumer;

			if (flag)
			{
				vertexconsumer = buffer.getBuffer(RenderType.outline(this.getTextureLocation(livingEntity)));
			}
			else
			{
				vertexconsumer = buffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(livingEntity)));
			}

			this.getParentModel().copyPropertiesTo(this.model);
			this.model.prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTicks);
			this.model.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

			float f = 1.0F;
			float f1 = 1.0F;
			float f2 = 1.0F;

			if (livingEntity instanceof MagicalSlimeEntity)
			{
				if (SlimeGirlOuterLayer.isRainbowName(livingEntity))
				{
					float[] afloat = ModClientUtils.getRainbowColors(livingEntity, partialTicks);
					f = afloat[0];
					f1 = afloat[1];
					f2 = afloat[2];
				}
				else
				{
					f = (((MagicalSlimeEntity)livingEntity).getColors())[0];
					f1 = (((MagicalSlimeEntity)livingEntity).getColors())[1];
					f2 = (((MagicalSlimeEntity)livingEntity).getColors())[2];
				}
			}

			this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, LivingEntityRenderer.getOverlayCoords(livingEntity, 0.0F), f, f1, f2, 1.0F);
		}
	}
}