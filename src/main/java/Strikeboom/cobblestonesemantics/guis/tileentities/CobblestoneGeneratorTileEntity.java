package Strikeboom.cobblestonesemantics.guis.tileentities;

import Strikeboom.cobblestonesemantics.guis.tileentities.itemhandlers.CobblestoneGeneratorItemHandler;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.EmptyHandler;

import javax.annotation.Nullable;

public class CobblestoneGeneratorTileEntity extends TileEntity implements ITickableTileEntity {
    ItemStackHandler itemStackHandler;
    private final LazyOptional<IItemHandler> itemHandlerLazyOptional;
    int delayUntilNextCobbleStone = 1;
    int amountOfCobblestoneEachOperation = 1;
    int cooldown = 0;

    public CobblestoneGeneratorTileEntity() {
        super(CobblestoneSemanticsTileEntities.COBBLESTONE_GENERATOR_BLOCK_ENTITY.get());
        itemStackHandler = new CobblestoneGeneratorItemHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(),3);
            }
        };
        itemHandlerLazyOptional = LazyOptional.of(() -> itemStackHandler);
    }
    public CobblestoneGeneratorTileEntity(int storageSlots, int delayUntilNextCobbleStone, int amountOfCobblestoneEachOperation) {
        super(CobblestoneSemanticsTileEntities.COBBLESTONE_GENERATOR_BLOCK_ENTITY.get());
        itemStackHandler = new CobblestoneGeneratorItemHandler(storageSlots)  {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(),3);
            }
        };
        itemHandlerLazyOptional = LazyOptional.of(() -> itemStackHandler);
        this.delayUntilNextCobbleStone = delayUntilNextCobbleStone;
        this.amountOfCobblestoneEachOperation = amountOfCobblestoneEachOperation;
    }
    @Override
    public void setRemoved() {
        super.setRemoved();
        itemHandlerLazyOptional.invalidate();
    }

    @Override
    public CompoundNBT save(CompoundNBT pTag) {
        pTag.put("ItemStackHandler",itemStackHandler.serializeNBT());
        CompoundNBT infoTag = new CompoundNBT();
        infoTag.putInt("Cooldown", cooldown);
        infoTag.putInt("DelayUntilNextCobbleStone", delayUntilNextCobbleStone);
        infoTag.putInt("AmountOfCobblestoneEachOperation", amountOfCobblestoneEachOperation);
        pTag.put("Info", infoTag);
        return super.save(pTag);
    }

    @Override
    public void load(BlockState state, CompoundNBT pTag) {
        if (pTag.contains("ItemStackHandler")) {
            itemStackHandler.deserializeNBT(pTag.getCompound("ItemStackHandler"));
        }
        if (pTag.contains("Info")) {
            cooldown = pTag.getCompound("Info").getInt("Cooldown");
            delayUntilNextCobbleStone = pTag.getCompound("Info").getInt("DelayUntilNextCobbleStone");
            amountOfCobblestoneEachOperation = pTag.getCompound("Info").getInt("AmountOfCobblestoneEachOperation");
        }
        super.load(state, pTag);
    }

    public void tick() {
        if (!level.isClientSide) {
            cooldown++;
            if (cooldown % delayUntilNextCobbleStone == 0) {
                for (int i = 0; i < itemStackHandler.getSlots(); i++) {
                    if (!itemStackHandler.getStackInSlot(i).isEmpty()) {
                        if (itemStackHandler.getStackInSlot(i).getCount() < itemStackHandler.getSlotLimit(i)) {
                            itemStackHandler.getStackInSlot(i).grow(amountOfCobblestoneEachOperation);
                            setChanged();
                            break;
                        }
                    } else {
                        itemStackHandler.setStackInSlot(i, new ItemStack(Blocks.COBBLESTONE));
                        setChanged();
                        break;
                    }
                }
            }

            //inputs cobble to the slot above or below
            TileEntity upBE = level.getBlockEntity(this.worldPosition.above());
            if (upBE != null) {
                if (upBE.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN).isPresent()) {
                    IItemHandler upHandler = upBE.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN).orElse(new EmptyHandler());
                    if (!(upHandler instanceof EmptyHandler)) {
                        if (!itemStackHandler.getStackInSlot(0).isEmpty()) {
                            for (int i = 0; i < upHandler.getSlots(); i++) {
                                if (upHandler.getStackInSlot(i).getCount() < upHandler.getSlotLimit(i)) {
                                    upHandler.insertItem(i, itemStackHandler.extractItem(((CobblestoneGeneratorItemHandler) itemStackHandler).getLargestSlotIndex(), 64, false), false);
                                    setChanged();
                                }
                            }
                        }
                    }
                }
            }
            TileEntity downBE = level.getBlockEntity(this.worldPosition.below());
            if (downBE != null) {
                if (downBE.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).isPresent()) {
                    IItemHandler downHandler = downBE.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).orElse(new EmptyHandler());
                    if (!(downHandler instanceof EmptyHandler)) {
                        if (!itemStackHandler.getStackInSlot(0).isEmpty()) {
                            for (int i = 0; i < downHandler.getSlots(); i++) {
                                if (downHandler.getStackInSlot(i).getCount() < downHandler.getSlotLimit(i)) {
                                    downHandler.insertItem(i, itemStackHandler.extractItem(((CobblestoneGeneratorItemHandler) itemStackHandler).getLargestSlotIndex(), 64, false), false);
                                    setChanged();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
//
    public int getCobblestoneAmount() {
        int amount = 0;
        for (int i = 0;i < itemStackHandler.getSlots();i++) {
            amount += itemStackHandler.getStackInSlot(i).getCount();
        }
        return amount;
    }

    
    @Override
    public <T> LazyOptional<T> getCapability( Capability<T> cap, final @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandlerLazyOptional.cast();
        }
        return super.getCapability(cap,side);
    }
}
