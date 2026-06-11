package Strikeboom.cobblestonesemantics.init;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class CobblestoneSemanticsFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, CobblestoneSemantics.MOD_ID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, CobblestoneSemantics.MOD_ID);

    public static final DeferredHolder<FluidType,FluidType> MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN_TYPE = FLUID_TYPES.register("molten_cobblestone_infused_obsidian", () -> new FluidType(FluidType.Properties.create()
            .lightLevel(15)
            .density(2000)
            .viscosity(10000)
            .temperature(1300)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_EMPTY_LAVA)
            .rarity(Rarity.UNCOMMON)
            .pathType(PathType.LAVA)
            .adjacentPathType(null)
            .descriptionId("fluid."+CobblestoneSemantics.MOD_ID+".molten_cobblestone_infused_obsidian")
    ));

    public static final DeferredHolder<Fluid,Fluid> MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN = FLUIDS.register("molten_cobblestone_infused_obsidian",() -> new BaseFlowingFluid.Source(createProperties()));
    public static final DeferredHolder<Fluid,Fluid> MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN_FLOWING = FLUIDS.register("molten_cobblestone_infused_obsidian_flowing",() -> new BaseFlowingFluid.Flowing(createProperties()));

    private static BaseFlowingFluid.Properties createProperties() {
        return new BaseFlowingFluid.Properties(MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN_TYPE, MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN, MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN_FLOWING)
                .tickRate(30)
                .block(() -> (LiquidBlock) CobblestoneSemanticsBlocks.MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN_BLOCK.get())
                .bucket(CobblestoneSemanticsItems.MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN_BUCKET);
    }

}
