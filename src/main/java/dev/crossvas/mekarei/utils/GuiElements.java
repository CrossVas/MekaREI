package dev.crossvas.mekarei.utils;

import dev.crossvas.mekarei.MekaREI;
import net.minecraft.resources.ResourceLocation;

public enum GuiElements {
    DEFAULT(12, 0, 18, 18),
    DEFAULT_DARK(30, 0, 18, 18),
    INPUT(12, 18, 18, 18),
    INPUT_EXTRA(12, 36, 18, 18),
    OUTPUT(30, 18, 18, 18),
    OUTPUT_EXTRA(30, 36, 18, 18),
    CHEM_BAR(0, 0, 6, 54),
    TANK_OVERLAY_DEFAULT(49, 1, 16, 58),
    TANK_DEFAULT(66, 0, 18, 60),
    TANK_DEFAULT_BLUE(84, 0, 18, 60),
    TANK_DEFAULT_ORANGE(102, 0, 18, 60),
    TANK_DEFAULT_RED(120, 0, 18, 60),
    TANK_DEFAULT_AQUA(138, 0, 18, 60),
    TANK_DEFAULT_YELLOW(156, 0, 18, 60),
    TANK_OVERLAY_MIDDLE(49, 61, 16, 46),
    TANK_MIDDLE(66, 60, 18, 48),
    TANK_MIDDLE_BLUE(84, 60, 18, 48),
    TANK_MIDDLE_ORANGE(102, 60, 18, 48),
    TANK_MIDDLE_RED(120, 60, 18, 48),
    TANK_MIDDLE_AQUA(138, 60, 18, 48),
    TANK_MIDDLE_YELLOW(156, 60, 18, 48),
    TANK_OVERLAY_SMALL(49, 109, 16, 28),
    TANK_SMALL(66, 108, 18, 30),
    TANK_SMALL_BLUE(84, 108, 18, 30),
    TANK_SMALL_ORANGE(102, 108, 18, 30),
    TANK_SMALL_RED(120, 108, 18, 30),
    TANK_SMALL_AQUA(138, 108, 18, 30),
    TANK_SMALL_YELLOW(156, 108, 18, 30),
    CHEMICAL_INFUSION_TANK(0, 54, 8, 14),
    COMPONENT_ARROW_UP(8, 54, 8, 10);

    public final int x;
    public final int y;
    public final int width;
    public final int height;
    public final ResourceLocation texture;

    GuiElements(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texture = new ResourceLocation(MekaREI.ID, "textures/gui/elements.png");
    }
}
