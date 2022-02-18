package Strikeboom.cobblestonesemantics.items.materials;

import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlocks;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;


public class CobblestoneInfusedObsidianTier implements Tier {
    @Override
    public int getUses() {
        return 1800;
    }

    @Override
    public float getSpeed() {
        return 14f;
    }

    @Override
    public float getAttackDamageBonus() {
        return 3.5f;
    }

    @Override
    public int getLevel() {return 3;}

    @Override
    public int getEnchantmentValue() {
        return 25;
    }

    @Override
    public  Ingredient getRepairIngredient() {
        return Ingredient.of(CobblestoneSemanticsBlocks.COBBLESTONE_INFUSED_OBSIDIAN.get());
    }
}
