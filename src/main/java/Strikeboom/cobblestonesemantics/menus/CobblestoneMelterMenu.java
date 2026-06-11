package Strikeboom.cobblestonesemantics.menus;

import Strikeboom.cobblestonesemantics.blockentities.CobblestoneMelterBlockEntity;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlocks;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.StacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;


public class CobblestoneMelterMenu extends AbstractContainerMenu {

    public final CobblestoneMelterBlockEntity blockEntity;
    private StacksResourceHandler<ItemStack, ItemResource>  blockInventory;
    public CobblestoneMelterMenu(int windowId, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(windowId,FriendlyByteBuf.readBlockPos(extraData),playerInventory);
    }
    public CobblestoneMelterMenu(int windowId, BlockPos pos, Inventory playerInventory) {
        super(CobblestoneSemanticsMenus.COBBLESTONE_MELTER_MENU.get(), windowId);
        blockEntity = (CobblestoneMelterBlockEntity)playerInventory.player.level().getBlockEntity(pos);

        if (blockEntity != null) {
            blockInventory = (StacksResourceHandler<ItemStack, ItemResource>) playerInventory.player.level().getCapability(Capabilities.Item.BLOCK,pos,null);
            addSlot(new ResourceHandlerSlot(blockInventory,blockInventory::set,0,53,33));
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
            if (index < this.blockInventory.size()) {
                if (!this.moveItemStackTo(current, blockInventory.size(), blockInventory.size() + 36, true))
                    return ItemStack.EMPTY;
            } else {
                if (!this.moveItemStackTo(current, 0, blockInventory.size(), false))
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
    public boolean stillValid( Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), pPlayer, CobblestoneSemanticsBlocks.COBBLESTONE_MELTER.get());
    }

}
