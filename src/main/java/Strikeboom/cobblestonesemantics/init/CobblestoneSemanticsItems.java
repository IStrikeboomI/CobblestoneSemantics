package Strikeboom.cobblestonesemantics.init;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.items.CobblestoneBag;
import Strikeboom.cobblestonesemantics.items.CobblestoneInfusedObsidianBag;
import Strikeboom.cobblestonesemantics.items.materials.CobblestoneInfusedObsidianArmorMaterial;
import Strikeboom.cobblestonesemantics.items.materials.CobblestoneInfusedObsidianTier;
import Strikeboom.cobblestonesemantics.setup.ModSetup;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CobblestoneSemanticsItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CobblestoneSemantics.MOD_ID);
    public static final Item.Properties ITEM_PROPERTIES = new Item.Properties().tab(ModSetup.CREATIVE_MODE_TAB);

    public static final Tier COBBLESTONE_INFUSED_OBSIDIAN_TIER = new CobblestoneInfusedObsidianTier();
    public static final RegistryObject<Item> COBBLESTONE_INFUSED_OBSIDIAN_PICKAXE = ITEMS.register("cobblestone_infused_obsidian_pickaxe", () -> new PickaxeItem(COBBLESTONE_INFUSED_OBSIDIAN_TIER, 1, -2.4F, ITEM_PROPERTIES));
    public static final RegistryObject<Item> COBBLESTONE_INFUSED_OBSIDIAN_AXE = ITEMS.register("cobblestone_infused_obsidian_axe", () -> new AxeItem(COBBLESTONE_INFUSED_OBSIDIAN_TIER, 5F, -2.5F, ITEM_PROPERTIES));
    public static final RegistryObject<Item> COBBLESTONE_INFUSED_OBSIDIAN_HOE = ITEMS.register("cobblestone_infused_obsidian_hoe", () -> new HoeItem(COBBLESTONE_INFUSED_OBSIDIAN_TIER, -3, -2.8F, ITEM_PROPERTIES));
    public static final RegistryObject<Item> COBBLESTONE_INFUSED_OBSIDIAN_SHOVEL = ITEMS.register("cobblestone_infused_obsidian_shovel", () -> new ShovelItem(COBBLESTONE_INFUSED_OBSIDIAN_TIER, -3, -3.0F, ITEM_PROPERTIES));
    public static final RegistryObject<Item> COBBLESTONE_INFUSED_OBSIDIAN_SWORD = ITEMS.register("cobblestone_infused_obsidian_sword", () -> new SwordItem(COBBLESTONE_INFUSED_OBSIDIAN_TIER, 3, 0.0F, ITEM_PROPERTIES));

    public static final ArmorMaterial COBBLESTONE_INFUSED_OBSIDIAN_ARMOR_MATERIAL = new CobblestoneInfusedObsidianArmorMaterial();
    public static final RegistryObject<Item> COBBLESTONE_INFUSED_OBSIDIAN_HELMET = ITEMS.register("cobblestone_infused_obsidian_helmet", () -> new ArmorItem(COBBLESTONE_INFUSED_OBSIDIAN_ARMOR_MATERIAL, EquipmentSlot.HEAD, ITEM_PROPERTIES.fireResistant()));
    public static final RegistryObject<Item> COBBLESTONE_INFUSED_OBSIDIAN_CHESTPLATE = ITEMS.register("cobblestone_infused_obsidian_chestplate", () -> new ArmorItem(COBBLESTONE_INFUSED_OBSIDIAN_ARMOR_MATERIAL, EquipmentSlot.CHEST, ITEM_PROPERTIES.fireResistant()));
    public static final RegistryObject<Item> COBBLESTONE_INFUSED_OBSIDIAN_LEGGINGS = ITEMS.register("cobblestone_infused_obsidian_leggings", () -> new ArmorItem(COBBLESTONE_INFUSED_OBSIDIAN_ARMOR_MATERIAL, EquipmentSlot.LEGS, ITEM_PROPERTIES.fireResistant()));
    public static final RegistryObject<Item> COBBLESTONE_INFUSED_OBSIDIAN_BOOTS = ITEMS.register("cobblestone_infused_obsidian_boots", () -> new ArmorItem(COBBLESTONE_INFUSED_OBSIDIAN_ARMOR_MATERIAL, EquipmentSlot.FEET, ITEM_PROPERTIES.fireResistant()));

    public static final RegistryObject<Item> COBBLESTONE_BAG = ITEMS.register("cobblestone_bag", CobblestoneBag::new);
    public static final RegistryObject<Item> COBBLESTONE_INFUSED_OBSIDIAN_BAG = ITEMS.register("cobblestone_infused_obsidian_bag", CobblestoneInfusedObsidianBag::new);
}
