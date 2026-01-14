package net.noon.loticlast.core.inner;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.noon.loticlast.Loticlast;

import java.util.Map;

public class InnerScreen extends Screen {

    private static final int PADDING = 20;

    // 自定义内容管理
    private final Map<String, MovableElement> movableElements = Maps.newHashMap();
    private MovableElement selectedElement = null;
    private int scrollX = 0, scrollY = 0;
    private boolean isDragging = false;
    private int dragStartX, dragStartY;

    public InnerScreen() {
        super(GameNarrator.NO_TITLE);
    }

    @Override
    protected void init() {
        super.init();

        // 添加一些测试用的可移动元素
        movableElements.clear();

        // 示例图片1
        movableElements.put("test1", new MovableElement(
                new ResourceLocation(Loticlast.MOD_ID, "textures/gui/window.png"),
                100, 100, 64, 64
        ));

        // 示例图片2
        movableElements.put("test2", new MovableElement(
                new ResourceLocation(Loticlast.MOD_ID, "textures/gui/window.png"),
                200, 200, 64, 64
        ));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        // 渲染暗色半透明背景
        this.renderBackground(graphics);

        // 计算窗口位置和大小（占据屏幕的85%）
        int windowWidth = (int) (this.width * 0.85);
        int windowHeight = (int) (this.height * 0.85);
        int windowX = (this.width - windowWidth) / 2;
        int windowY = (this.height - windowHeight) / 2;

        CoordinateLines lines = new CoordinateLines(this.width, this.height, 0.05f);

        int left = lines.left;
        int top = lines.top;
        int right = lines.right;
        int bottom = lines.bottom;
        int innerLeft = left + 7;
        int innerTop = top + 16;
        int innerRight = right - 7;
        int innerBottom = bottom - 7;

        // 渲染背景
        ResourceLocation bgTexture = new ResourceLocation(Loticlast.MOD_ID, "textures/gui/stone.png");
        drawBgTexture(bgTexture, graphics, lines);

        RenderSystem.enableBlend();

        // 把图片分割，九宫格
        ResourceLocation texture = new ResourceLocation(Loticlast.MOD_ID, "textures/gui/window.png");
        // 图片的大小为 256 x 256 , 32x32 分割
        // 0 - 255

        drawBorder(texture, graphics);

        //开启裁剪
        graphics.enableScissor(innerLeft, innerTop, innerRight - innerLeft, innerBottom - innerTop);

        // 渲染所有可移动元素（应用滚动偏移）
        for (MovableElement element : movableElements.values()) {
            element.render(graphics,
                    windowX + PADDING + element.x + scrollX,
                    windowY + PADDING + 20 + element.y + scrollY,
                    mouseX, mouseY);
        }

//        graphics.disableScissor();

        // 显示提示信息
        if (selectedElement != null) {
            graphics.drawString(this.font,
                    Component.literal("拖动元素: " + selectedElement.texture.toString()),
                    windowX + 10, windowY + windowHeight - 30,
                    0xFFFFFF);
        } else {
            graphics.drawString(this.font,
                    Component.literal("按住左键拖动图片 | 鼠标滚轮滚动"),
                    windowX + 10, windowY + windowHeight - 30,
                    0xAAAAAA);
        }

        super.render(graphics, mouseX, mouseY, partialTick);

    }

    private void drawBorder(ResourceLocation texture, GuiGraphics graphics) {
        // 占据屏幕的90%
        int left = (int) (this.width * 0.05);
        int top = (int) (this.height * 0.05);
        int right = this.width - left;
        int bottom = this.height - top;
        int innerLeft = left + 32;
        int innerTop = top + 32;
        int innerRight = right - 32;
        int innerBottom = bottom - 32;


        //绘制角：左上，右上，左下，右下
        graphics.blit(texture, left, top, 0, 0, 32, 32, 256, 256);
        graphics.blit(texture, innerRight, top, 224, 0, 32, 32, 256, 256);
        graphics.blit(texture, left, innerBottom, 0, 112, 32, 32, 256, 256);
        graphics.blit(texture, innerRight, innerBottom, 224, 112, 32, 32, 256, 256);
        //计算边数
        int xSide = (int) ((innerRight - innerLeft) / 32.0);
        int ySide = (int) ((innerBottom - innerTop) / 32.0);
        //计算空缺
        int xGapLoc = innerLeft + xSide * 32;
        int xGapLen = innerRight - xGapLoc;
        int yGapLoc = innerTop + ySide * 32;
        int yGapLen = innerBottom - yGapLoc;
        //绘制边
        for (int i = 0; i < xSide; i++) {
            // 顶边
            graphics.blit(texture, innerLeft + 32 * i, top, 32, 0, 32, 32, 256, 256);
            // 底边
            graphics.blit(texture, innerLeft + 32 * i, innerBottom, 32, 112, 32, 32, 256, 256);
        }
        for (int i = 0; i < ySide; i++) {
            // 左边
            graphics.blit(texture, left, innerTop + 32 * i, 0, 32, 32, 32, 256, 256);
            // 右边
            graphics.blit(texture, innerRight, innerTop + 32 * i, 224, 32, 32, 32, 256, 256);
        }
        // 绘制空缺
        graphics.blit(texture, xGapLoc, top, 32, 0, xGapLen, 32, 256, 256);
        graphics.blit(texture, xGapLoc, innerBottom, 32, 112, xGapLen, 32, 256, 256);
        graphics.blit(texture, left, yGapLoc, 0, 32, 32, yGapLen, 256, 256);
        graphics.blit(texture, innerRight, yGapLoc, 224, 32, 32, yGapLen, 256, 256);

    }

    private void drawBgTexture(ResourceLocation texture, GuiGraphics graphics, CoordinateLines lines) {
        int innerLeft = lines.left + 7;
        int innerTop = lines.top + 7;
        int innerRight = lines.right - 7;
        int innerBottom = lines.bottom - 7;

        // 计算 x，y上的个数
        int xCount = (int) ((innerRight - innerLeft) / 16.0 + 0.5);
        int yCount = (int) ((innerBottom - innerTop) / 16.0 + 0.5);
        // 绘制
        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                graphics.blit(texture, innerLeft + i * 16, innerTop + j * 16, 0, 0, 16, 16, 16, 16);
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) { // 左键
            int windowWidth = (int) (this.width * 0.85);
            int windowHeight = (int) (this.height * 0.85);
            int windowX = (this.width - windowWidth) / 2;
            int windowY = (this.height - windowHeight) / 2;

            // 计算窗口内坐标
            int innerX = (int) mouseX - windowX - PADDING - scrollX;
            int innerY = (int) mouseY - windowY - PADDING - 20 - scrollY;

            // 检查是否点击了任何元素
            for (MovableElement element : movableElements.values()) {
                if (element.isMouseOver(innerX, innerY)) {
                    selectedElement = element;
                    dragStartX = (int) mouseX;
                    dragStartY = (int) mouseY;
                    isDragging = true;
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            isDragging = false;
            selectedElement = null;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (button == 0 && isDragging && selectedElement != null) {
            // 拖动选中的元素
            int deltaX = (int) mouseX - dragStartX;
            int deltaY = (int) mouseY - dragStartY;
            selectedElement.x += deltaX;
            selectedElement.y += deltaY;
            dragStartX = (int) mouseX;
            dragStartY = (int) mouseY;
            return true;
        } else if (button == 1 || button == 2) {
            // 右键或中键拖动滚动
            scrollX += dragX;
            scrollY += dragY;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        // 鼠标滚轮滚动
        scrollY += delta * 20;
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // ESC键关闭界面
        if (keyCode == 256) { // ESC
            this.onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    // 可移动元素类
    private static class MovableElement {
        private final ResourceLocation texture;
        private int x, y;
        private final int width, height;

        public MovableElement(ResourceLocation texture, int x, int y, int width, int height) {
            this.texture = texture;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public void render(GuiGraphics guiGraphics, int screenX, int screenY, int mouseX, int mouseY) {
            // 渲染图片
            guiGraphics.blit(texture, screenX, screenY, 0, 0, width, height, width, height);

            // 鼠标悬停时显示边框
            if (isMouseOver(mouseX - (screenX - x), mouseY - (screenY - y))) {
//                RenderUtil.drawBorder(guiGraphics, screenX - 1, screenY - 1, width + 2, height + 2, 1, 0xFFFFFF00);
            }
        }

        public boolean isMouseOver(int testX, int testY) {
            return testX >= x && testX <= x + width && testY >= y && testY <= y + height;
        }
    }

    private static class CoordinateLines {
        public int left;
        public int top;
        public int right;
        public int bottom;

        public CoordinateLines(int width, int height, float scale) {
            left = (int) (width * scale);
            top = (int) (height * scale);
            right = width - left;
            bottom = height - top;
        }
    }

}

