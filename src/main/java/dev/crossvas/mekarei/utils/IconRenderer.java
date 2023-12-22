package dev.crossvas.mekarei.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.AbstractRenderer;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;

public class IconRenderer extends AbstractRenderer {

    ResourceLocation TEXTURE;
    int uWidth;
    int vHeight;
    int textureWidth;
    int textureHeight;

    public IconRenderer(ResourceLocation texture, int uWidth, int vHeight, int textureWidth, int textureHeight) {
        this.TEXTURE = texture;
        this.uWidth = uWidth;
        this.vHeight = vHeight;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    @Override
    public void render(PoseStack matrices, Rectangle bounds, int mouseX, int mouseY, float delta) {
        RenderSystem.setShaderTexture(0, TEXTURE);
        matrices.pushPose();
        matrices.translate(-1, -1, 0);
        GuiComponent.blit(matrices, bounds.x, bounds.y, 0, 0, 0.0F, uWidth, vHeight, textureHeight, textureWidth);
        matrices.popPose();
    }
}
