package Strikeboom.cobblestonesemantics.items.materials;

import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlocks;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public class CobblestoneInfusedObsidianArmorMaterial implements ArmorMaterial {
    @Override
    public int getDurabilityForSlot(EquipmentSlot pSlot) {
        return new int[]{555, 625, 610, 485}[pSlot.getIndex()];
    }

    @Override
    public int getDefenseForSlot(EquipmentSlot pSlot) {
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
