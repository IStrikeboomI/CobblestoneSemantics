package Strikeboom.cobblestonesemantics.items;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsItems;
import Strikeboom.cobblestonesemantics.menus.CobblestoneBagMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Consumer;

public class CobblestoneBag extends Item implements TooltipProvider {
    public CobblestoneBag(ResourceLocation loc) {
        super(new Properties().setId(ResourceKey.create(BuiltInRegistries.ITEM.key(),loc)));
    }


    @Override
    public InteractionResult use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide) {
            if (pPlayer.getItemInHand(pUsedHand).getItem() == this) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return Component.translatable("item."+CobblestoneSemantics.MOD_ID+".cobblestone_bag");
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                        return new CobblestoneBagMenu(windowId, playerInventory);
                    }
                };
                pPlayer.openMenu(containerProvider);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void addToTooltip(TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag flag, DataComponentGetter componentGetter) {
        tooltipAdder.accept(Component.translatable("tooltip." + CobblestoneSemantics.MOD_ID + ".holds","9"));
        tooltipAdder.accept(Component.translatable("tooltip." + CobblestoneSemantics.MOD_ID + ".upgrade"));
        tooltipAdder.accept(Component.translatable("tooltip." + CobblestoneSemantics.MOD_ID + ".upgrade_saves").withStyle(ChatFormatting.YELLOW));
    }
}
