package Strikeboom.cobblestonesemantics.blockentities.fluidtanks;

import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.fluid.FluidStacksResourceHandler;


public class LavaGeneratorFluidTank extends FluidStacksResourceHandler {
    public LavaGeneratorFluidTank() {
        super(1,5000);
    }

    @Override
    public boolean isValid(int index, FluidResource resource) {
        return resource.getFluid() == Fluids.LAVA;
    }
}
