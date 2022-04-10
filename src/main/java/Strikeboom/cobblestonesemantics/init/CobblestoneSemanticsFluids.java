package Strikeboom.cobblestonesemantics.init;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CobblestoneSemanticsFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, CobblestoneSemantics.MOD_ID);

    public static final RegistryObject<Fluid> MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN = FLUIDS.register("molten_cobblestone_infused_obsidian",() -> new ForgeFlowingFluid.Source(createProperties()));
    public static final RegistryObject<Fluid> MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN_FLOWING = FLUIDS.register("molten_cobblestone_infused_obsidian_flowing",() -> new ForgeFlowingFluid.Flowing(createProperties()));

    public static final RegistryObject<Block> MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN_BLOCK = CobblestoneSemanticsBlocks.BLOCKS.register("molten_cobblestone_infused_obsidian", () -> new FlowingFluidBlock(() -> (FlowingFluid) MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN.get(), AbstractBlock.Properties.copy(Blocks.LAVA)));
    public static final RegistryObject<Item> MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN_BUCKET = CobblestoneSemanticsItems.ITEMS.register("molten_cobblestone_infused_obsidian_bucket", () -> new BucketItem(MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(CobblestoneSemantics.CREATIVE_MODE_TAB)));

    private static ForgeFlowingFluid.Properties createProperties() {
        return new ForgeFlowingFluid.Properties(MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN,MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN_FLOWING, FluidAttributes.builder(
                        new ResourceLocation(CobblestoneSemantics.MOD_ID,"block/molten_cobblestone_infused_obsidian_still"),
                        new ResourceLocation(CobblestoneSemantics.MOD_ID,"block/molten_cobblestone_infused_obsidian_flow")
                )
                .overlay(new ResourceLocation(CobblestoneSemantics.MOD_ID,"block/molten_cobblestone_infused_obsidian_flow"))
                .density(2000)
                .temperature(1300)
                .viscosity(10000)
                .sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA)
                .translationKey("fluid."+CobblestoneSemantics.MOD_ID+".molten_cobblestone_infused_obsidian")
                .luminosity(15))
                .tickRate(30)
                .bucket(MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN_BUCKET)
                .block(() -> (FlowingFluidBlock) MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN_BLOCK.get());
    }

}
