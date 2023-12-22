package dev.crossvas.mekarei.categories;

import dev.crossvas.mekarei.MekaREI;
import dev.crossvas.mekarei.displays.MetallurgicInfuserDisplay;
import dev.crossvas.mekarei.utils.*;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import mekanism.api.chemical.infuse.InfusionStack;
import mekanism.common.registries.MekanismBlocks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class MetallurgicInfuserCategory implements DisplayCategory<MetallurgicInfuserDisplay>, IGuiHelper {

    @Override
    public CategoryIdentifier<? extends MetallurgicInfuserDisplay> getCategoryIdentifier() {
        return Categories.METAL_INFUSING;
    }

    @Override
    public Component getTitle() {
        return MekanismBlocks.METALLURGIC_INFUSER.getTextComponent();
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MekanismBlocks.METALLURGIC_INFUSER.asItem());
    }

    @Override
    public List<Widget> setupDisplay(MetallurgicInfuserDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ObjectArrayList<>();
        GuiHelper.createRecipeBase(widgets, bounds);
        GuiHelper.addGuiElement(widgets, GuiElements.CHEM_BAR, point(bounds.getMinX() + offset, bounds.getMinY() + offset));
        GuiHelper.addEntryWidget(widgets, point(bounds.getMinX() + offset, bounds.getMinY() + offset), MekanismEntryTypes.infusions(display.recipe().getChemicalInput().getRepresentations()), GuiElements.CHEM_BAR);
        // input
        Point inputPoint = point(adjustedInputPoint(bounds).x + slotSize + innerOffset, adjustedInputPoint(bounds).y);
        GuiHelper.addInputSlotElement(widgets, inputPoint, EntryIngredients.ofItemStacks(display.recipe().getItemInput().getRepresentations()), GuiElements.INPUT);
        // extra
        List<ItemStack> infuseStacks = new ObjectArrayList<>();
        for (InfusionStack infusionStack : display.recipe().getChemicalInput().getRepresentations()) {
            infuseStacks.addAll(MekaREI.INFUSION_STACK_HELPER.getStacksFor(infusionStack.getType(), true));
        }
        GuiHelper.addInputSlotElement(widgets, adjustedInputPoint(bounds), EntryIngredients.ofItemStacks(infuseStacks), GuiElements.INPUT_EXTRA);
        // output
        GuiHelper.addOutputSlotElement(widgets, adjustedOutputPoint(bounds), EntryIngredients.ofItemStacks(display.recipe().getOutputDefinition()), GuiElements.OUTPUT);
        GuiHelper.addProgressBar(widgets, point(getProgressBarX(bounds), getProgressBarY(bounds)), 2000, GuiProgress.ARROW_NORMAL_RIGHT);
        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 64;
    }

    @Override
    public int getDisplayWidth(MetallurgicInfuserDisplay display) {
        return 132;
    }

}
