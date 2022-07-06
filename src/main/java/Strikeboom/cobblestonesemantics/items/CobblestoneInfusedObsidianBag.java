package Strikeboom.cobblestonesemantics.items;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.guis.blockentities.itemhandlers.ItemItemStackHandlerCapabilityWrapper;
import Strikeboom.cobblestonesemantics.guis.menus.CobblestoneInfusedObsidianBagMenu;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CobblestoneInfusedObsidianBag extends Item {
    public CobblestoneInfusedObsidianBag() {
        super(CobblestoneSemanticsItems.ITEM_PROPERTIES);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip." + CobblestoneSemantics.MOD_ID + ".holds","27"));
    }
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ItemItemStackHandlerCapabilityWrapper(27,this);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide) {
            if (pPlayer.getItemInHand(pUsedHand).getItem() == this) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return Component.translatable("item."+CobblestoneSemantics.MOD_ID+".cobblestone_infused_obsidian_bag");
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                        return new CobblestoneInfusedObsidianBagMenu(windowId, playerInventory);
                    }
                };
                NetworkHooks.openGui((ServerPlayer) pPlayer, containerProvider, pPlayer.getOnPos());
            }
        }
        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }
}
