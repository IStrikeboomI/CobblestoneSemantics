package Strikeboom.cobblestonesemantics.blockentities;

import Strikeboom.cobblestonesemantics.blockentities.itemhandlers.CobblestoneGeneratorItemHandler;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.StacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;

public class CobblestoneGeneratorBlockEntity extends BlockEntity {
    public final CobblestoneGeneratorItemHandler itemStackHandler;
    int delayUntilNextCobbleStone = 1;
    int amountOfCobblestoneEachOperation = 1;
    int cooldown = 0;
    public CobblestoneGeneratorBlockEntity( BlockPos pWorldPosition, BlockState pBlockState) {
        super(CobblestoneSemanticsBlockEntities.COBBLESTONE_GENERATOR_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
        itemStackHandler = new CobblestoneGeneratorItemHandler(1) {
            @Override
            protected void onContentsChanged(int index, ItemStack previousContents) {
                setChanged();
                level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(), Block.UPDATE_ALL);
            }
        };
    }
    public CobblestoneGeneratorBlockEntity( BlockPos pWorldPosition, BlockState pBlockState,int storageSlots,int delayUntilNextCobbleStone,int amountOfCobblestoneEachOperation) {
        super(CobblestoneSemanticsBlockEntities.COBBLESTONE_GENERATOR_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
        itemStackHandler = new CobblestoneGeneratorItemHandler(storageSlots)  {
            @Override
            protected void onContentsChanged(int index, ItemStack previousContents) {
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
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        itemStackHandler.serialize(output);
        output.putInt("cooldown", cooldown);
        output.putInt("delayUntilNextCobbleStone", delayUntilNextCobbleStone);
        output.putInt("amountOfCobblestoneEachOperation", amountOfCobblestoneEachOperation);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        itemStackHandler.deserialize(input);
        cooldown = input.getInt("cooldown").orElseThrow();
        delayUntilNextCobbleStone = input.getInt("delayUntilNextCobbleStone").orElseThrow();
        amountOfCobblestoneEachOperation = input.getInt("amountOfCobblestoneEachOperation").orElseThrow();
    }


    public void tickServer() {
        cooldown++;
        if (cooldown % delayUntilNextCobbleStone == 0) {
            for (int i = 0; i<itemStackHandler.size();i++) {
                if (!itemStackHandler.getResource(i).isEmpty()) {
                    if (itemStackHandler.getAmountAsInt(i) + amountOfCobblestoneEachOperation <= itemStackHandler.getCapacityAsInt(i,itemStackHandler.getResource(i))) {
                        itemStackHandler.set(i,itemStackHandler.getResource(i),itemStackHandler.getAmountAsInt(i) + amountOfCobblestoneEachOperation);
                        setChanged();
                        break;
                    }
                } else {
                    itemStackHandler.set(i,ItemResource.of(new ItemStack(Blocks.COBBLESTONE)),amountOfCobblestoneEachOperation);
                    setChanged();
                    break;
                }
            }
            cooldown = 0;
        }

        //inputs cobble to the slot above or below
        insertCobble(Direction.UP);
        insertCobble(Direction.DOWN);
    }
    private void insertCobble(Direction target) {
        BlockEntity upBE = level.getBlockEntity(this.worldPosition.relative(target));
        if (upBE != null) {
            if (!(upBE instanceof CobblestoneGeneratorBlockEntity)) {
                StacksResourceHandler<ItemStack,ItemResource> cap = (StacksResourceHandler<ItemStack, ItemResource>) level.getCapability(Capabilities.Item.BLOCK,this.worldPosition.above(),target.getOpposite());
                if (cap != null) {
                    if (getCobblestoneAmount() > 0) {
                        for (int i = 0; i < cap.size(); i++) {
                            if (cap.getAmountAsInt(i) < 64) {
                                int largestSlotIndex = itemStackHandler.getLargestSlotIndex();
                                int toInsert = Math.min(64,getCobblestoneAmount());
                                int inserted = cap.insert(cap.getResource(i), itemStackHandler.extract(itemStackHandler.getResource(largestSlotIndex), toInsert, null), null);
                                setChanged();
                            }
                        }
                    }
                }
            }
        }
    }
    public int getCobblestoneAmount() {
        int amount = 0;
        for (int i = 0;i < itemStackHandler.size();i++) {
            amount += itemStackHandler.getAmountAsInt(i);
        }
        return amount;
    }

}
