package Strikeboom.cobblestonesemantics.integrations.jei.cobblestone_melter;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;

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
