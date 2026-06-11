package Strikeboom.cobblestonesemantics.blockentities;

import Strikeboom.cobblestonesemantics.blockentities.energystorage.CobblestoneSemanticsEnergyStorage;
import Strikeboom.cobblestonesemantics.blockentities.itemhandlers.AllInOneGeneratorItemHandler;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlockEntities;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import javax.annotation.Nullable;

public class AllInOneGeneratorBlockEntity extends BlockEntity {
    public final AllInOneGeneratorItemHandler itemStackHandler;
    public final CobblestoneSemanticsEnergyStorage energyStorage;
    private int cooldown;
    private int delay;
    public AllInOneGeneratorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(CobblestoneSemanticsBlockEntities.ALL_IN_ONE_GENERATOR_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
        itemStackHandler = new AllInOneGeneratorItemHandler() {

            @Override
            protected void onContentsChanged(int slot, ItemStack previousContents) {
                setChanged();
                if (level != null) {
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
                }
            }
        };
        energyStorage = new CobblestoneSemanticsEnergyStorage(10000000,false,true) {
            @Override
            protected void onEnergyChanged() {
                if (level != null) {
                    setChanged();
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
                }
            }
        };
        cooldown = 0;
        delay = 100;
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
        output.putInt("energy",energyStorage.getAmountAsInt());
        itemStackHandler.serialize(output);
        output.putInt("cooldown", cooldown);
        output.putInt("delay", delay);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        itemStackHandler.deserialize(input);
        energyStorage.set(input.getInt("energy").orElseThrow());
        cooldown = input.getInt("cooldown").orElseThrow();
        delay = input.getInt("delay").orElseThrow();
    }

    public void tickServer() {
        boolean shouldUpdate = false;

        if (!itemStackHandler.getResource(0).isEmpty() &&
                itemStackHandler.getLavaGenerators() >= 2 &&
                itemStackHandler.getLavaMelters() >= 1 &&
                energyStorage.getAmountAsInt() + (CobblestoneSemanticsConfig.LAVA_GENERATOR_POWER_PER_LAVA_BUCKET.get() * (int)(Math.floor(itemStackHandler.getLavaGenerators() / 2f) < itemStackHandler.getLavaMelters() ? Math.floor(itemStackHandler.getLavaGenerators() / 2f) :itemStackHandler.getLavaMelters())) <= energyStorage.getCapacityAsInt()) {

            cooldown += itemStackHandler.getCobbleGenTier();
            if (cooldown > delay) {
                cooldown = delay;
            }
            shouldUpdate = true;
        } else {
            if (cooldown != 0) {
                cooldown = 0;
                shouldUpdate = true;
            }
        }
        if (cooldown % delay == 0 && cooldown != 0) {
            cooldown = 0;
            energyStorage.addEnergy(CobblestoneSemanticsConfig.LAVA_GENERATOR_POWER_PER_LAVA_BUCKET.get() * (int)(Math.floor(itemStackHandler.getLavaGenerators() / 2f) < itemStackHandler.getLavaMelters() ? Math.floor(itemStackHandler.getLavaGenerators() / 2f) :itemStackHandler.getLavaMelters()),null);
        }
        if (shouldUpdate) {
            setChanged();
            this.level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(),Block.UPDATE_ALL);
        }
    }


    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }


    public int getCooldown() {
        return cooldown;
    }

    public int getDelay() {
        return delay;
    }
}
