package Strikeboom.cobblestonesemantics.integrations.jei.lava_generator;

import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;

public class LavaGeneratorRecipe {
    public FluidStack getIngredient() {
        return new FluidStack(Fluids.LAVA,1000);
    }
}
