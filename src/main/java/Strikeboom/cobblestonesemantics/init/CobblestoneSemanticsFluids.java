package Strikeboom.cobblestonesemantics.init;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

public class CobblestoneSemanticsFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, CobblestoneSemantics.MOD_ID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, CobblestoneSemantics.MOD_ID);

    public static final RegistryObject<FluidType> MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN_TYPE = FLUID_TYPES.register("molten_cobblestone_infused_obsidian", () -> new FluidType(FluidType.Properties.create()
            .lightLevel(15)
            .density(2000)
            .viscosity(10000)
            .temperature(1300)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_EMPTY_LAVA)
            .rarity(Rarity.UNCOMMON)
            .pathType(BlockPathTypes.LAVA)
            .adjacentPathType(null)
            .descriptionId("fluid."+CobblestoneSemantics.MOD_ID+".molten_cobblestone_infused_obsidian")
    ) {
        @Override
        public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
            consumer.accept(new IClientFluidTypeExtensions() {

                @Override
                public int getTintColor() {
                    return 0x7e7e21a6;
                }

                @Override
                public ResourceLocation getStillTexture() {
                    return new ResourceLocation(CobblestoneSemantics.MOD_ID,"block/molten_cobblestone_infused_obsidian_still");
                }

                @Override
                public ResourceLocation getFlowingTexture() {
                    return new ResourceLocation(CobblestoneSemantics.MOD_ID,"block/molten_cobblestone_infused_obsidian_flowing");
                }
            });
        }
    });

    public static final RegistryObject<Fluid> MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN = FLUIDS.register("molten_cobblestone_infused_obsidian",() -> new ForgeFlowingFluid.Source(createProperties()));
    public static final RegistryObject<Fluid> MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN_FLOWING = FLUIDS.register("molten_cobblestone_infused_obsidian_flowing",() -> new ForgeFlowingFluid.Flowing(createProperties()));

    private static ForgeFlowingFluid.Properties createProperties() {
        return new ForgeFlowingFluid.Properties(MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN_TYPE, MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN, MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN_FLOWING)
                .tickRate(30)
                .block(() -> (LiquidBlock) CobblestoneSemanticsBlocks.MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN_BLOCK.get())
                .bucket(CobblestoneSemanticsItems.MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN_BUCKET);
    }

}
