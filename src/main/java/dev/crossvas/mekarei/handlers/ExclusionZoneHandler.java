package dev.crossvas.mekarei.handlers;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.registry.screen.ExclusionZonesProvider;
import mekanism.client.gui.GuiMekanism;
import mekanism.client.gui.element.GuiElement;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;

import java.util.Collection;
import java.util.List;

public class ExclusionZoneHandler implements ExclusionZonesProvider<GuiMekanism<?>> {

    @Override
    public Collection<Rectangle> provide(GuiMekanism<?> gui) {
        int parentX = gui.getLeft();
        int parentY = gui.getTop();
        int parentWidth = gui.getWidth();
        int parentHeight = gui.getHeight();
        List<Rectangle> extraAreas = getAreasFor(parentX, parentY, parentWidth, parentHeight, gui.children());
        extraAreas.addAll(getAreasFor(parentX, parentY, parentWidth, parentHeight, gui.getWindows()));
        return extraAreas;
    }

    public static List<Rectangle> getAreasFor(int parentX, int parentY, int parentWidth, int parentHeight, Collection<? extends GuiEventListener> children) {
        List<Rectangle> areas = new ObjectArrayList<>();
        for (GuiEventListener child : children) {
            if (child instanceof AbstractWidget widget && widget.visible) {
                if (areaSticksOut(widget.x, widget.y, widget.getWidth(), widget.getHeight(), parentX, parentY, parentWidth, parentHeight)) {
                    areas.add(new Rectangle(widget.x, widget.y, widget.getWidth(), widget.getHeight()));
                }
                if (widget instanceof GuiElement element) {
                    for (Rectangle grandChildArea : getAreasFor(widget.x, widget.y, widget.getWidth(), widget.getHeight(), element.children())) {
                        if (areaSticksOut(grandChildArea.getX(), grandChildArea.getY(), grandChildArea.getWidth(), grandChildArea.getHeight(),
                                parentX, parentY, parentWidth, parentHeight)) {
                            areas.add(grandChildArea);
                        }
                    }
                }
            }
        }
        return areas;
    }

    private static boolean areaSticksOut(int x, int y, int width, int height, int parentX, int parentY, int parentWidth, int parentHeight) {
        return x < parentX || y < parentY || x + width > parentX + parentWidth || y + height > parentY + parentHeight;
    }
}
