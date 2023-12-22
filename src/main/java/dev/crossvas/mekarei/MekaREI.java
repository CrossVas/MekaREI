package dev.crossvas.mekarei;

import dev.crossvas.mekarei.utils.ChemicalStackHelper;
import net.minecraftforge.fml.common.Mod;

@Mod(MekaREI.ID)
public class MekaREI {

    public static final ChemicalStackHelper.GasStackHelper GAS_STACK_HELPER = new ChemicalStackHelper.GasStackHelper();
    public static final ChemicalStackHelper.InfusionStackHelper INFUSION_STACK_HELPER = new ChemicalStackHelper.InfusionStackHelper();
    public static final ChemicalStackHelper.PigmentStackHelper PIGMENT_STACK_HELPER = new ChemicalStackHelper.PigmentStackHelper();
    public static final ChemicalStackHelper.SlurryStackHelper SLURRY_STACK_HELPER = new ChemicalStackHelper.SlurryStackHelper();

    public static final String ID = "mekarei";
}
