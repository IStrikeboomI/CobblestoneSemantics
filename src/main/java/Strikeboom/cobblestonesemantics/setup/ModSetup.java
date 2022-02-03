package Strikeboom.cobblestonesemantics.setup;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlocks;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsItems;
import com.google.common.collect.Lists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModSetup {

    public static final CreativeModeTab CREATIVE_MODE_TAB = new CreativeModeTab(CobblestoneSemantics.MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_1.get());
        }
    };

    public static void init(FMLCommonSetupEvent event) {
        TierSortingRegistry.registerTier(CobblestoneSemanticsItems.COBBLESTONE_INFUSED_OBSIDIAN_TIER,new ResourceLocation(CobblestoneSemantics.MOD_ID,"cobblestone_infused_obsidian"),
                Lists.newArrayList(Tiers.WOOD,Tiers.GOLD,Tiers.IRON,Tiers.DIAMOND,Tiers.STONE),Lists.newArrayList(Tiers.NETHERITE));

    }
}
