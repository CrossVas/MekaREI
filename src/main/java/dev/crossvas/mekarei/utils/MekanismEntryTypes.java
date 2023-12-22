package dev.crossvas.mekarei.utils;

import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.EntryType;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.infuse.InfusionStack;
import mekanism.api.chemical.pigment.PigmentStack;
import mekanism.api.chemical.slurry.SlurryStack;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;

public class MekanismEntryTypes {

    public static final EntryType<InfusionStack> INFUSION_ENTRY = EntryType.deferred(new ResourceLocation("mekanism","infusion"));
    public static final EntryType<GasStack> GAS_ENTRY = EntryType.deferred(new ResourceLocation("mekanism","gas"));
    public static final EntryType<PigmentStack> PIGMENT_ENTRY = EntryType.deferred(new ResourceLocation("mekanism","pigment"));
    public static final EntryType<SlurryStack> SLURRY_ENTRY = EntryType.deferred(new ResourceLocation("mekanism","slurry"));

    public static EntryStack<InfusionStack> infusion(InfusionStack value) {
        return EntryStack.of(INFUSION_ENTRY, value);
    }

    public static EntryIngredient infusions(Collection<InfusionStack> values) {
        return EntryIngredients.of(INFUSION_ENTRY, values);
    }

    public static EntryIngredient gases(Collection<GasStack> values) {
        return EntryIngredients.of(GAS_ENTRY, values);
    }

    public static EntryStack<GasStack> gas(GasStack value) {
        return EntryStack.of(GAS_ENTRY, value);
    }

    public static EntryIngredient pigments(Collection<PigmentStack> values) {
        return EntryIngredients.of(PIGMENT_ENTRY, values);
    }

    public static EntryStack<PigmentStack> pigment(PigmentStack value) {
        return EntryStack.of(PIGMENT_ENTRY, value);
    }

    public static EntryIngredient slurries(Collection<SlurryStack> values) {
        return EntryIngredients.of(SLURRY_ENTRY, values);
    }

    public static EntryStack<SlurryStack> slurry(SlurryStack value) {
        return EntryStack.of(SLURRY_ENTRY, value);
    }
}
