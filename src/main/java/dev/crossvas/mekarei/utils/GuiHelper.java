package dev.crossvas.mekarei.utils;

import com.google.common.base.CaseFormat;
import com.mojang.blaze3d.systems.RenderSystem;
import me.shedaniel.math.Dimension;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.impl.client.gui.widget.EntryWidget;
import net.minecraft.network.chat.Component;

import java.util.List;

public class GuiHelper {

    public static void createRecipeBase(List<Widget> list, Rectangle bounds) {
        list.add(Widgets.createRecipeBase(bounds));
    }

    public static void addProgressBar(List<Widget> list, Point point, double animationTime, GuiProgress type) {
        list.add(createProgressBar(point.getX(), point.getY(), animationTime, type));
    }

    private static Widget createProgressBar(int x, int y, double animationTime, GuiProgress type) {
        return Widgets.createDrawableWidget((helper, matrices, mouseX, mouseY, delta) -> {
            RenderSystem.setShaderTexture(0, type.texture);
            helper.blit(matrices, x, y, type.x, type.y, type.width, type.height);
            int i = (int) ((System.currentTimeMillis() / animationTime) % 1.0 * type.width);
            if (i < 0) {
                i = 0;
            }
            if (CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, type.name()).contains("left")) {
                helper.blit(matrices, x + type.width - i, y, type.xActive + type.width - i, type.yActive, i, type.height);
            } else {
                helper.blit(matrices, x, y, type.xActive, type.yActive, i, type.height);
            }
        });
    }

    public static void addGuiElement(List<Widget> list, GuiElements element, Point point) {
        list.add(Widgets.createTexturedWidget(element.texture, point.getX(), point.getY(), element.x, element.y, element.width, element.height));
    }

    public static void addInputSlotElement(List<Widget> list, Point point, EntryIngredient entry, GuiElements element) {
        Widget slotBackground = Widgets.createTexturedWidget(element.texture, point.getX() - 1, point.getY() - 1, element.x, element.y, element.width, element.height);
        Slot slot = Widgets.createSlot(point).entries(entry).markInput();
        slot.setBackgroundEnabled(false);
        list.add(slotBackground);
        list.add(slot);
    }

    public static void addOutputSlotElement(List<Widget> list, Point point, EntryIngredient entry, GuiElements element) {
        Widget slotBackground = Widgets.createTexturedWidget(element.texture, point.x - 1, point.y - 1, element.x, element.y, element.width, element.height, 256, 256);
        Slot slot = Widgets.createSlot(point).entries(entry).markOutput();
        slot.setBackgroundEnabled(false);
        list.add(slotBackground);
        list.add(slot);
    }

    public static void addTank(List<Widget> list, Point point, EntryIngredient fluid, GuiElements tank, GuiElements overlay) {
        // tank base
        list.add(Widgets.createTexturedWidget(tank.texture, point.x, point.y, tank.x, tank.y, tank.width, tank.height));
        list.add(new EntryWidget(new Rectangle(point, new Dimension(tank.width, tank.height))).entries(fluid).disableBackground().disableHighlight());
        // tank overlay
        list.add(Widgets.createTexturedWidget(overlay.texture, point.x + 1, point.y + 1, overlay.x, overlay.y, overlay.width, overlay.height));
    }

    public static void addEntryWidget(List<Widget> list, Point point, EntryIngredient entry, GuiElements element) {
        Widget widget = new EntryWidget(new Rectangle(point, new Dimension(element.width, element.height))).entries(entry).disableBackground();
        list.add(widget);
    }

    public static void addLabel(List<Widget> widgetList, Point point, Component text) {
        widgetList.add(Widgets.createLabel(point, text).shadow(false));
    }

    public static void addLabelRight(List<Widget> widgetList, Point point, Component text) {
        widgetList.add(Widgets.createLabel(point, text).shadow(false).rightAligned());
    }

    public static void addLabelLeft(List<Widget> widgetList, Point point, Component text) {
        widgetList.add(Widgets.createLabel(point, text).shadow(false).leftAligned());
    }
}
