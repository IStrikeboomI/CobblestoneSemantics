package Strikeboom.cobblestonesemantics.guis.container;

import Strikeboom.cobblestonesemantics.guis.tileentity.TileEntityCobblestoneMelter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCobblestoneMelter extends Container {
    IItemHandler handler;
    TileEntityCobblestoneMelter te;
    public ContainerCobblestoneMelter(IInventory playerInv, TileEntityCobblestoneMelter te) {
        this.handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null);
        this.te = te;
        this.addSlotToContainer(new SlotItemHandler(handler,0,53,33));
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
            if (fromSlot < this.handler.getSlots()) {

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
        return true;
    }
}
