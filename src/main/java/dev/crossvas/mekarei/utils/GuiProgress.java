package dev.crossvas.mekarei.utils;

import dev.crossvas.mekarei.MekaREI;
import net.minecraft.resources.ResourceLocation;

public enum GuiProgress {
    ARROW_LARGE_LEFT(49, 25, 49, 33, 48, 8, true),
    ARROW_LARGE_RIGHT(0, 25, 0, 33, 48, 8, true),
    ARROW_NORMAL_RIGHT(0, 0, 0, 8, 32, 8, true),
    ARROW_SMALL_LEFT(49, 50, 49, 58, 28, 8, true),
    ARROW_SMALL_RIGHT(20, 50, 20, 58, 28, 8, true),
    BIDIRECTIONAL(33, 6, 33, 6, 16, 6, false),
    BAR(23, 75, 23, 84, 25, 9, true);

    public final int x;
    public final int y;
    public final int xActive;
    public final int yActive;
    public final int width;
    public final int height;
    public final ResourceLocation texture;
    public final boolean isActive;

    GuiProgress(int x, int y, int xActive, int yActive, int width, int height, boolean active) {
        this.x = x;
        this.y = y;
        this.xActive = xActive;
        this.yActive = yActive;
        this.width = width;
        this.height = height;
        this.texture = new ResourceLocation(MekaREI.ID, "textures/gui/progress.png");
        this.isActive = active;
    }
}
