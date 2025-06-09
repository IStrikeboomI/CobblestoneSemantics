package Strikeboom.cobblestonesemantics.blockentities;

import Strikeboom.cobblestonesemantics.blockentities.energystorage.CobblestoneSemanticsEnergyStorage;
import Strikeboom.cobblestonesemantics.blockentities.itemhandlers.AllInOneGeneratorItemHandler;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlockEntities;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;


import javax.annotation.Nullable;
import java.util.Objects;

public class AllInOneGeneratorBlockEntity extends BlockEntity {
    public final AllInOneGeneratorItemHandler itemStackHandler;
    public final CobblestoneSemanticsEnergyStorage energyStorage;
    private int cooldown;
    private int delay;
    public AllInOneGeneratorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(CobblestoneSemanticsBlockEntities.ALL_IN_ONE_GENERATOR_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
        itemStackHandler = new AllInOneGeneratorItemHandler() {
            @Override
            protected void onContentsChanged(int slot) {
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
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        super.saveAdditional(pTag, registries);
        pTag.putInt("energy",energyStorage.getEnergyStored());
        pTag.put("ItemStackHandler",itemStackHandler.serializeNBT(registries));
        CompoundTag infoTag = new CompoundTag();
        infoTag.putInt("Cooldown", cooldown);
        infoTag.putInt("Delay", delay);
        pTag.put("Info", infoTag);
    }

    @Override
    public void loadAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        super.loadAdditional(pTag,registries);
        if (pTag.contains("ItemStackHandler")) {
            itemStackHandler.deserializeNBT(registries,pTag.getCompound("ItemStackHandler").orElseThrow());
        }
        if (pTag.contains("energy")) {
            energyStorage.setEnergy(pTag.getInt("energy").orElseThrow());
        }
        if (pTag.contains("Info")) {
            cooldown = pTag.getCompound("Info").orElseThrow().getInt("Cooldown").orElseThrow();
            delay = pTag.getCompound("Info").orElseThrow().getInt("Delay").orElseThrow();
        }
    }
    public void tickServer() {
        boolean shouldUpdate = false;

        if (!itemStackHandler.getStackInSlot(0).isEmpty() &&
                itemStackHandler.getLavaGenerators() >= 2 &&
                itemStackHandler.getLavaMelters() >= 1 &&
                energyStorage.getEnergyStored() + (CobblestoneSemanticsConfig.LAVA_GENERATOR_POWER_PER_LAVA_BUCKET.get() * (int)(Math.floor(itemStackHandler.getLavaGenerators() / 2f) < itemStackHandler.getLavaMelters() ? Math.floor(itemStackHandler.getLavaGenerators() / 2f) :itemStackHandler.getLavaMelters())) <= energyStorage.getMaxEnergyStored()) {

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
            energyStorage.addEnergy(CobblestoneSemanticsConfig.LAVA_GENERATOR_POWER_PER_LAVA_BUCKET.get() * (int)(Math.floor(itemStackHandler.getLavaGenerators() / 2f) < itemStackHandler.getLavaMelters() ? Math.floor(itemStackHandler.getLavaGenerators() / 2f) :itemStackHandler.getLavaMelters()));
        }
        if (shouldUpdate) {
            setChanged();
            this.level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(),Block.UPDATE_ALL);
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditional(tag,registries);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        super.handleUpdateTag(tag, lookupProvider);
        loadAdditional(tag,lookupProvider);
    }


    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        super.onDataPacket(net, pkt, lookupProvider);
        int oldCooldown = cooldown;
        int oldDelay = delay;
        CobblestoneSemanticsEnergyStorage oldEnergyStorage = energyStorage;
        ItemStackHandler oldItemStackHandler = itemStackHandler;

        CompoundTag tag = pkt.getTag();
        // This will call loadClientData()
        handleUpdateTag(tag,lookupProvider);

        // If any of the values was changed we request a refresh of our model data and send a block update
        if (oldCooldown != cooldown || oldDelay != delay ||
                !Objects.equals(oldEnergyStorage,energyStorage) ||
                !Objects.equals(oldItemStackHandler, itemStackHandler)) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getDelay() {
        return delay;
    }
}
