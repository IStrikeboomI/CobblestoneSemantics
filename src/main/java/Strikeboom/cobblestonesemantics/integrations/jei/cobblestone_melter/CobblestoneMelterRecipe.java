package Strikeboom.cobblestonesemantics.integrations.jei.cobblestone_melter;

import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CobblestoneMelterRecipe {
    ItemStack stack;
    public CobblestoneMelterRecipe(ItemStack stack) {
        this.stack = stack;
    }
    public ItemStack getInput() {
        return stack;
    }
    public FluidStack getOutput() {
        return new FluidStack(Fluids.LAVA,500);
    }
}
