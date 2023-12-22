package dev.crossvas.mekarei.utils;

import dev.architectury.fluid.FluidStack;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.List;

public class VanillaEntryHelper {

    public static EntryIngredient fluids(List<net.minecraftforge.fluids.FluidStack> fluids) {
        return EntryIngredients.of(VanillaEntryTypes.FLUID, getFluids(fluids));
    }

    public static List<FluidStack> getFluids(List<net.minecraftforge.fluids.FluidStack> fluids) {
        return fluids.stream().map(fluid -> FluidStack.create(fluid.getFluid(), fluid.getAmount())).toList();
    }
}
