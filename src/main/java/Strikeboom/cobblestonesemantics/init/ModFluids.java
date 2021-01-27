package Strikeboom.cobblestonesemantics.init;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class ModFluids {
    public static final List<Fluid> FLUIDS = new LinkedList<>();

    public static Fluid moltenCobblestoneInfusedObsidian;

    public static void preInit() {
        moltenCobblestoneInfusedObsidian = new Fluid("moltencobblestoneinfusedobsidian",new ResourceLocation(CobblestoneSemantics.MOD_ID,"blocks/cobblestone_infused_obsidian_still"),new ResourceLocation(CobblestoneSemantics.MOD_ID,"blocks/cobblestone_infused_obsidian_flow"),new Color(85,63,145)).setViscosity(2000).setTemperature(1000).setLuminosity(3000);
        add(moltenCobblestoneInfusedObsidian);
    }
    private static void add(Fluid fluid) {
        FluidRegistry.registerFluid(fluid);
        FluidRegistry.addBucketForFluid(fluid);
        FLUIDS.add(fluid);
        ModBlocks.BLOCKS.add(new BlockFluidClassic(fluid, Material.LAVA).setRegistryName(fluid.getName()+"_block"));
    }
}
