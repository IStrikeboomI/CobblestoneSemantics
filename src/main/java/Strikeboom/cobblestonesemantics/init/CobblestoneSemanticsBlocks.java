package Strikeboom.cobblestonesemantics.init;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.blocks.AllInOneGenerator;
import Strikeboom.cobblestonesemantics.blocks.CobblestoneGenerator;
import Strikeboom.cobblestonesemantics.blocks.CobblestoneMelter;
import Strikeboom.cobblestonesemantics.blocks.LavaGenerator;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class CobblestoneSemanticsBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, CobblestoneSemantics.MOD_ID);

    public static final DeferredHolder<Block,Block> COBBLESTONE_INFUSED_OBSIDIAN = BLOCKS.register("cobblestone_infused_obsidian", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).sound(SoundType.STONE).strength(50f,2500f).requiresCorrectToolForDrops()));

    //dirt
    public static final DeferredHolder<Block,Block> COBBLESTONE_GENERATOR_1 = BLOCKS.register("cobblestone_generator_1", () -> new CobblestoneGenerator(1,1,60,1));
    //stone
    public static final DeferredHolder<Block,Block> COBBLESTONE_GENERATOR_2 = BLOCKS.register("cobblestone_generator_2", () -> new CobblestoneGenerator(2,2,40,1));
    //iron
    public static final DeferredHolder<Block,Block> COBBLESTONE_GENERATOR_3 = BLOCKS.register("cobblestone_generator_3", () -> new CobblestoneGenerator(3,4,20,1));
    //redstone
    public static final DeferredHolder<Block,Block> COBBLESTONE_GENERATOR_4 = BLOCKS.register("cobblestone_generator_4", () -> new CobblestoneGenerator(4,8,10,1));
    //gold
    public static final DeferredHolder<Block,Block> COBBLESTONE_GENERATOR_5 = BLOCKS.register("cobblestone_generator_5", () -> new CobblestoneGenerator(5,16,7,2));
    //diamond
    public static final DeferredHolder<Block,Block> COBBLESTONE_GENERATOR_6 = BLOCKS.register("cobblestone_generator_6", () -> new CobblestoneGenerator(6,32,5,4));
    //emerald
    public static final DeferredHolder<Block,Block> COBBLESTONE_GENERATOR_7 = BLOCKS.register("cobblestone_generator_7", () -> new CobblestoneGenerator(7,64,4,8));
    //netherite
    public static final DeferredHolder<Block,Block> COBBLESTONE_GENERATOR_8 = BLOCKS.register("cobblestone_generator_8", () -> new CobblestoneGenerator(8,128,3,16));
    //nether star
    public static final DeferredHolder<Block,Block> COBBLESTONE_GENERATOR_9 = BLOCKS.register("cobblestone_generator_9", () -> new CobblestoneGenerator(9,256,2,32));
    //dragon egg
    public static final DeferredHolder<Block,Block> COBBLESTONE_GENERATOR_10 = BLOCKS.register("cobblestone_generator_10", () -> new CobblestoneGenerator(10,512,1,64));

    public static final DeferredHolder<Block,Block> COBBLESTONE_MELTER = BLOCKS.register("cobblestone_melter", CobblestoneMelter::new);
    public static final DeferredHolder<Block,Block> LAVA_GENERATOR = BLOCKS.register("lava_generator", LavaGenerator::new);
    public static final DeferredHolder<Block,Block> ALL_IN_ONE_GENERATOR = BLOCKS.register("all_in_one_generator", AllInOneGenerator::new);

    public static final DeferredHolder<Block,Block> MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN_BLOCK = BLOCKS.register("molten_cobblestone_infused_obsidian", () -> new LiquidBlock((FlowingFluid) CobblestoneSemanticsFluids.MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.LAVA)));
}
