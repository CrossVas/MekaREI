package dev.crossvas.mekarei.utils;

import mekanism.api.MekanismAPI;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.infuse.InfuseType;
import mekanism.api.chemical.infuse.InfusionStack;
import mekanism.api.chemical.pigment.Pigment;
import mekanism.api.chemical.pigment.PigmentStack;
import mekanism.api.chemical.slurry.Slurry;
import mekanism.api.chemical.slurry.SlurryStack;
import mekanism.api.recipes.chemical.ItemStackToChemicalRecipe;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.tier.ChemicalTankTier;
import mekanism.common.util.ChemicalUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ChemicalStackHelper<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>> {

    public ChemicalStackHelper() {}

    protected abstract String getType();
    protected abstract IForgeRegistry<CHEMICAL> getRegistry();

    protected @Nullable IMekanismRecipeTypeProvider<? extends ItemStackToChemicalRecipe<CHEMICAL, STACK>, ?> getConversionRecipeType() {
        return null;
    }

    public List<ItemStack> getStacksFor(@NotNull CHEMICAL type, boolean displayConversions) {
        if (type.isEmptyType()) {
            return Collections.emptyList();
        } else {
            Level world = Minecraft.getInstance().level;
            if (world == null) {
                return Collections.emptyList();
            } else {
                List<ItemStack> stacks = new ArrayList<>();
                stacks.add(ChemicalUtil.getFullChemicalTank(ChemicalTankTier.BASIC, type));
                if (displayConversions) {
                    IMekanismRecipeTypeProvider<? extends ItemStackToChemicalRecipe<CHEMICAL, STACK>, ?> recipeType = this.getConversionRecipeType();
                    if (recipeType != null) {

                        for (ItemStackToChemicalRecipe<CHEMICAL, STACK> recipe : recipeType.getRecipes(world)) {
                            if (recipe.getOutputDefinition().stream().anyMatch((output) -> output.isTypeEqual(type))) {
                                stacks.addAll(recipe.getInput().getRepresentations());
                            }
                        }
                    }
                }
                return stacks;
            }
        }
    }

    public static class SlurryStackHelper extends ChemicalStackHelper<Slurry, SlurryStack> {
        public SlurryStackHelper() {}

        protected IForgeRegistry<Slurry> getRegistry() {
            return MekanismAPI.slurryRegistry();
        }

        protected String getType() {
            return "Slurry";
        }
    }

    public static class PigmentStackHelper extends ChemicalStackHelper<Pigment, PigmentStack> {
        public PigmentStackHelper() {}

        protected IForgeRegistry<Pigment> getRegistry() {
            return MekanismAPI.pigmentRegistry();
        }

        protected String getType() {
            return "Pigment";
        }
    }

    public static class InfusionStackHelper extends ChemicalStackHelper<InfuseType, InfusionStack> {
        public InfusionStackHelper() {}

        protected String getType() {
            return "Infuse Type";
        }

        protected IForgeRegistry<InfuseType> getRegistry() {
            return MekanismAPI.infuseTypeRegistry();
        }

        protected IMekanismRecipeTypeProvider<? extends ItemStackToChemicalRecipe<InfuseType, InfusionStack>, ?> getConversionRecipeType() {
            return MekanismRecipeType.INFUSION_CONVERSION;
        }
    }

    public static class GasStackHelper extends ChemicalStackHelper<Gas, GasStack> {
        public GasStackHelper() {}

        protected String getType() {
            return "Gas";
        }

        protected IForgeRegistry<Gas> getRegistry() {
            return MekanismAPI.gasRegistry();
        }

        protected IMekanismRecipeTypeProvider<? extends ItemStackToChemicalRecipe<Gas, GasStack>, ?> getConversionRecipeType() {
            return MekanismRecipeType.GAS_CONVERSION;
        }
    }
}
