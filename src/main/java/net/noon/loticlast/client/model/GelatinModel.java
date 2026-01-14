package net.noon.loticlast.client.model;

import net.minecraft.resources.ResourceLocation;
import net.noon.loticlast.Loticlast;
import net.noon.loticlast.entity.custom.gelatin.Gelatin;
import software.bernie.geckolib.model.GeoModel;

public class GelatinModel extends GeoModel<Gelatin> {

    @Override
    public ResourceLocation getModelResource(Gelatin animatable) {
        return new ResourceLocation(Loticlast.MOD_ID, "geo/entity/gelatin.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Gelatin animatable) {

        return new ResourceLocation(Loticlast.MOD_ID, "textures/entity/gelatin/gelatin.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Gelatin animatable) {
        return new ResourceLocation(Loticlast.MOD_ID, "animations/gelatin.animation.json");
    }
}