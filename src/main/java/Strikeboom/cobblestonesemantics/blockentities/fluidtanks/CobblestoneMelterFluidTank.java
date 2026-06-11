package Strikeboom.cobblestonesemantics.blockentities.fluidtanks;

import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.fluid.FluidStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;


public class CobblestoneMelterFluidTank extends FluidStacksResourceHandler {
    public CobblestoneMelterFluidTank(int capacity) {
        super(1,capacity);
    }

    @Override
    public int insert(int index, FluidResource resource, int amount, TransactionContext transaction) {
        return 0;
    }

    @Override
    public boolean isValid(int index, FluidResource resource) {
        return resource.getFluid() == Fluids.LAVA;
    }
}
