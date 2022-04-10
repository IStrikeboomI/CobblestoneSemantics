package Strikeboom.cobblestonesemantics.recipes;

import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsCustomRecipes;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsItems;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsTags;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class CobblestoneInfusedObsidianBagRecipe extends SpecialRecipe {

    public CobblestoneInfusedObsidianBagRecipe(ResourceLocation pId) {
        super(pId);
    }

    @Override
    public boolean matches(CraftingInventory pContainer, World pWorld) {
        int bagAmount = 0;
        int cobblestoneInfusedObsidianAmount = 0;
        for (int i = 0;i < pContainer.getContainerSize();i++) {
            ItemStack stack = pContainer.getItem(i);
            if (CobblestoneSemanticsItems.COBBLESTONE_BAG.get() == stack.getItem()) {
                bagAmount++;
            }
            if (CobblestoneSemanticsTags.COBBLESTONE_INFUSED_OBSIDIAN_TAG.contains(stack.getItem())) {
                cobblestoneInfusedObsidianAmount++;
            }
        }
        return bagAmount == 1 && cobblestoneInfusedObsidianAmount == 1;
    }

    @Override
    public ItemStack assemble(CraftingInventory pContainer) {
        ItemStack cobblestoneBag = ItemStack.EMPTY;
        for (int i = 0;i < pContainer.getContainerSize();i++) {
            if (pContainer.getItem(i).getItem() == CobblestoneSemanticsItems.COBBLESTONE_BAG.get()) {
                cobblestoneBag = pContainer.getItem(i).copy();
            }
        }
        if (!cobblestoneBag.isEmpty()) {
            ItemStack cobblestoneInfusedObsidianBag = new ItemStack(CobblestoneSemanticsItems.COBBLESTONE_INFUSED_OBSIDIAN_BAG.get());
            cobblestoneBag.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent( iItemHandler -> {
                cobblestoneInfusedObsidianBag.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler1 -> {
                    for (int i = 0;i < iItemHandler.getSlots();i++) {
                        ((ItemStackHandler)iItemHandler1).setStackInSlot(i,iItemHandler.getStackInSlot(i));
                    }
                });
            });
            return cobblestoneInfusedObsidianBag;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= 2;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return CobblestoneSemanticsCustomRecipes.COBBLESTONE_INFUSED_OBSIDIAN_BAG_RECIPE.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return IRecipeType.CRAFTING;
    }

    @Override
    public ItemStack getResultItem() {
        return new ItemStack(CobblestoneSemanticsItems.COBBLESTONE_INFUSED_OBSIDIAN_BAG.get());
    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY,Ingredient.of(CobblestoneSemanticsItems.COBBLESTONE_BAG.get()),Ingredient.of(CobblestoneSemanticsTags.COBBLESTONE_INFUSED_OBSIDIAN_TAG));
    }

}
