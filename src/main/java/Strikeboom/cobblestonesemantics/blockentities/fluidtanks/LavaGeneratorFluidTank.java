package Strikeboom.cobblestonesemantics.blockentities.fluidtanks;

import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class LavaGeneratorFluidTank extends FluidTank {
    public LavaGeneratorFluidTank(int capacity) {
        super(capacity);
    }

    @Override
    public boolean isFluidValid(FluidStack stack) {
        return stack.getFluid() == Fluids.LAVA;
    }

}
