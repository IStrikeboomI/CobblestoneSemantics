package Strikeboom.cobblestonesemantics.recipes;

import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsCustomRecipes;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsItems;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.ItemStackHandler;
import net.neoforged.neoforge.capabilities.Capabilities;

public class CobblestoneInfusedObsidianBagRecipe extends ShapelessRecipe {

    public CobblestoneInfusedObsidianBagRecipe(ResourceLocation pId) {
        super(pId, CraftingBookCategory.EQUIPMENT);
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
    public ItemStack assemble(CraftingContainer pContainer, RegistryAccess p_267165_) {
        ItemStack cobblestoneBag = ItemStack.EMPTY;
        for (int i = 0;i < pContainer.getContainerSize();i++) {
            if (pContainer.getItem(i).is(CobblestoneSemanticsItems.COBBLESTONE_BAG.get())) {
                cobblestoneBag = pContainer.getItem(i).copy();
            }
        }
        if (!cobblestoneBag.isEmpty()) {
            ItemStack cobblestoneInfusedObsidianBag = new ItemStack(CobblestoneSemanticsItems.COBBLESTONE_INFUSED_OBSIDIAN_BAG.get());
            cobblestoneBag.getCapability(Capabilities.ItemHandler.ITEM).ifPresent(iItemHandler -> {
                cobblestoneInfusedObsidianBag.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler1 -> {
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
    public ItemStack getResultItem(RegistryAccess p_267025_) {
        return new ItemStack(CobblestoneSemanticsItems.COBBLESTONE_INFUSED_OBSIDIAN_BAG.get());
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        return null;
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
