package Strikeboom.cobblestonesemantics.guis.blockentities.fluidtanks;

import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class CobblestoneMelterFluidTank extends FluidTank {
    public CobblestoneMelterFluidTank(int capacity) {
        super(capacity);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        return 0;
    }

    @Override
    public boolean isFluidValid(FluidStack stack) {
        return stack.getFluid() == Fluids.LAVA;
    }

}
