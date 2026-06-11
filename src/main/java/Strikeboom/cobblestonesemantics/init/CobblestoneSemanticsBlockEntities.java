package Strikeboom.cobblestonesemantics.init;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.blockentities.AllInOneGeneratorBlockEntity;
import Strikeboom.cobblestonesemantics.blockentities.CobblestoneGeneratorBlockEntity;
import Strikeboom.cobblestonesemantics.blockentities.CobblestoneMelterBlockEntity;
import Strikeboom.cobblestonesemantics.blockentities.LavaGeneratorBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CobblestoneSemanticsBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CobblestoneSemantics.MOD_ID);

    public static final Supplier<BlockEntityType<CobblestoneGeneratorBlockEntity>> COBBLESTONE_GENERATOR_BLOCK_ENTITY = BLOCK_ENTITIES.register("cobblestone_generator",(loc) -> new BlockEntityType<>(CobblestoneGeneratorBlockEntity::new,
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_1.get(),
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_2.get(),
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_3.get(),
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_4.get(),
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_5.get(),
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_6.get(),
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_7.get(),
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_8.get(),
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_9.get(),
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_10.get()));

    public static final Supplier<BlockEntityType<CobblestoneMelterBlockEntity>> COBBLESTONE_MELTER_BLOCK_ENTITY = BLOCK_ENTITIES.register("cobblestone_melter",(loc) -> new BlockEntityType<>(CobblestoneMelterBlockEntity::new, CobblestoneSemanticsBlocks.COBBLESTONE_MELTER.get()));
    public static final Supplier<BlockEntityType<LavaGeneratorBlockEntity>> LAVA_GENERATOR_BLOCK_ENTITY = BLOCK_ENTITIES.register("lava_generator",(loc) -> new BlockEntityType<>(LavaGeneratorBlockEntity::new, CobblestoneSemanticsBlocks.LAVA_GENERATOR.get()));
    public static final Supplier<BlockEntityType<AllInOneGeneratorBlockEntity>> ALL_IN_ONE_GENERATOR_BLOCK_ENTITY = BLOCK_ENTITIES.register("all_in_one_generator",(loc) -> new BlockEntityType<>(AllInOneGeneratorBlockEntity::new, CobblestoneSemanticsBlocks.ALL_IN_ONE_GENERATOR.get()));
}
