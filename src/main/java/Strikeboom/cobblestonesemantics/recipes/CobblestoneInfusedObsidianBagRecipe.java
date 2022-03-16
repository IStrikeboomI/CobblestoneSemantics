package Strikeboom.cobblestonesemantics.recipes;

import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlocks;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsCustomRecipes;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsItems;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsTags;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class CobblestoneInfusedObsidianBagRecipe extends CustomRecipe {

    public CobblestoneInfusedObsidianBagRecipe(ResourceLocation pId) {
        super(pId);
    }

    @Override
    public boolean matches(CraftingContainer pContainer, Level pLevel) {
        int bagAmount = 0;
        int cobblestoneInfusedObsidianAmount = 0;
        for (int i = 0;i < pContainer.getContainerSize();i++) {
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
    public ItemStack assemble(CraftingContainer pContainer) {
        ItemStack cobblestoneBag = ItemStack.EMPTY;
        for (int i = 0;i < pContainer.getContainerSize();i++) {
            if (pContainer.getItem(i).is(CobblestoneSemanticsItems.COBBLESTONE_BAG.get())) {
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
    public RecipeSerializer<?> getSerializer() {
        return CobblestoneSemanticsCustomRecipes.   COBBLESTONE_INFUSED_OBSIDIAN_BAG_RECIPE.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeType.CRAFTING;
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
