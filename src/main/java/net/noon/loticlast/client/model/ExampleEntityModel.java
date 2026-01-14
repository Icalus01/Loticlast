package net.noon.loticlast.client.model;

import net.minecraft.resources.ResourceLocation;
import net.noon.loticlast.Loticlast;
import net.noon.loticlast.entity.custom.ExampleEntity;
import software.bernie.geckolib.model.GeoModel;

public class ExampleEntityModel extends GeoModel<ExampleEntity> {
    // 模型文件路径
    @Override
    public ResourceLocation getModelResource(ExampleEntity entity) {
        return new ResourceLocation(Loticlast.MOD_ID, "geo/entity/alligator.geo.json");
    }

    // 纹理文件路径（支持多纹理变体）
    @Override
    public ResourceLocation getTextureResource(ExampleEntity entity) {
        // 可根据实体数据返回不同纹理
        if (entity.hasCustomName() && "Special".equals(entity.getCustomName().getString())) {
            return new ResourceLocation(Loticlast.MOD_ID, "textures/entity/example_entity_special.png");
        }
        return new ResourceLocation(Loticlast.MOD_ID, "textures/entity/alligator.png");
    }

    // 动画文件路径
    @Override
    public ResourceLocation getAnimationResource(ExampleEntity entity) {
        return new ResourceLocation(Loticlast.MOD_ID, "animations/alligator.rp_anim.json");
    }

}