package dev.crossvas.mekarei.utils;

import dev.crossvas.mekarei.displays.*;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;

public class Categories {

    public static final CategoryIdentifier<MetallurgicInfuserDisplay> METAL_INFUSING = of("metallurgic_infuser");
    public static final CategoryIdentifier<ItemStackToInfusingDisplay> ITEM_INFUSING = of("item_infusing");
    public static final CategoryIdentifier<AntiprotonicSynthesizerDisplay> ANTIPROTONIC_SYNTH = of("antiprotonic_synthesizer");
    public static final CategoryIdentifier<ThermoelectricBoilerDisplay> THERMOELECTRIC_BOILER = of("thermoelectric_boiler");
    public static final CategoryIdentifier<CrystallizerDisplay> CRYSTALLIZER = of("crystallizer");
    public static final CategoryIdentifier<ChemicalDissolutionDisplay> CHEMICAL_DISSOLUTION = of("chemical_dissolution");
    public static final CategoryIdentifier<ChemicalInfuserDisplay> CHEMICAL_INFUSER = of("chemical_infuser");
    public static final CategoryIdentifier<ItemStackGasToItemStackRecipeDisplay> CHEMICAL_INJECTOR = of("chemical_injector");
    public static final CategoryIdentifier<ItemStackToGasRecipeDisplay> CHEMICAL_OXIDIZER = of("chemical_oxidizer");
    public static final CategoryIdentifier<ChemicalWasherDisplay> CHEMICAL_WASHER = of("chemical_washer");
    public static final CategoryIdentifier<CombinerDisplay> COMBINER = of("combiner");
    public static final CategoryIdentifier<CondensentratorDisplay> CONDENSENTRATOR = of("condensentrator");
    public static final CategoryIdentifier<CondensentratorDisplay> DECONDENSENTRATOR = of("decondensentrator");
    public static final CategoryIdentifier<ItemStackToItemStackRecipeDisplay> CRUSHER = of("crusher");
    public static final CategoryIdentifier<ElectrolyticSeparatorDisplay> SEPARATOR = of("separator");
    public static final CategoryIdentifier<ItemStackToEnergyDisplay> ITEM_TO_ENERGY = of("item_to_energy");
    public static final CategoryIdentifier<ItemStackToItemStackRecipeDisplay> ENRICHMENT = of("enrichment");
    public static final CategoryIdentifier<ItemStackToGasRecipeDisplay> ITEM_TO_GAS = of("item_to_gas");
    public static final CategoryIdentifier<GasToGasRecipeDisplay> ISOTOPIC_CENTRIFUGE = of("isotopic_centrifuge");
    public static final CategoryIdentifier<NutritionalLiquificationDisplay> NUTRITIONAL_LIQUIFICATION = of("nutritional_liquification");
    public static final CategoryIdentifier<ItemStackGasToItemStackRecipeDisplay> OSMIUM_COMPRESSOR = of("osmium_compressor");
    public static final CategoryIdentifier<PaintingMachineDisplay> PAINTING = of("painting");
    public static final CategoryIdentifier<PigmentExtractorDisplay> PIGMENT_EXTRACTOR = of("pigment_extractor");
    public static final CategoryIdentifier<PigmentMixerDisplay> PIGMENT_MIXER = of("pigment_mixer");
    public static final CategoryIdentifier<SawmillDisplay> SAWMILL = of("sawmill");
    public static final CategoryIdentifier<PressurizedChamberDisplay> PRESSURIZED_CHAMBER = of("pressurized_chamber");
    public static final CategoryIdentifier<ItemStackGasToItemStackRecipeDisplay> PURIFICATION_CHAMBER = of("purification_chamber");
    public static final CategoryIdentifier<GasToGasRecipeDisplay> SOLAR_NEUTRON = of("solar_neutron");
    public static final CategoryIdentifier<SPSDisplay> SPS = of("sps");
    public static final CategoryIdentifier<ThermalEvaporationDisplay> EVAPORATION_TOWER = of("evaporation_tower");

    private static <T extends BasicDisplay> CategoryIdentifier<T> of(String id) {
        return CategoryIdentifier.of("mekanism", id);
    }
}
