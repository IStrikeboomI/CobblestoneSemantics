package Strikeboom.cobblestonesemantics.guis.containers;

import Strikeboom.cobblestonesemantics.guis.tileentities.itemhandlers.BagItemHandler;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsContainers;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CobblestoneInfusedObsidianBagContainer extends Container {
    private BagItemHandler handler;
    public CobblestoneInfusedObsidianBagContainer(int windowId, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(CobblestoneSemanticsContainers.COBBLESTONE_INFUSED_OBSIDIAN_BAG_MENU.get(), windowId);

        if (!playerInventory.getSelected().isEmpty()) {
            playerInventory.getSelected().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
                int slotNumber = 0;
                handler = (BagItemHandler) iItemHandler;
                for (int i = 0;i < 9;i++) {
                    for (int j = 0;j < 3;j++) {
                        this.addSlot(new SlotItemHandler(
                                handler
                                ,slotNumber
                                , 8 + i * 18
                                ,18 + j * 18));
                        slotNumber++;
                    }
                }
            });
        }


        int xPos = 8;
        int yPos = 84;

        //draws hotbar
        for (int x = 0; x < 9; x++) {
            addSlot(new Slot(playerInventory, x, xPos + x * 18, yPos + 58));
        }

        //draws the 27 main slots
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                addSlot(new Slot(playerInventory, x + y * 9 + 9, xPos + x * 18, yPos + y * 18));
            }
        }
    }
    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack previous = ItemStack.EMPTY;
        Slot slot = slots.get(index);

        if (slot.hasItem()) {
            ItemStack current = slot.getItem();
            previous = current.copy();
            if (index < this.handler.getSlots()) {
                if (!this.moveItemStackTo(current, handler.getSlots(), handler.getSlots() + 36, true))
                    return ItemStack.EMPTY;
            } else {
                if (!this.moveItemStackTo(current, 0, handler.getSlots(), false))
                    return ItemStack.EMPTY;
            }

            if (current.getCount() == 0)
                slot.set(ItemStack.EMPTY);
            else
                slot.setChanged();

            if (current.getCount() == previous.getCount())
                return ItemStack.EMPTY;
            slot.onTake(playerIn, current);
        }
        return previous;
    }
    @Override
    public boolean stillValid(PlayerEntity pPlayer) {
        return pPlayer.getItemInHand(pPlayer.getUsedItemHand()).getItem() == CobblestoneSemanticsItems.COBBLESTONE_INFUSED_OBSIDIAN_BAG.get();
    }
}
