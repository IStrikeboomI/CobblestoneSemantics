package Strikeboom.cobblestonesemantics.init;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.items.CobblestoneBag;
import Strikeboom.cobblestonesemantics.items.CobblestoneInfusedObsidianBag;
import Strikeboom.cobblestonesemantics.items.tools.*;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraftforge.common.util.EnumHelper;

import java.util.LinkedList;
import java.util.List;

public class ModItems {

    public static final List<Item> ITEMS = new LinkedList<>();

    public static Item cobblestoneInfusedObsidianHelmet;
    public static Item cobblestoneInfusedObsidianChestplate;
    public static Item cobblestoneInfusedObsidianLeggings;
    public static Item cobblestoneInfusedObsidianBoots;
    public static ItemPickaxe cobblestoneInfusedObsidianPickaxe;
    public static ItemAxe cobblestoneInfusedObsidianAxe;
    public static ItemSword cobblestoneInfusedObsidianSword;
    public static ItemSpade cobblestoneInfusedObsidianShovel;
    public static ItemHoe cobblestoneInfusedObsidianHoe;
    public static Item cobblestoneBag;
    public static Item cobblestoneInfusedObsidianBag;

    public static void preInit() {
        ItemArmor.ArmorMaterial cobblestoneInfusedObsidianArmor = EnumHelper.addArmorMaterial("cobblestoneInfusedObsidian",CobblestoneSemantics.MOD_ID + ":cobblestone_infused_obsidian",500,new int[] {3, 6, 8, 3},15, SoundEvents.BLOCK_STONE_BREAK,1f);
        cobblestoneInfusedObsidianHelmet = new ItemArmor(cobblestoneInfusedObsidianArmor,1, EntityEquipmentSlot.HEAD).setRegistryName("cobblestone_infused_obsidian_helmet").setUnlocalizedName(CobblestoneSemantics.MOD_ID + ".cobblestone_infused_obsidian_helmet");
        cobblestoneInfusedObsidianChestplate = new ItemArmor(cobblestoneInfusedObsidianArmor,1,EntityEquipmentSlot.CHEST).setRegistryName("cobblestone_infused_obsidian_chestplate").setUnlocalizedName(CobblestoneSemantics.MOD_ID + ".cobblestone_infused_obsidian_chestplate");
        cobblestoneInfusedObsidianLeggings = new ItemArmor(cobblestoneInfusedObsidianArmor,2,EntityEquipmentSlot.LEGS).setRegistryName("cobblestone_infused_obsidian_leggings").setUnlocalizedName(CobblestoneSemantics.MOD_ID + ".cobblestone_infused_obsidian_leggings");
        cobblestoneInfusedObsidianBoots = new ItemArmor(cobblestoneInfusedObsidianArmor,1,EntityEquipmentSlot.FEET).setRegistryName("cobblestone_infused_obsidian_boots").setUnlocalizedName(CobblestoneSemantics.MOD_ID + ".cobblestone_infused_obsidian_boots");

        Item.ToolMaterial cobblestoneInfusedObsidianTools = EnumHelper.addToolMaterial("cobblestoneInfusedObsidian",4,1800,14f,3.5f,15);
        cobblestoneInfusedObsidianPickaxe = new ToolPickaxe(cobblestoneInfusedObsidianTools,"cobblestone_infused_obsidian_pickaxe");
        cobblestoneInfusedObsidianAxe = new ToolAxe(cobblestoneInfusedObsidianTools,"cobblestone_infused_obsidian_axe");
        cobblestoneInfusedObsidianSword = new ToolSword(cobblestoneInfusedObsidianTools,"cobblestone_infused_obsidian_sword");
        cobblestoneInfusedObsidianShovel = new ToolSpade(cobblestoneInfusedObsidianTools,"cobblestone_infused_obsidian_shovel");
        cobblestoneInfusedObsidianHoe = new ToolHoe(cobblestoneInfusedObsidianTools,"cobblestone_infused_obsidian_hoe");

        cobblestoneBag = new CobblestoneBag().setRegistryName("cobblestone_bag").setUnlocalizedName(CobblestoneSemantics.MOD_ID + ".cobblestone_bag");
        cobblestoneInfusedObsidianBag = new CobblestoneInfusedObsidianBag().setRegistryName("cobblestone_infused_obsidian_bag").setUnlocalizedName(CobblestoneSemantics.MOD_ID + ".cobblestone_infused_obsidian_bag");

        add(cobblestoneInfusedObsidianHelmet    );
        add(cobblestoneInfusedObsidianChestplate);
        add(cobblestoneInfusedObsidianLeggings  );
        add(cobblestoneInfusedObsidianBoots     );
        add(cobblestoneInfusedObsidianPickaxe   );
        add(cobblestoneInfusedObsidianAxe       );
        add(cobblestoneInfusedObsidianSword     );
        add(cobblestoneInfusedObsidianShovel    );
        add(cobblestoneInfusedObsidianHoe       );
        add(cobblestoneBag                      );
        add(cobblestoneInfusedObsidianBag       );
    }
    private static void add(Item item) {
        item.setCreativeTab(CobblestoneSemantics.MOD_TAB);
        ITEMS.add(item);
    }
}
