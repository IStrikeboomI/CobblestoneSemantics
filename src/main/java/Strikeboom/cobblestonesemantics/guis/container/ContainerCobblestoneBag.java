package Strikeboom.cobblestonesemantics.guis.container;

import Strikeboom.cobblestonesemantics.init.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCobblestoneBag extends Container {

    IInventory playerInv;
    ItemStack stack;
    IItemHandler handler;
    public ContainerCobblestoneBag(IInventory playerInv, ItemStack stack) {
        this.playerInv = playerInv;
        this.stack = stack;
        int slotNumber = 0;
        handler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null);
        for (int i = 0;i < 3;i++) {
            for (int j = 0;j < 3;j++) {
                slotNumber++;
                this.addSlotToContainer(new SlotItemHandler(
                        handler
                        ,slotNumber - 1
                        ,62 + i * 18
                        ,17 + j*18));
            }
        }

        int xPos = 8;
        int yPos = 84;

        //draws hotbar
        for (int x = 0; x < 9; x++) {
            this.addSlotToContainer(new Slot(playerInv, x, xPos + x * 18, yPos + 58));
        }

        //draws the 27 main slots
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, xPos + x * 18, yPos + y * 18));
            }
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
        ItemStack previous = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(fromSlot);

        if (slot != null && slot.getHasStack()) {
            ItemStack current = slot.getStack();

            previous = current.copy();

            if (fromSlot < handler.getSlots()) {

                if (!this.mergeItemStack(current, handler.getSlots(), handler.getSlots() + 36, true))
                    return ItemStack.EMPTY;
            } else {

                if (!this.mergeItemStack(current, 0, handler.getSlots(), false))
                    return ItemStack.EMPTY;
            }

            if (current.getCount() == 0)
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();

            if (current.getCount() == previous.getCount())
                return null;
            slot.onTake(playerIn, current);
        }
        return previous;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return  !playerIn.isSneaking() && playerIn.getHeldItemMainhand().getItem() == ModItems.cobblestoneBag;
    }
}
