package Strikeboom.cobblestonesemantics.menus;

import Strikeboom.cobblestonesemantics.blockentities.AllInOneGeneratorBlockEntity;
import Strikeboom.cobblestonesemantics.blockentities.itemhandlers.AllInOneGeneratorItemHandler;
import Strikeboom.cobblestonesemantics.blocks.AllInOneGenerator;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlocks;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsCapabilities;
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
import net.neoforged.neoforge.items.SlotItemHandler;

public class AllInOneGeneratorMenu extends AbstractContainerMenu {
    public final AllInOneGeneratorBlockEntity blockEntity;
    private AllInOneGeneratorItemHandler blockInventory;

    public AllInOneGeneratorMenu(int containerId, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(containerId,FriendlyByteBuf.readBlockPos(extraData),playerInventory);
    }

    public AllInOneGeneratorMenu(int windowId, BlockPos pos, Inventory playerInventory) {
        super(CobblestoneSemanticsMenus.ALL_IN_ONE_GENERATOR_MENU.get(), windowId);
        blockEntity = (AllInOneGeneratorBlockEntity)playerInventory.player.getCommandSenderWorld().getBlockEntity(pos);

        if (blockEntity != null) {
            blockInventory = (AllInOneGeneratorItemHandler) playerInventory.player.getCommandSenderWorld().getCapability(Capabilities.ItemHandler.BLOCK,pos,null);
            if (blockInventory != null) {
                addSlot(new SlotItemHandler(blockInventory, 0, 29, 51));
                int melterSlotIndex = 1;
                for (int i = 60; i <= 78; i += 18) {
                    for (int j = 44; j <= 72; j += 18) {
                        addSlot(new SlotItemHandler(blockInventory, melterSlotIndex, i, j));
                        melterSlotIndex++;
                    }
                }
                int generatorSlotIndex = 5;
                for (int i = 24; i <= 78; i += 18) {
                    for (int j = 5; j <= 32; j += 18) {
                        this.addSlot(new SlotItemHandler(blockInventory, generatorSlotIndex, i, j));
                        generatorSlotIndex++;
                    }
                }
            }
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
    public boolean stillValid( Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), pPlayer, CobblestoneSemanticsBlocks.ALL_IN_ONE_GENERATOR.get());
    }
}
