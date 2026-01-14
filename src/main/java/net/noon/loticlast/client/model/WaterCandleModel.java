package net.noon.loticlast.client.model;

import net.minecraft.resources.ResourceLocation;
import net.noon.loticlast.Loticlast;
import net.noon.loticlast.entity.custom.disperser.WaterCandle;
import software.bernie.geckolib.model.GeoModel;

public class WaterCandleModel extends GeoModel<WaterCandle> {

    @Override
    public ResourceLocation getModelResource(WaterCandle animatable) {
        return new ResourceLocation(Loticlast.MOD_ID, "geo/entity/water_candle.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WaterCandle animatable) {

        return new ResourceLocation(Loticlast.MOD_ID, "textures/entity/water_candle.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WaterCandle animatable) {
        return new ResourceLocation(Loticlast.MOD_ID, "animations/water_candle.animation.json");
    }
}