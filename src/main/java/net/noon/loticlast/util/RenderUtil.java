package net.noon.loticlast.util;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class RenderUtil {


    /*用于绘制尺寸自适应边框，前提：角大小不超过32x32
    * 如果图片有半透明区域，记得开启混合
    * */


    public static void setColor(int color) {
        RenderSystem.setShaderColor(((color >> 16) & 255) / 255F, ((color >> 8) & 255) / 255F, (color & 255) / 255F, 1.0F);
    }
    
    public static void drawRect(double x, double y, double x2, double y2, double width, int color) {
        if (y > y2) {
            double tempY = y;
            double tempX = x;
            y = y2;
            x = x2;
            y2 = tempY;
            x2 = tempX;
        }
        Tesselator tesselator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        RenderSystem.enableBlend();
        // RenderSystem.disableTexture();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderUtil.setColor(color);
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
        boolean xHigh = x < x2;
        bufferbuilder.vertex(x, xHigh ? y + width : y, 0.0D).endVertex();
        bufferbuilder.vertex(x2, xHigh ? y2 + width : y2, 0.0D).endVertex();
        bufferbuilder.vertex(x2 + width, xHigh ? y2 : y2 + width, 0.0D).endVertex();
        bufferbuilder.vertex(x + width, xHigh ? y : y + width, 0.0D).endVertex();
        tesselator.end();
        // RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

}
