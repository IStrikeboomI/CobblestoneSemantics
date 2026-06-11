package Strikeboom.cobblestonesemantics.init;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.blocks.AllInOneGenerator;
import Strikeboom.cobblestonesemantics.blocks.CobblestoneGenerator;
import Strikeboom.cobblestonesemantics.blocks.CobblestoneMelter;
import Strikeboom.cobblestonesemantics.blocks.LavaGenerator;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class CobblestoneSemanticsBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, CobblestoneSemantics.MOD_ID);

    public static final DeferredHolder<Block,Block> COBBLESTONE_INFUSED_OBSIDIAN = BLOCKS.register("cobblestone_infused_obsidian", (registry) -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).sound(SoundType.STONE).strength(50f,2500f).requiresCorrectToolForDrops()
            .setId(ResourceKey.create(BuiltInRegistries.BLOCK.key(), registry))));

    //dirt
    public static final DeferredHolder<Block,Block> COBBLESTONE_GENERATOR_1 = BLOCKS.register("cobblestone_generator_1", (registry) -> new CobblestoneGenerator(1,1,60,1,registry));
    //stone
    public static final DeferredHolder<Block,Block> COBBLESTONE_GENERATOR_2 = BLOCKS.register("cobblestone_generator_2", (registry) -> new CobblestoneGenerator(2,2,40,1,registry));
    //iron
    public static final DeferredHolder<Block,Block> COBBLESTONE_GENERATOR_3 = BLOCKS.register("cobblestone_generator_3", (registry) -> new CobblestoneGenerator(3,4,20,1,registry));
    //redstone
    public static final DeferredHolder<Block,Block> COBBLESTONE_GENERATOR_4 = BLOCKS.register("cobblestone_generator_4", (registry) -> new CobblestoneGenerator(4,8,10,1,registry));
    //gold
    public static final DeferredHolder<Block,Block> COBBLESTONE_GENERATOR_5 = BLOCKS.register("cobblestone_generator_5", (registry) -> new CobblestoneGenerator(5,16,7,2,registry));
    //diamond
    public static final DeferredHolder<Block,Block> COBBLESTONE_GENERATOR_6 = BLOCKS.register("cobblestone_generator_6", (registry) -> new CobblestoneGenerator(6,32,5,4,registry));
    //emerald
    public static final DeferredHolder<Block,Block> COBBLESTONE_GENERATOR_7 = BLOCKS.register("cobblestone_generator_7", (registry) -> new CobblestoneGenerator(7,64,4,8,registry));
    //netherite
    public static final DeferredHolder<Block,Block> COBBLESTONE_GENERATOR_8 = BLOCKS.register("cobblestone_generator_8", (registry) -> new CobblestoneGenerator(8,128,3,16,registry));
    //nether star
    public static final DeferredHolder<Block,Block> COBBLESTONE_GENERATOR_9 = BLOCKS.register("cobblestone_generator_9", (registry) -> new CobblestoneGenerator(9,256,2,32,registry));
    //dragon egg
    public static final DeferredHolder<Block,Block> COBBLESTONE_GENERATOR_10 = BLOCKS.register("cobblestone_generator_10", (registry) -> new CobblestoneGenerator(10,512,1,64,registry));

    public static final DeferredHolder<Block,Block> COBBLESTONE_MELTER = BLOCKS.register("cobblestone_melter", CobblestoneMelter::new);
    public static final DeferredHolder<Block,Block> LAVA_GENERATOR = BLOCKS.register("lava_generator", LavaGenerator::new);
    public static final DeferredHolder<Block,Block> ALL_IN_ONE_GENERATOR = BLOCKS.register("all_in_one_generator", AllInOneGenerator::new);

    public static final DeferredHolder<Block,Block> MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN_BLOCK = BLOCKS.register("molten_cobblestone_infused_obsidian", (loc) -> new LiquidBlock((FlowingFluid) CobblestoneSemanticsFluids.MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.LAVA).setId(ResourceKey.create(BuiltInRegistries.BLOCK.key(), loc))));
}
