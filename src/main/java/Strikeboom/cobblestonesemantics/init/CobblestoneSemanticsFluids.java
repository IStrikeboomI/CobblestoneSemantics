package Strikeboom.cobblestonesemantics.init;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.setup.ModSetup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CobblestoneSemanticsFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, CobblestoneSemantics.MOD_ID);

    public static final RegistryObject<Fluid> MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN = FLUIDS.register("molten_cobblestone_infused_obsidian",() -> new ForgeFlowingFluid.Source(createProperties()));
    public static final RegistryObject<Fluid> MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN_FLOWING = FLUIDS.register("molten_cobblestone_infused_obsidian_flowing",() -> new ForgeFlowingFluid.Flowing(createProperties()));

    public static final RegistryObject<Block> MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN_BLOCK = CobblestoneSemanticsBlocks.BLOCKS.register("molten_cobblestone_infused_obsidian", () -> new LiquidBlock(() -> (FlowingFluid) MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN.get(), BlockBehaviour.Properties.copy(Blocks.LAVA)));
    public static final RegistryObject<Item> MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN_BUCKET = CobblestoneSemanticsItems.ITEMS.register("molten_cobblestone_infused_obsidian_bucket", () -> new BucketItem(MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ModSetup.CREATIVE_MODE_TAB)));

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
                .block(() -> (LiquidBlock) MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN_BLOCK.get());
    }

}
