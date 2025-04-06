package Strikeboom.cobblestonesemantics.items;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsItems;
import Strikeboom.cobblestonesemantics.menus.CobblestoneBagMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class CobblestoneBag extends Item {
    public CobblestoneBag() {
        super(CobblestoneSemanticsItems.ITEM_PROPERTIES);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext context, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip." + CobblestoneSemantics.MOD_ID + ".holds","9"));
        pTooltipComponents.add(Component.translatable("tooltip." + CobblestoneSemantics.MOD_ID + ".upgrade"));
        pTooltipComponents.add(Component.translatable("tooltip." + CobblestoneSemantics.MOD_ID + ".upgrade_saves").withStyle(ChatFormatting.YELLOW));
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
}
