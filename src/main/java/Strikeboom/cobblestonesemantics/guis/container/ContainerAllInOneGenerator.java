package Strikeboom.cobblestonesemantics.guis.container;

import Strikeboom.cobblestonesemantics.guis.tileentity.TileEntityAllInOneGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerAllInOneGenerator extends Container {
    IItemHandler handler;
    IInventory playerInv;
    public ContainerAllInOneGenerator(IInventory playerInv, TileEntityAllInOneGenerator te) {
        this.handler = te.handler;
        this.playerInv = playerInv;

        this.addSlotToContainer(new SlotItemHandler(handler,0,29,51));
        int melterSlotIndex = 1;
        for (int i = 60; i <= 78;i += 18) {
            for (int j = 44; j <= 72;j += 18) {
                this.addSlotToContainer(new SlotItemHandler(handler, melterSlotIndex,i,j));
                melterSlotIndex++;
            }
        }
        int generatorSlotIndex = 5;
        for (int i = 24; i <= 78;i += 18) {
            for (int j = 5; j <= 32;j += 18) {
                this.addSlotToContainer(new SlotItemHandler(handler, generatorSlotIndex,i,j));
                generatorSlotIndex++;
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
