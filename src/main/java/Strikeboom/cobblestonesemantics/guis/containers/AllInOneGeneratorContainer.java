package Strikeboom.cobblestonesemantics.guis.containers;

import Strikeboom.cobblestonesemantics.guis.tileentities.AllInOneGeneratorTileEntity;
import Strikeboom.cobblestonesemantics.guis.tileentities.itemhandlers.AllInOneGeneratorItemHandler;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlocks;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;


public class AllInOneGeneratorContainer extends Container {
    public final AllInOneGeneratorTileEntity blockEntity;
    private AllInOneGeneratorItemHandler blockInventory;
    private IWorldPosCallable access;
    public AllInOneGeneratorContainer(int windowId, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player, IWorldPosCallable access) {
        super(CobblestoneSemanticsContainers.ALL_IN_ONE_GENERATOR_MENU.get(), windowId);
        blockEntity = (AllInOneGeneratorTileEntity)player.getCommandSenderWorld().getBlockEntity(pos);
        this.access = access;
        if (blockEntity != null) {
            blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                blockInventory = (AllInOneGeneratorItemHandler)h;
                addSlot(new SlotItemHandler(h,0,29,51));
                int melterSlotIndex = 1;
                for (int i = 60; i <= 78;i += 18) {
                    for (int j = 44; j <= 72;j += 18) {
                        addSlot(new SlotItemHandler(h, melterSlotIndex,i,j));
                        melterSlotIndex++;
                    }
                }
                int generatorSlotIndex = 5;
                for (int i = 24; i <= 78;i += 18) {
                    for (int j = 5; j <= 32;j += 18) {
                        this.addSlot(new SlotItemHandler(h, generatorSlotIndex,i,j));
                        generatorSlotIndex++;
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
            if (index < this.blockInventory.getSlots()) {
                if (!this.moveItemStackTo(current, blockInventory.getSlots(), blockInventory.getSlots() + 36, true))
                    return ItemStack.EMPTY;
            } else {
                if (!this.moveItemStackTo(current, 0, blockInventory.getSlots(), false))
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
    public boolean stillValid( PlayerEntity pPlayer) {
        return stillValid(access, pPlayer, CobblestoneSemanticsBlocks.ALL_IN_ONE_GENERATOR.get());
    }
}
