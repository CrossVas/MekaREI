package dev.crossvas.mekarei.categories;

import dev.crossvas.mekarei.displays.SPSDisplay;
import dev.crossvas.mekarei.utils.*;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.client.jei.recipe.SPSJEIRecipe;
import mekanism.common.MekanismLang;
import mekanism.common.config.MekanismConfig;
import mekanism.common.registries.MekanismGases;
import mekanism.common.registries.MekanismItems;
import mekanism.common.util.text.EnergyDisplay;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.Collections;
import java.util.List;

public class SPSCategory implements DisplayCategory<SPSDisplay>, IGuiHelper {

    @Override
    public CategoryIdentifier<? extends SPSDisplay> getCategoryIdentifier() {
        return Categories.SPS;
    }

    @Override
    public Component getTitle() {
        return MekanismLang.SPS.translate();
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MekanismItems.ANTIMATTER_PELLET);
    }

    @Override
    public List<Widget> setupDisplay(SPSDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ObjectArrayList<>();
        GuiHelper.createRecipeBase(widgets, bounds);
        GuiHelper.addTank(widgets, point(bounds.getMinX() + offset, bounds.getMinY() + offset), MekanismEntryTypes.gases(display.recipe().input().getRepresentations()), GuiElements.TANK_DEFAULT, GuiElements.TANK_OVERLAY_DEFAULT);
        GuiHelper.addTank(widgets, point(bounds.getMaxX() - slotSize - offset, bounds.getMinY() + offset), MekanismEntryTypes.gases(Collections.singleton(display.recipe().output())), GuiElements.TANK_DEFAULT, GuiElements.TANK_OVERLAY_DEFAULT);

        GuiHelper.addProgressBar(widgets, point(getProgressBarX(bounds) - slotSize - innerOffset + 1, getProgressBarY(bounds)), 2000, GuiProgress.ARROW_LARGE_RIGHT);

        GuiHelper.addLabel(widgets, point(bounds.getCenterX(), bounds.getMinY() + slotSize), MekanismLang.SPS_ENERGY_INPUT.translate(EnergyDisplay.of(MekanismConfig.general.spsEnergyPerInput.get().multiply((long)MekanismConfig.general.spsInputPerAntimatter.get()))).withStyle(ChatFormatting.BLACK));
        GuiHelper.addLabel(widgets, point(bounds.getCenterX(), bounds.getMaxY() - slotSize - lineHeight), MekanismLang.PROCESS_RATE_MB.translate(1.0).withStyle(ChatFormatting.BLACK));
        return widgets;
    }

    @Override
    public int getDisplayWidth(SPSDisplay display) {
        return 175;
    }

    @Override
    public int getDisplayHeight() {
        return 70;
    }

    public static List<SPSJEIRecipe> getSPSRecipes() {
        return Collections.singletonList(new SPSJEIRecipe(IngredientCreatorAccess.gas().from(MekanismGases.POLONIUM, (long) MekanismConfig.general.spsInputPerAntimatter.get()), MekanismGases.ANTIMATTER.getStack(1L)));
    }
}
