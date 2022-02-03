package Strikeboom.cobblestonesemantics.guis.menus;

import Strikeboom.cobblestonesemantics.guis.blockentities.itemhandlers.BagItemHandler;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CobblestoneBagMenu extends AbstractContainerMenu {
    private BagItemHandler handler;
    public CobblestoneBagMenu(int windowId, BlockPos pos, Inventory playerInventory, Player player) {
        super(CobblestoneSemanticsMenus.COBBLESTONE_BAG_MENU.get(), windowId);

        if (!playerInventory.getSelected().isEmpty()) {
            playerInventory.getSelected().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
                int slotNumber = 0;
                handler = (BagItemHandler) iItemHandler;
                for (int i = 0;i < 3;i++) {
                    for (int j = 0;j < 3;j++) {
                        addSlot(new SlotItemHandler(
                                handler
                                ,slotNumber
                                ,62 + i * 18
                                ,17 + j*18));
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
    public ItemStack quickMoveStack(Player playerIn, int index) {
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
    public boolean stillValid(Player pPlayer) {
        return true;
    }
}
