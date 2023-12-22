package dev.crossvas.mekarei.categories;

import dev.crossvas.mekarei.displays.NutritionalLiquificationDisplay;
import dev.crossvas.mekarei.utils.*;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.recipe.impl.NutritionalLiquifierIRecipe;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.registries.MekanismFluids;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class NutritionalLiquificationCategory implements DisplayCategory<NutritionalLiquificationDisplay>, IGuiHelper {

    @Override
    public CategoryIdentifier<? extends NutritionalLiquificationDisplay> getCategoryIdentifier() {
        return Categories.NUTRITIONAL_LIQUIFICATION;
    }

    @Override
    public Component getTitle() {
        return MekanismBlocks.NUTRITIONAL_LIQUIFIER.getTextComponent();
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MekanismBlocks.NUTRITIONAL_LIQUIFIER.getBlock());
    }

    @Override
    public List<Widget> setupDisplay(NutritionalLiquificationDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ObjectArrayList<>();
        GuiHelper.createRecipeBase(widgets, bounds);
        GuiHelper.addInputSlotElement(widgets, adjustedInputPoint(bounds), EntryIngredients.ofItemStacks(display.recipe().getInput().getRepresentations()), GuiElements.INPUT);
        GuiHelper.addTank(widgets, point(bounds.getMaxX() - offset - slotSize, bounds.getMinY() + offset), VanillaEntryHelper.fluids(display.recipe().getOutputDefinition()), GuiElements.TANK_DEFAULT_BLUE, GuiElements.TANK_OVERLAY_DEFAULT);
        GuiHelper.addProgressBar(widgets, point(getProgressBarX(bounds) - slotSize + innerOffset, getProgressBarY(bounds)), 2000, GuiProgress.ARROW_LARGE_RIGHT);
        return widgets;
    }

    @Override
    public int getDisplayWidth(NutritionalLiquificationDisplay display) {
        return 120;
    }

    @Override
    public int getDisplayHeight() {
        return 70;
    }

    public static List<NutritionalLiquifierIRecipe> getRecipes() {
        List<NutritionalLiquifierIRecipe> recipes = new ObjectArrayList<>();
        ForgeRegistries.ITEMS.forEach(item -> {
            if (item != null) {
                if (item.isEdible()) {
                    ItemStack stack = new ItemStack(item);
                    FoodProperties food = stack.getFoodProperties(null);
                    if (food != null && food.getNutrition() > 0) {
                        recipes.add(new NutritionalLiquifierIRecipe(item, IngredientCreatorAccess.item().from(stack), MekanismFluids.NUTRITIONAL_PASTE.getFluidStack(food.getNutrition() * 50)));
                    }
                }
            }
        });
        return recipes;
    }
}
