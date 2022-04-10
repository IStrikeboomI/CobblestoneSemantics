package Strikeboom.cobblestonesemantics.init;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.guis.tileentities.AllInOneGeneratorTileEntity;
import Strikeboom.cobblestonesemantics.guis.tileentities.CobblestoneGeneratorTileEntity;
import Strikeboom.cobblestonesemantics.guis.tileentities.CobblestoneMelterTileEntity;
import Strikeboom.cobblestonesemantics.guis.tileentities.LavaGeneratorTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CobblestoneSemanticsTileEntities {
    public static final DeferredRegister<TileEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, CobblestoneSemantics.MOD_ID);

    public static final RegistryObject<TileEntityType<CobblestoneGeneratorTileEntity>> COBBLESTONE_GENERATOR_BLOCK_ENTITY = BLOCK_ENTITIES.register("cobblestone_generator",() -> TileEntityType.Builder.of(CobblestoneGeneratorTileEntity::new,CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_1.get(),
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_2.get(),
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_3.get(),
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_4.get(),
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_5.get(),
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_6.get(),
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_7.get(),
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_8.get(),
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_9.get(),
                                                                                                                                                                                                                                                          CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_10.get()).build(null));
    public static final RegistryObject<TileEntityType<CobblestoneMelterTileEntity>> COBBLESTONE_MELTER_BLOCK_ENTITY = BLOCK_ENTITIES.register("cobblestone_melter",() -> TileEntityType.Builder.of(CobblestoneMelterTileEntity::new, CobblestoneSemanticsBlocks.COBBLESTONE_MELTER.get()).build(null));
    public static final RegistryObject<TileEntityType<LavaGeneratorTileEntity>> LAVA_GENERATOR_BLOCK_ENTITY = BLOCK_ENTITIES.register("lava_generator",() -> TileEntityType.Builder.of(LavaGeneratorTileEntity::new, CobblestoneSemanticsBlocks.LAVA_GENERATOR.get()).build(null));
    public static final RegistryObject<TileEntityType<AllInOneGeneratorTileEntity>> ALL_IN_ONE_GENERATOR_BLOCK_ENTITY = BLOCK_ENTITIES.register("all_in_one_generator",() -> TileEntityType.Builder.of(AllInOneGeneratorTileEntity::new, CobblestoneSemanticsBlocks.ALL_IN_ONE_GENERATOR.get()).build(null));
}
