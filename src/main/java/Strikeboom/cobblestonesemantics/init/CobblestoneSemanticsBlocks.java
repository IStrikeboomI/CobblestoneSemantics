package Strikeboom.cobblestonesemantics.init;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.blocks.AllInOneGenerator;
import Strikeboom.cobblestonesemantics.blocks.CobblestoneGenerator;
import Strikeboom.cobblestonesemantics.blocks.CobblestoneMelter;
import Strikeboom.cobblestonesemantics.blocks.LavaGenerator;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CobblestoneSemanticsBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CobblestoneSemantics.MOD_ID);

    public static final RegistryObject<Block> COBBLESTONE_INFUSED_OBSIDIAN = BLOCKS.register("cobblestone_infused_obsidian", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).sound(SoundType.STONE).strength(50f,2500f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Item> COBBLESTONE_INFUSED_OBSIDIAN_ITEM = fromBlock(COBBLESTONE_INFUSED_OBSIDIAN);

    //dirt
    public static final RegistryObject<Block> COBBLESTONE_GENERATOR_1 = BLOCKS.register("cobblestone_generator_1", () -> new CobblestoneGenerator(1,1,60,1));
    //stone
    public static final RegistryObject<Block> COBBLESTONE_GENERATOR_2 = BLOCKS.register("cobblestone_generator_2", () -> new CobblestoneGenerator(2,2,40,1));
    //iron
    public static final RegistryObject<Block> COBBLESTONE_GENERATOR_3 = BLOCKS.register("cobblestone_generator_3", () -> new CobblestoneGenerator(3,4,20,1));
    //redstone
    public static final RegistryObject<Block> COBBLESTONE_GENERATOR_4 = BLOCKS.register("cobblestone_generator_4", () -> new CobblestoneGenerator(4,8,10,1));
    //gold
    public static final RegistryObject<Block> COBBLESTONE_GENERATOR_5 = BLOCKS.register("cobblestone_generator_5", () -> new CobblestoneGenerator(5,16,7,2));
    //diamond
    public static final RegistryObject<Block> COBBLESTONE_GENERATOR_6 = BLOCKS.register("cobblestone_generator_6", () -> new CobblestoneGenerator(6,32,5,4));
    //emerald
    public static final RegistryObject<Block> COBBLESTONE_GENERATOR_7 = BLOCKS.register("cobblestone_generator_7", () -> new CobblestoneGenerator(7,64,4,8));
    //netherite
    public static final RegistryObject<Block> COBBLESTONE_GENERATOR_8 = BLOCKS.register("cobblestone_generator_8", () -> new CobblestoneGenerator(8,128,3,16));
    //nether star
    public static final RegistryObject<Block> COBBLESTONE_GENERATOR_9 = BLOCKS.register("cobblestone_generator_9", () -> new CobblestoneGenerator(9,256,2,32));
    //dragon egg
    public static final RegistryObject<Block> COBBLESTONE_GENERATOR_10 = BLOCKS.register("cobblestone_generator_10", () -> new CobblestoneGenerator(10,512,1,64));

    public static final RegistryObject<Item> COBBLESTONE_GENERATOR_ITEM_1 = fromBlock(COBBLESTONE_GENERATOR_1);
    public static final RegistryObject<Item> COBBLESTONE_GENERATOR_ITEM_2 = fromBlock(COBBLESTONE_GENERATOR_2);
    public static final RegistryObject<Item> COBBLESTONE_GENERATOR_ITEM_3 = fromBlock(COBBLESTONE_GENERATOR_3);
    public static final RegistryObject<Item> COBBLESTONE_GENERATOR_ITEM_4 = fromBlock(COBBLESTONE_GENERATOR_4);
    public static final RegistryObject<Item> COBBLESTONE_GENERATOR_ITEM_5 = fromBlock(COBBLESTONE_GENERATOR_5);
    public static final RegistryObject<Item> COBBLESTONE_GENERATOR_ITEM_6 = fromBlock(COBBLESTONE_GENERATOR_6);
    public static final RegistryObject<Item> COBBLESTONE_GENERATOR_ITEM_7 = fromBlock(COBBLESTONE_GENERATOR_7);
    public static final RegistryObject<Item> COBBLESTONE_GENERATOR_ITEM_8 = fromBlock(COBBLESTONE_GENERATOR_8);
    public static final RegistryObject<Item> COBBLESTONE_GENERATOR_ITEM_9 = fromBlock(COBBLESTONE_GENERATOR_9);
    public static final RegistryObject<Item> COBBLESTONE_GENERATOR_ITEM_10 = fromBlock(COBBLESTONE_GENERATOR_10);

    public static final RegistryObject<Block> COBBLESTONE_MELTER = BLOCKS.register("cobblestone_melter", CobblestoneMelter::new);
    public static final RegistryObject<Item> COBBLESTONE_MELTER_ITEM = fromBlock(COBBLESTONE_MELTER);
    public static final RegistryObject<Block> LAVA_GENERATOR = BLOCKS.register("lava_generator", LavaGenerator::new);
    public static final RegistryObject<Item> LAVA_GENERATOR_ITEM = fromBlock(LAVA_GENERATOR);
    public static final RegistryObject<Block> ALL_IN_ONE_GENERATOR = BLOCKS.register("all_in_one_generator", AllInOneGenerator::new);
    public static final RegistryObject<Item> ALL_IN_ONE_GENERATOR_ITEM = fromBlock(ALL_IN_ONE_GENERATOR);

    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block) {
        return CobblestoneSemanticsItems.ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), CobblestoneSemanticsItems.ITEM_PROPERTIES));
    }
}
