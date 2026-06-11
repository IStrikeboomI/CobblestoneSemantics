package Strikeboom.cobblestonesemantics.init;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.stream.Collectors;

public class CobblestoneSemanticsCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, CobblestoneSemantics.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATIVE_MODE_TAB = CREATIVE_MODE_TABS.register(CobblestoneSemantics.MOD_ID,() -> CreativeModeTab.builder()
            .icon(() -> CobblestoneSemanticsItems.COBBLESTONE_GENERATOR_ITEM_1.get().getDefaultInstance())
            .title(Component.translatable("itemGroup.cobblestonesemantics"))
            .displayItems((parameters, output) -> {
                output.acceptAll(CobblestoneSemanticsItems.ITEMS.getEntries().stream().map(itemRegistryObject -> new ItemStack(itemRegistryObject.get())).collect(Collectors.toList()));
            })
            .build());
}
