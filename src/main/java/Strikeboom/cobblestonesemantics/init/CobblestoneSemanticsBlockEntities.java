package Strikeboom.cobblestonesemantics.init;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.guis.blockentities.AllInOneGeneratorBlockEntity;
import Strikeboom.cobblestonesemantics.guis.blockentities.CobblestoneGeneratorBlockEntity;
import Strikeboom.cobblestonesemantics.guis.blockentities.CobblestoneMelterBlockEntity;
import Strikeboom.cobblestonesemantics.guis.blockentities.LavaGeneratorBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CobblestoneSemanticsBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CobblestoneSemantics.MOD_ID);

    public static final RegistryObject<BlockEntityType<CobblestoneGeneratorBlockEntity>> COBBLESTONE_GENERATOR_BLOCK_ENTITY = BLOCK_ENTITIES.register("cobblestone_generator",() -> BlockEntityType.Builder.of(CobblestoneGeneratorBlockEntity::new,CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_1.get(),
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_2.get(),
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_3.get(),
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_4.get(),
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_5.get(),
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_6.get(),
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_7.get(),
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_8.get(),
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_9.get(),
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_10.get()).build(null));
    public static final RegistryObject<BlockEntityType<CobblestoneMelterBlockEntity>> COBBLESTONE_MELTER_BLOCK_ENTITY = BLOCK_ENTITIES.register("cobblestone_melter",() -> BlockEntityType.Builder.of(CobblestoneMelterBlockEntity::new, CobblestoneSemanticsBlocks.COBBLESTONE_MELTER.get()).build(null));
    public static final RegistryObject<BlockEntityType<LavaGeneratorBlockEntity>> LAVA_GENERATOR_BLOCK_ENTITY = BLOCK_ENTITIES.register("lava_generator",() -> BlockEntityType.Builder.of(LavaGeneratorBlockEntity::new, CobblestoneSemanticsBlocks.LAVA_GENERATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<AllInOneGeneratorBlockEntity>> ALL_IN_ONE_GENERATOR_BLOCK_ENTITY = BLOCK_ENTITIES.register("all_in_one_generator",() -> BlockEntityType.Builder.of(AllInOneGeneratorBlockEntity::new, CobblestoneSemanticsBlocks.ALL_IN_ONE_GENERATOR.get()).build(null));
}
