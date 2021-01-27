package Strikeboom.cobblestonesemantics.integrations.tconstruct;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.init.ModFluids;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.utils.HarvestLevels;

public class CobblestoneInfusedObsidianToolMaterial {
    public static void preInit() {
        Material cobblestoneInfusedObsidian = new Material(CobblestoneSemantics.MOD_ID + ".cobblestone_infused_obsidian",0x553f91);
        cobblestoneInfusedObsidian.addItem("cobblestoneinfusedobsidian");
        TinkerRegistry.addMaterialStats(cobblestoneInfusedObsidian, new HeadMaterialStats(3520, 8.25F, 6.03F, HarvestLevels.COBALT), new HandleMaterialStats(1.35F, 200), new ExtraMaterialStats(300), new BowMaterialStats(1.33F, 2.35F, 2.32F));
        cobblestoneInfusedObsidian.setFluid(ModFluids.moltenCobblestoneInfusedObsidian);
        cobblestoneInfusedObsidian.setCastable(true);
        cobblestoneInfusedObsidian.setCraftable(false);
        TinkerRegistry.integrate(cobblestoneInfusedObsidian).preInit();
    }
}
