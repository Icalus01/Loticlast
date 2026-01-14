package net.noon.loticlast.client.render.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.noon.loticlast.Loticlast;
import net.noon.loticlast.client.model.GelatinModel;
import net.noon.loticlast.entity.custom.gelatin.Gelatin;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GelatinRenderer extends GeoEntityRenderer<Gelatin> {

    public GelatinRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new GelatinModel());
        this.shadowRadius = 0.8F;
    }

    @Override
    public ResourceLocation getTextureLocation(Gelatin animatable) {
        // 覆盖默认纹理获取，使用模型类中的逻辑
        ResourceLocation texture = this.model.getTextureResource(animatable);
        return texture != null ? texture :
                new ResourceLocation(Loticlast.MOD_ID, "textures/entity/gelatin.png");
    }

    @Override
    protected float getDeathMaxRotation(Gelatin animatable) {
        return 0.0F; // 死亡时不旋转
    }
}