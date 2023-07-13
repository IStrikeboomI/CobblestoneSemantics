package Strikeboom.cobblestonesemantics.init;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.items.CobblestoneBag;
import Strikeboom.cobblestonesemantics.items.CobblestoneInfusedObsidianBag;
import Strikeboom.cobblestonesemantics.items.materials.CobblestoneInfusedObsidianArmorMaterial;
import Strikeboom.cobblestonesemantics.items.materials.CobblestoneInfusedObsidianTier;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CobblestoneSemanticsItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CobblestoneSemantics.MOD_ID);
    public static final Item.Properties ITEM_PROPERTIES = new Item.Properties();

    public static final Tier COBBLESTONE_INFUSED_OBSIDIAN_TIER = new CobblestoneInfusedObsidianTier();
    public static final RegistryObject<Item> COBBLESTONE_INFUSED_OBSIDIAN_PICKAXE = ITEMS.register("cobblestone_infused_obsidian_pickaxe", () -> new PickaxeItem(COBBLESTONE_INFUSED_OBSIDIAN_TIER, 1, -2.4F, ITEM_PROPERTIES));
    public static final RegistryObject<Item> COBBLESTONE_INFUSED_OBSIDIAN_AXE = ITEMS.register("cobblestone_infused_obsidian_axe", () -> new AxeItem(COBBLESTONE_INFUSED_OBSIDIAN_TIER, 5F, -2.5F, ITEM_PROPERTIES));
    public static final RegistryObject<Item> COBBLESTONE_INFUSED_OBSIDIAN_HOE = ITEMS.register("cobblestone_infused_obsidian_hoe", () -> new HoeItem(COBBLESTONE_INFUSED_OBSIDIAN_TIER, -3, -2.8F, ITEM_PROPERTIES));
    public static final RegistryObject<Item> COBBLESTONE_INFUSED_OBSIDIAN_SHOVEL = ITEMS.register("cobblestone_infused_obsidian_shovel", () -> new ShovelItem(COBBLESTONE_INFUSED_OBSIDIAN_TIER, -3, -3.0F, ITEM_PROPERTIES));
    public static final RegistryObject<Item> COBBLESTONE_INFUSED_OBSIDIAN_SWORD = ITEMS.register("cobblestone_infused_obsidian_sword", () -> new SwordItem(COBBLESTONE_INFUSED_OBSIDIAN_TIER, 3, 0.0F, ITEM_PROPERTIES));

    public static final ArmorMaterial COBBLESTONE_INFUSED_OBSIDIAN_ARMOR_MATERIAL = new CobblestoneInfusedObsidianArmorMaterial();
    public static final RegistryObject<Item> COBBLESTONE_INFUSED_OBSIDIAN_HELMET = ITEMS.register("cobblestone_infused_obsidian_helmet", () -> new ArmorItem(COBBLESTONE_INFUSED_OBSIDIAN_ARMOR_MATERIAL, ArmorItem.Type.HELMET, ITEM_PROPERTIES.fireResistant()));
    public static final RegistryObject<Item> COBBLESTONE_INFUSED_OBSIDIAN_CHESTPLATE = ITEMS.register("cobblestone_infused_obsidian_chestplate", () -> new ArmorItem(COBBLESTONE_INFUSED_OBSIDIAN_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, ITEM_PROPERTIES.fireResistant()));
    public static final RegistryObject<Item> COBBLESTONE_INFUSED_OBSIDIAN_LEGGINGS = ITEMS.register("cobblestone_infused_obsidian_leggings", () -> new ArmorItem(COBBLESTONE_INFUSED_OBSIDIAN_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, ITEM_PROPERTIES.fireResistant()));
    public static final RegistryObject<Item> COBBLESTONE_INFUSED_OBSIDIAN_BOOTS = ITEMS.register("cobblestone_infused_obsidian_boots", () -> new ArmorItem(COBBLESTONE_INFUSED_OBSIDIAN_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, ITEM_PROPERTIES.fireResistant()));

    public static final RegistryObject<Item> COBBLESTONE_BAG = ITEMS.register("cobblestone_bag", CobblestoneBag::new);
    public static final RegistryObject<Item> COBBLESTONE_INFUSED_OBSIDIAN_BAG = ITEMS.register("cobblestone_infused_obsidian_bag", CobblestoneInfusedObsidianBag::new);

    public static final RegistryObject<Item> MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN_BUCKET = ITEMS.register("molten_cobblestone_infused_obsidian_bucket", () -> new BucketItem(CobblestoneSemanticsFluids.MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final RegistryObject<Item> COBBLESTONE_GENERATOR_ITEM_1 = fromBlock(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_1);
    public static final RegistryObject<Item> COBBLESTONE_GENERATOR_ITEM_2 = fromBlock(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_2);
    public static final RegistryObject<Item> COBBLESTONE_GENERATOR_ITEM_3 = fromBlock(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_3);
    public static final RegistryObject<Item> COBBLESTONE_GENERATOR_ITEM_4 = fromBlock(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_4);
    public static final RegistryObject<Item> COBBLESTONE_GENERATOR_ITEM_5 = fromBlock(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_5);
    public static final RegistryObject<Item> COBBLESTONE_GENERATOR_ITEM_6 = fromBlock(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_6);
    public static final RegistryObject<Item> COBBLESTONE_GENERATOR_ITEM_7 = fromBlock(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_7);
    public static final RegistryObject<Item> COBBLESTONE_GENERATOR_ITEM_8 = fromBlock(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_8);
    public static final RegistryObject<Item> COBBLESTONE_GENERATOR_ITEM_9 = fromBlock(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_9);
    public static final RegistryObject<Item> COBBLESTONE_GENERATOR_ITEM_10 = fromBlock(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_10);
    public static final RegistryObject<Item> COBBLESTONE_MELTER_ITEM = fromBlock(CobblestoneSemanticsBlocks.COBBLESTONE_MELTER);
    public static final RegistryObject<Item> LAVA_GENERATOR_ITEM = fromBlock(CobblestoneSemanticsBlocks.LAVA_GENERATOR);
    public static final RegistryObject<Item> ALL_IN_ONE_GENERATOR_ITEM = fromBlock(CobblestoneSemanticsBlocks.ALL_IN_ONE_GENERATOR);
    public static final RegistryObject<Item> COBBLESTONE_INFUSED_OBSIDIAN_ITEM = fromBlock(CobblestoneSemanticsBlocks.COBBLESTONE_INFUSED_OBSIDIAN);

    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block) {
        return CobblestoneSemanticsItems.ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), ITEM_PROPERTIES.durability(0).stacksTo(64)));
    }
}
