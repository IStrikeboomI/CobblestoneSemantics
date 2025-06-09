package Strikeboom.cobblestonesemantics.recipes;

import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsCustomRecipes;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsItems;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

public class CobblestoneInfusedObsidianBagRecipe extends CustomRecipe {

    public CobblestoneInfusedObsidianBagRecipe() {
        super(CraftingBookCategory.EQUIPMENT);
    }

    @Override
    public boolean matches(CraftingInput pContainer, Level pLevel) {
        int bagAmount = 0;
        int cobblestoneInfusedObsidianAmount = 0;
        for (int i = 0;i < pContainer.size();i++) {
            ItemStack stack = pContainer.getItem(i);
            if (stack.is(CobblestoneSemanticsItems.COBBLESTONE_BAG.get())) {
                bagAmount++;
            }
            if (stack.is(CobblestoneSemanticsTags.COBBLESTONE_INFUSED_OBSIDIAN_TAG)) {
                cobblestoneInfusedObsidianAmount++;
            }
        }
        return bagAmount == 1 && cobblestoneInfusedObsidianAmount == 1;
    }

    @Override
    public ItemStack assemble(CraftingInput pContainer, HolderLookup.Provider registries) {
        ItemStack cobblestoneBag = ItemStack.EMPTY;
        for (int i = 0;i < pContainer.size();i++) {
            if (pContainer.getItem(i).is(CobblestoneSemanticsItems.COBBLESTONE_BAG.get())) {
                cobblestoneBag = pContainer.getItem(i).copy();
            }
        }
        if (!cobblestoneBag.isEmpty()) {
            ItemStack cobblestoneInfusedObsidianBag = new ItemStack(CobblestoneSemanticsItems.COBBLESTONE_INFUSED_OBSIDIAN_BAG.get());
            IItemHandler cobblestoneBagHandler = cobblestoneBag.getCapability(Capabilities.ItemHandler.ITEM);
            IItemHandler cobblestoneInfusedObsidianBagHandler =    cobblestoneInfusedObsidianBag.getCapability(Capabilities.ItemHandler.ITEM);
            for (int i = 0;i < cobblestoneBagHandler.getSlots();i++) {
                ((ItemStackHandler)cobblestoneInfusedObsidianBagHandler).setStackInSlot(i,cobblestoneBagHandler.getStackInSlot(i));
            }
            return cobblestoneInfusedObsidianBag;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<CobblestoneInfusedObsidianBagRecipe> getSerializer() {
        return CobblestoneSemanticsCustomRecipes.COBBLESTONE_INFUSED_OBSIDIAN_BAG_RECIPE.get();
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> list = NonNullList.create();
        if (input.items().stream().noneMatch(itemStack -> itemStack.getItem() == CobblestoneSemanticsItems.COBBLESTONE_BAG.get())) {
            list.add(CobblestoneSemanticsItems.COBBLESTONE_BAG.get().getDefaultInstance());
        }
        if (input.items().stream().noneMatch(itemStack -> itemStack.getTags().anyMatch(itemTagKey -> itemTagKey.equals(CobblestoneSemanticsTags.COBBLESTONE_INFUSED_OBSIDIAN_TAG)))) {
            list.add(CobblestoneSemanticsItems.COBBLESTONE_BAG.get().getDefaultInstance());
        }
        return list;
    }

}
