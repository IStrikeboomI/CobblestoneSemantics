package Strikeboom.cobblestonesemantics.blockentities;

import Strikeboom.cobblestonesemantics.blockentities.itemhandlers.CobblestoneGeneratorItemHandler;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.EmptyItemHandler;

import javax.annotation.Nullable;

public class CobblestoneGeneratorBlockEntity extends BlockEntity {
    public final CobblestoneGeneratorItemHandler itemStackHandler;
    int delayUntilNextCobbleStone = 1;
    int amountOfCobblestoneEachOperation = 1;
    int cooldown = 0;
    public CobblestoneGeneratorBlockEntity( BlockPos pWorldPosition, BlockState pBlockState) {
        super(CobblestoneSemanticsBlockEntities.COBBLESTONE_GENERATOR_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
        itemStackHandler = new CobblestoneGeneratorItemHandler(1) {

            @Override
            public void onContentsChanged(int slot) {
                setChanged();
                level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(), Block.UPDATE_ALL);
            }
        };
    }
    public CobblestoneGeneratorBlockEntity( BlockPos pWorldPosition, BlockState pBlockState,int storageSlots,int delayUntilNextCobbleStone,int amountOfCobblestoneEachOperation) {
        super(CobblestoneSemanticsBlockEntities.COBBLESTONE_GENERATOR_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
        itemStackHandler = new CobblestoneGeneratorItemHandler(storageSlots)  {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(), Block.UPDATE_ALL);
            }
        };
        this.delayUntilNextCobbleStone = delayUntilNextCobbleStone;
        this.amountOfCobblestoneEachOperation = amountOfCobblestoneEachOperation;
    }
    @Override
    public void setRemoved() {
        super.setRemoved();
        level.invalidateCapabilities(getBlockPos());
        invalidateCapabilities();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag,HolderLookup.Provider registries) {
        pTag.put("ItemStackHandler",itemStackHandler.serializeNBT(registries));
        CompoundTag infoTag = new CompoundTag();
        infoTag.putInt("Cooldown", cooldown);
        infoTag.putInt("DelayUntilNextCobbleStone", delayUntilNextCobbleStone);
        infoTag.putInt("AmountOfCobblestoneEachOperation", amountOfCobblestoneEachOperation);
        pTag.put("Info", infoTag);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        if (pTag.contains("ItemStackHandler")) {
            itemStackHandler.deserializeNBT(registries,pTag.getCompound("ItemStackHandler"));
        }
        if (pTag.contains("Info")) {
            cooldown = pTag.getCompound("Info").getInt("Cooldown");
            delayUntilNextCobbleStone = pTag.getCompound("Info").getInt("DelayUntilNextCobbleStone");
            amountOfCobblestoneEachOperation = pTag.getCompound("Info").getInt("AmountOfCobblestoneEachOperation");
        }
        super.loadAdditional(pTag, registries);
    }


    public void tickServer() {
        cooldown++;
        if (cooldown % delayUntilNextCobbleStone == 0) {
            for (int i = 0; i<itemStackHandler.getSlots();i++) {
                if (!itemStackHandler.getStackInSlot(i).isEmpty()) {
                    if (itemStackHandler.getStackInSlot(i).getCount() + amountOfCobblestoneEachOperation <= itemStackHandler.getSlotLimit(i)) {
                        itemStackHandler.getStackInSlot(i).grow(amountOfCobblestoneEachOperation);
                        setChanged();
                        break;
                    }
                } else {
                    itemStackHandler.setStackInSlot(i,new ItemStack(Blocks.COBBLESTONE,amountOfCobblestoneEachOperation));
                    setChanged();
                    break;
                }
            }
            cooldown = 0;
        }

        //inputs cobble to the slot above or below
        BlockEntity upBE = level.getBlockEntity(this.worldPosition.above());
        if (upBE != null) {
            if (!(upBE instanceof CobblestoneGeneratorBlockEntity)) {
                IItemHandler cap = level.getCapability(Capabilities.ItemHandler.BLOCK,this.worldPosition.above(),Direction.DOWN);
                if (cap != null) {
                    if (!(cap instanceof EmptyItemHandler)) {
                        if (!itemStackHandler.getStackInSlot(0).isEmpty()) {
                            for (int i = 0; i < cap.getSlots(); i++) {
                                if (cap.getStackInSlot(i).getCount() < cap.getSlotLimit(i)) {
                                    int largestSlotIndex = itemStackHandler.getLargestSlotIndex();
                                    cap.insertItem(i, itemStackHandler.extractItem(largestSlotIndex, 64, false), false);
                                    setChanged();
                                }
                            }
                        }
                    }
                }
            }
        }
        BlockEntity downBE = level.getBlockEntity(this.worldPosition.below());
        if (downBE != null) {
            if (!(downBE instanceof CobblestoneGeneratorBlockEntity)) {
                IItemHandler cap = level.getCapability(Capabilities.ItemHandler.BLOCK,this.worldPosition.below(),Direction.UP);
                if (cap != null) {
                    if (!(cap instanceof EmptyItemHandler)) {
                        if (!itemStackHandler.getStackInSlot(0).isEmpty()) {
                            for (int i = 0; i < cap.getSlots(); i++) {
                                if (cap.getStackInSlot(i).getCount() < cap.getSlotLimit(i)) {
                                    cap.insertItem(i, itemStackHandler.extractItem(((CobblestoneGeneratorItemHandler) itemStackHandler).getLargestSlotIndex(), 64, false), false);
                                    setChanged();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public int getCobblestoneAmount() {
        int amount = 0;
        for (int i = 0;i < itemStackHandler.getSlots();i++) {
            amount += itemStackHandler.getStackInSlot(i).getCount();
        }
        return amount;
    }

}
