package net.noon.loticlast.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.noon.loticlast.client.model.ExampleEntityModel;
import net.noon.loticlast.entity.custom.ExampleEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ExampleEntityRenderer extends GeoEntityRenderer<ExampleEntity> {

    public ExampleEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ExampleEntityModel());

        // 配置渲染参数
        this.shadowRadius = 0.5f; // 阴影大小
        this.scaleHeight = 1.0f;  // 高度缩放
        this.scaleWidth = 1.0f;   // 宽度缩放

        // 添加图层渲染（可选）
        // addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    // 可选：自定义渲染逻辑
    @Override
    public void preRender(PoseStack poseStack, ExampleEntity animatable,
                          BakedGeoModel model, MultiBufferSource bufferSource,
                          VertexConsumer buffer, boolean isReRender,
                          float partialTick, int packedLight, int packedOverlay,
                          float red, float green, float blue, float alpha) {

        super.preRender(poseStack, animatable, model, bufferSource, buffer,
                isReRender, partialTick, packedLight, packedOverlay,
                red, green, blue, alpha);

        // 示例：随时间轻微浮动
        if (!isReRender) {
            float time = animatable.tickCount + partialTick;
            float offset = (float) Math.sin(time * 0.1f) * 0.05f;
            poseStack.translate(0, offset, 0);
        }
    }

    // 可选：自定义渲染类型（如透明、发光等）
    @Override
    public RenderType getRenderType(ExampleEntity animatable,
                                    ResourceLocation texture,
                                    MultiBufferSource bufferSource,
                                    float partialTick) {

        // 半透明渲染
        // return RenderType.entityTranslucent(texture);

        // 发光渲染（需要发光纹理）
        // return RenderType.eyes(texture);

        // 默认实体渲染
        return RenderType.entityCutout(texture);
    }


    // 可选：旋转模型以匹配原版生物方向
    @Override
    protected void applyRotations(ExampleEntity entity, PoseStack poseStack,
                                  float ageInTicks, float rotationYaw,
                                  float partialTicks) {
        super.applyRotations(entity, poseStack, ageInTicks, rotationYaw, partialTicks);

        // 例如：如果实体是倒下的（如蜘蛛）
        // if (entity.getPose() == Pose.SLEEPING) {
        //     poseStack.translate(0, 0.1f, 0);
        // }
    }
}
