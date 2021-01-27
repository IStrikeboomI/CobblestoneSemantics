package Strikeboom.cobblestonesemantics.guis.tileentity.customs.fluidtanks;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class LavaGeneratorFluidTank extends FluidTank {
    TileEntity te;
    public LavaGeneratorFluidTank(int capacity, TileEntity te) {
        super(capacity);
        this.te = te;
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
        return fluid != null && fluid.getFluid() == FluidRegistry.LAVA;
    }

    @Override
    protected void onContentsChanged() {
        te.markDirty();
    }

}
