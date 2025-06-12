package Strikeboom.cobblestonesemantics.init;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.function.Consumer;


public class CobblestoneSemanticsItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, CobblestoneSemantics.MOD_ID);
    public static final Item.Properties TOOL_PROPERTIES = new Item.Properties().fireResistant().rarity(Rarity.RARE);

    public static final ToolMaterial COBBLESTONE_INFUSED_OBSIDIAN_TIER = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 3000, 10.0F, 5.0F, 12, CobblestoneSemanticsTags.COBBLESTONE_INFUSED_OBSIDIAN_TAG);
    public static final DeferredHolder<Item,Item> COBBLESTONE_INFUSED_OBSIDIAN_PICKAXE = ITEMS.register("cobblestone_infused_obsidian_pickaxe", (loc) -> new Item(TOOL_PROPERTIES.pickaxe(COBBLESTONE_INFUSED_OBSIDIAN_TIER, 1, -2.4F).setId(ResourceKey.create(BuiltInRegistries.ITEM.key(),loc))));
    public static final DeferredHolder<Item,Item> COBBLESTONE_INFUSED_OBSIDIAN_AXE = ITEMS.register("cobblestone_infused_obsidian_axe", (loc) -> new AxeItem(COBBLESTONE_INFUSED_OBSIDIAN_TIER, 5F, -2.5F, TOOL_PROPERTIES.setId(ResourceKey.create(BuiltInRegistries.ITEM.key(),loc))));
    public static final DeferredHolder<Item,Item> COBBLESTONE_INFUSED_OBSIDIAN_HOE = ITEMS.register("cobblestone_infused_obsidian_hoe", (loc) -> new HoeItem(COBBLESTONE_INFUSED_OBSIDIAN_TIER, -3, -2.8F, TOOL_PROPERTIES.setId(ResourceKey.create(BuiltInRegistries.ITEM.key(),loc))));
    public static final DeferredHolder<Item,Item> COBBLESTONE_INFUSED_OBSIDIAN_SHOVEL = ITEMS.register("cobblestone_infused_obsidian_shovel", (loc) -> new ShovelItem(COBBLESTONE_INFUSED_OBSIDIAN_TIER, -3, -3.0F, TOOL_PROPERTIES.setId(ResourceKey.create(BuiltInRegistries.ITEM.key(),loc))));
    public static final DeferredHolder<Item,Item> COBBLESTONE_INFUSED_OBSIDIAN_SWORD = ITEMS.register("cobblestone_infused_obsidian_sword", (loc) -> new Item(TOOL_PROPERTIES.sword(COBBLESTONE_INFUSED_OBSIDIAN_TIER, 3, 0.0F).setId(ResourceKey.create(BuiltInRegistries.ITEM.key(),loc))));

    public static final ArmorMaterial COBBLESTONE_INFUSED_OBSIDIAN_ARMOR_MATERIAL = new ArmorMaterial(
            30, Util.make(new EnumMap<>(ArmorType.class), p_371445_ -> {
        p_371445_.put(ArmorType.BOOTS, 3);
        p_371445_.put(ArmorType.LEGGINGS, 6);
        p_371445_.put(ArmorType.CHESTPLATE, 8);
        p_371445_.put(ArmorType.HELMET, 3);
        p_371445_.put(ArmorType.BODY, 11);
    }), 10, SoundEvents.ARMOR_EQUIP_GENERIC, 2.0F, 1.0f, CobblestoneSemanticsTags.COBBLESTONE_INFUSED_OBSIDIAN_TAG, ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(CobblestoneSemantics.MOD_ID, "cobblestone_infused_obsidian")));
    public static final DeferredHolder<Item,Item> COBBLESTONE_INFUSED_OBSIDIAN_HELMET = ITEMS.register("cobblestone_infused_obsidian_helmet", (loc) -> new Item(TOOL_PROPERTIES.humanoidArmor(COBBLESTONE_INFUSED_OBSIDIAN_ARMOR_MATERIAL, ArmorType.HELMET).setId(ResourceKey.create(BuiltInRegistries.ITEM.key(),loc))));
    public static final DeferredHolder<Item,Item> COBBLESTONE_INFUSED_OBSIDIAN_CHESTPLATE = ITEMS.register("cobblestone_infused_obsidian_chestplate", (loc) -> new Item(TOOL_PROPERTIES.humanoidArmor(COBBLESTONE_INFUSED_OBSIDIAN_ARMOR_MATERIAL, ArmorType.CHESTPLATE).setId(ResourceKey.create(BuiltInRegistries.ITEM.key(),loc))));
    public static final DeferredHolder<Item,Item> COBBLESTONE_INFUSED_OBSIDIAN_LEGGINGS = ITEMS.register("cobblestone_infused_obsidian_leggings", (loc) -> new Item(TOOL_PROPERTIES.humanoidArmor(COBBLESTONE_INFUSED_OBSIDIAN_ARMOR_MATERIAL, ArmorType.LEGGINGS).setId(ResourceKey.create(BuiltInRegistries.ITEM.key(),loc))));
    public static final DeferredHolder<Item,Item> COBBLESTONE_INFUSED_OBSIDIAN_BOOTS = ITEMS.register("cobblestone_infused_obsidian_boots", (loc) -> new Item(TOOL_PROPERTIES.humanoidArmor(COBBLESTONE_INFUSED_OBSIDIAN_ARMOR_MATERIAL, ArmorType.BOOTS).setId(ResourceKey.create(BuiltInRegistries.ITEM.key(),loc))));

    public static final DeferredHolder<Item,Item> MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN_BUCKET = ITEMS.register("molten_cobblestone_infused_obsidian_bucket", (loc) -> new BucketItem(CobblestoneSemanticsFluids.MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).setId(ResourceKey.create(BuiltInRegistries.ITEM.key(),loc))));

    public static final DeferredHolder<Item,Item> COBBLESTONE_GENERATOR_ITEM_1 = fromBlock(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_1);
    public static final DeferredHolder<Item,Item> COBBLESTONE_GENERATOR_ITEM_2 = fromBlock(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_2);
    public static final DeferredHolder<Item,Item> COBBLESTONE_GENERATOR_ITEM_3 = fromBlock(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_3);
    public static final DeferredHolder<Item,Item> COBBLESTONE_GENERATOR_ITEM_4 = fromBlock(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_4);
    public static final DeferredHolder<Item,Item> COBBLESTONE_GENERATOR_ITEM_5 = fromBlock(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_5);
    public static final DeferredHolder<Item,Item> COBBLESTONE_GENERATOR_ITEM_6 = fromBlock(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_6);
    public static final DeferredHolder<Item,Item> COBBLESTONE_GENERATOR_ITEM_7 = fromBlock(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_7);
    public static final DeferredHolder<Item,Item> COBBLESTONE_GENERATOR_ITEM_8 = fromBlock(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_8);
    public static final DeferredHolder<Item,Item> COBBLESTONE_GENERATOR_ITEM_9 = fromBlock(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_9);
    public static final DeferredHolder<Item,Item> COBBLESTONE_GENERATOR_ITEM_10 = fromBlock(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_10);
    public static final DeferredHolder<Item,Item> COBBLESTONE_MELTER_ITEM = fromBlock(CobblestoneSemanticsBlocks.COBBLESTONE_MELTER);
    public static final DeferredHolder<Item,Item> LAVA_GENERATOR_ITEM = fromBlock(CobblestoneSemanticsBlocks.LAVA_GENERATOR);
    public static final DeferredHolder<Item,Item> ALL_IN_ONE_GENERATOR_ITEM = fromBlock(CobblestoneSemanticsBlocks.ALL_IN_ONE_GENERATOR);
    public static final DeferredHolder<Item,Item> COBBLESTONE_INFUSED_OBSIDIAN_ITEM = fromBlock(CobblestoneSemanticsBlocks.COBBLESTONE_INFUSED_OBSIDIAN);

    public static <B extends Block> DeferredHolder<Item,Item> fromBlock(DeferredHolder<B,B> block) {
        return CobblestoneSemanticsItems.ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().setId(ResourceKey.create(BuiltInRegistries.ITEM.key(),block.getId()))) {
            @Override
            public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
                if (block.get() instanceof TooltipProvider tp) {
                    tp.addToTooltip(context,tooltipAdder,flag,stack);
                }
            }
        });
    }
}
