package Strikeboom.cobblestonesemantics.items;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.guis.containers.CobblestoneInfusedObsidianBagContainer;
import Strikeboom.cobblestonesemantics.guis.tileentities.itemhandlers.ItemItemStackHandlerCapabilityWrapper;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

public class CobblestoneInfusedObsidianBag extends Item {
    public CobblestoneInfusedObsidianBag() {
        super(CobblestoneSemanticsItems.ITEM_PROPERTIES);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable World pWorld, List<ITextComponent> pTooltipComponents, ITooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TranslationTextComponent("tooltip." + CobblestoneSemantics.MOD_ID + ".holds","27"));
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new ItemItemStackHandlerCapabilityWrapper(27,this);
    }

    @Override
    public ActionResult<ItemStack> use(World pWorld, PlayerEntity pPlayer, Hand pUsedHand) {
        if (!pWorld.isClientSide) {
            if (pPlayer.getItemInHand(pUsedHand).getItem() == this) {
                INamedContainerProvider containerProvider = new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("item."+CobblestoneSemantics.MOD_ID+".cobblestone_infused_obsidian_bag");
                    }

                    @Override
                    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return new CobblestoneInfusedObsidianBagContainer(windowId, pPlayer.blockPosition(), playerInventory, playerEntity);
                    }
                };
                NetworkHooks.openGui((ServerPlayerEntity) pPlayer, containerProvider, pPlayer.blockPosition());
            }
        }
        return ActionResult.success(pPlayer.getItemInHand(pUsedHand));
    }
}
