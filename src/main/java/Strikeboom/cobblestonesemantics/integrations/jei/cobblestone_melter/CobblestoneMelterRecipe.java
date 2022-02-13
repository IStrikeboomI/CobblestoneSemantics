package Strikeboom.cobblestonesemantics.integrations.jei.cobblestone_melter;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

public class CobblestoneMelterRecipe {
    Block block;
    public CobblestoneMelterRecipe(Block block) {
        this.block = block;
    }
    public ItemStack getInput() {
        return new ItemStack(block);
    }
    public FluidStack getOutput() {
        return new FluidStack(Fluids.LAVA,500);
    }
}
