package net.noon.loticlast.client.render.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.noon.loticlast.Loticlast;
import net.noon.loticlast.client.model.WaterCandleModel;
import net.noon.loticlast.entity.custom.disperser.WaterCandle;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WaterCandleRenderer extends GeoEntityRenderer<WaterCandle> {

    public WaterCandleRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new WaterCandleModel());
        this.shadowRadius = 0.8F;
    }

    @Override
    public ResourceLocation getTextureLocation(WaterCandle animatable) {
        // 覆盖默认纹理获取，使用模型类中的逻辑
        ResourceLocation texture = this.model.getTextureResource(animatable);
        return texture != null ? texture :
                new ResourceLocation(Loticlast.MOD_ID, "textures/entity/water_candle.png");
    }

}