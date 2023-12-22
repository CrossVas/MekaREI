package dev.crossvas.mekarei.categories;

import dev.crossvas.mekarei.displays.PigmentMixerDisplay;
import dev.crossvas.mekarei.utils.*;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import mekanism.common.registries.MekanismBlocks;
import net.minecraft.network.chat.Component;

import java.util.List;

public class PigmentMixerCategory implements DisplayCategory<PigmentMixerDisplay>, IGuiHelper {

    @Override
    public CategoryIdentifier<? extends PigmentMixerDisplay> getCategoryIdentifier() {
        return Categories.PIGMENT_MIXER;
    }

    @Override
    public Component getTitle() {
        return MekanismBlocks.PIGMENT_MIXER.getTextComponent();
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MekanismBlocks.PIGMENT_MIXER.getBlock());
    }

    @Override
    public List<Widget> setupDisplay(PigmentMixerDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ObjectArrayList<>();
        GuiHelper.createRecipeBase(widgets, bounds);
        GuiHelper.addTank(widgets, point(bounds.getMinX() + offset, bounds.getMinY() + offset), MekanismEntryTypes.pigments(display.recipe().getLeftInput().getRepresentations()), GuiElements.TANK_DEFAULT_RED, GuiElements.TANK_OVERLAY_DEFAULT);
        GuiHelper.addTank(widgets, point(bounds.getMaxX() - slotSize - offset, bounds.getMinY() + offset), MekanismEntryTypes.pigments(display.recipe().getRightInput().getRepresentations()), GuiElements.TANK_DEFAULT_ORANGE, GuiElements.TANK_OVERLAY_DEFAULT);
        GuiHelper.addTank(widgets, point(bounds.getCenterX() - slotSize / 2, bounds.getMinY() + offset), MekanismEntryTypes.pigments(display.recipe().getOutputDefinition()), GuiElements.TANK_DEFAULT_BLUE, GuiElements.TANK_OVERLAY_DEFAULT);

        GuiHelper.addProgressBar(widgets, point(bounds.getMinX() + offset + slotSize + offset, bounds.getCenterY() - 4), 2000, GuiProgress.ARROW_SMALL_RIGHT);
        GuiHelper.addProgressBar(widgets, point(bounds.getMaxX() - offset - slotSize - offset - GuiProgress.ARROW_SMALL_LEFT.width, bounds.getCenterY() - 4), 2000, GuiProgress.ARROW_SMALL_LEFT);
        return widgets;
    }

    @Override
    public int getDisplayWidth(PigmentMixerDisplay display) {
        return 140;
    }

    @Override
    public int getDisplayHeight() {
        return 70;
    }
}
