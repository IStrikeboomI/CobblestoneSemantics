package Strikeboom.cobblestonesemantics.integrations.jei.cobblestone_melter;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.stream.Collectors;

public class CobblestoneMelterRecipe {
    public List<ItemStack> getInputs() {
        return Tags.Blocks.COBBLESTONE.getValues().stream().map(ItemStack::new).collect(Collectors.toList());
    }
    public FluidStack getOutput() {
        return new FluidStack(Fluids.LAVA,500);
    }
}
