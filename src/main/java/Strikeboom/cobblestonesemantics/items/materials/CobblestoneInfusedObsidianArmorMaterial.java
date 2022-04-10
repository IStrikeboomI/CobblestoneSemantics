package Strikeboom.cobblestonesemantics.items.materials;

import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlocks;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class CobblestoneInfusedObsidianArmorMaterial implements IArmorMaterial {
    @Override
    public int getDurabilityForSlot(EquipmentSlotType pSlot) {
        return new int[]{555, 625, 610, 485}[pSlot.getIndex()];
    }

    @Override
    public int getDefenseForSlot(EquipmentSlotType pSlot) {
        return new int[] {3, 6, 8, 3}[pSlot.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return 19;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_NETHERITE;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(CobblestoneSemanticsBlocks.COBBLESTONE_INFUSED_OBSIDIAN.get());
    }

    @Override
    public String getName() {
        return "cobblestonesemantics:cobblestone_infused_obsidian";
    }

    @Override
    public float getToughness() {
        return 3f;
    }

    @Override
    public float getKnockbackResistance() {
        return 1.5f;
    }
}
