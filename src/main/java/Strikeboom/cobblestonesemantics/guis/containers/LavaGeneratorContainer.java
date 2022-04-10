package Strikeboom.cobblestonesemantics.guis.containers;

import Strikeboom.cobblestonesemantics.guis.tileentities.LavaGeneratorTileEntity;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlocks;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;

public class LavaGeneratorContainer extends Container {
    public final LavaGeneratorTileEntity blockEntity;
    private IWorldPosCallable access;

    public LavaGeneratorContainer(int windowId, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player, IWorldPosCallable access) {
        super(CobblestoneSemanticsContainers.LAVA_GENERATOR_MENU.get(), windowId);
        blockEntity = (LavaGeneratorTileEntity) player.getCommandSenderWorld().getBlockEntity(pos);
        this.access = access;
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
            if (!this.moveItemStackTo(current, 0,  36, true))
                return ItemStack.EMPTY;

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
        return stillValid(access, pPlayer, CobblestoneSemanticsBlocks.LAVA_GENERATOR.get());
    }
}
