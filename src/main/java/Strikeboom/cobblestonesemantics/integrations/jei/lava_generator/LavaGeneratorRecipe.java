package Strikeboom.cobblestonesemantics.integrations.jei.lava_generator;

import net.minecraft.fluid.Fluids;
import net.minecraftforge.fluids.FluidStack;

public class LavaGeneratorRecipe {
    public FluidStack getOutput() {
        return new FluidStack(Fluids.LAVA,1000);
    }
}
