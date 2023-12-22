package dev.crossvas.mekarei.categories;

import dev.crossvas.mekarei.displays.ItemStackToEnergyDisplay;
import dev.crossvas.mekarei.utils.*;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import mekanism.common.MekanismLang;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.text.EnergyDisplay;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ItemStackToEnergyCategory implements DisplayCategory<ItemStackToEnergyDisplay>, IGuiHelper {

    @Override
    public CategoryIdentifier<? extends ItemStackToEnergyDisplay> getCategoryIdentifier() {
        return Categories.ITEM_TO_ENERGY;
    }

    @Override
    public Component getTitle() {
        return MekanismLang.CONVERSION_ENERGY.translate();
    }

    @Override
    public Renderer getIcon() {
        return new IconRenderer(MekanismUtils.getResource(MekanismUtils.ResourceType.GUI, "energy.png"), 18, 18, 18, 18);
    }

    @Override
    public List<Widget> setupDisplay(ItemStackToEnergyDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ObjectArrayList<>();
        GuiHelper.createRecipeBase(widgets, bounds);
        GuiHelper.addInputSlotElement(widgets, adjustedInputPoint(bounds), EntryIngredients.ofItemStacks(display.recipe().getInput().getRepresentations()), GuiElements.INPUT);
        GuiHelper.addLabelLeft(widgets, point(adjustedInputPoint(bounds).x + slotSize + innerOffset, adjustedInputPoint(bounds).y + offset),
                Component.literal("→ ").append(EnergyDisplay.of(display.recipe().getOutput(display.recipe().getInput().getRepresentations().get(0))).getTextComponent()).withStyle(ChatFormatting.BLACK));
        return widgets;
    }

    @Override
    public int getDisplayWidth(ItemStackToEnergyDisplay display) {
        return defaultWidth;
    }

    @Override
    public int getDisplayHeight() {
        return 32;
    }
}
