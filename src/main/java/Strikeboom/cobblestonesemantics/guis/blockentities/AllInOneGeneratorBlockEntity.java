package Strikeboom.cobblestonesemantics.guis.blockentities;

import Strikeboom.cobblestonesemantics.guis.blockentities.energystorage.CobblestoneSemanticsEnergyStorage;
import Strikeboom.cobblestonesemantics.guis.blockentities.itemhandlers.AllInOneGeneratorItemHandler;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlockEntities;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;

public class AllInOneGeneratorBlockEntity extends BlockEntity {
    private final AllInOneGeneratorItemHandler itemStackHandler;
    private final LazyOptional<IItemHandler> itemHandlerLazyOptional;
    private final CobblestoneSemanticsEnergyStorage energyStorage;
    private final LazyOptional<IEnergyStorage> energyLazyOptional;
    private int cooldown;
    private int delay;
    public AllInOneGeneratorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(CobblestoneSemanticsBlockEntities.ALL_IN_ONE_GENERATOR_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
        itemStackHandler = new AllInOneGeneratorItemHandler();
        itemHandlerLazyOptional = LazyOptional.of(() -> itemStackHandler);
        energyStorage = new CobblestoneSemanticsEnergyStorage(10000000,false,true);
        energyLazyOptional = LazyOptional.of(() -> energyStorage);
        cooldown = 0;
        delay = 100;
    }
    @Override
    public void setRemoved() {
        super.setRemoved();
        itemHandlerLazyOptional.invalidate();
        energyLazyOptional.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.putInt("energy",energyStorage.getEnergyStored());
        pTag.put("ItemStackHandler",itemStackHandler.serializeNBT());
        CompoundTag infoTag = new CompoundTag();
        infoTag.putInt("Cooldown", cooldown);
        infoTag.putInt("Delay", delay);
        pTag.put("Info", infoTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        if (pTag.contains("ItemStackHandler")) {
            itemStackHandler.deserializeNBT(pTag.getCompound("ItemStackHandler"));
        }
        if (pTag.contains("energy")) {
            energyStorage.setEnergy(pTag.getInt("energy"));
        }
        if (pTag.contains("Info")) {
            cooldown = pTag.getCompound("Info").getInt("Cooldown");
            delay = pTag.getCompound("Info").getInt("Delay");
        }
        super.load(pTag);
    }
    public void tickServer() {
        boolean shouldUpdate = false;

        if (!itemStackHandler.getStackInSlot(0).isEmpty() &&
                itemStackHandler.getLavaGenerators() > 0 &&
                itemStackHandler.getLavaMelters() > 0 &&
                energyStorage.getEnergyStored() + ( CobblestoneSemanticsConfig.LAVA_GENERATOR_POWER_PER_LAVA_BUCKET.get() * (int)(Math.floor(itemStackHandler.getLavaGenerators() / 2f) < itemStackHandler.getLavaMelters() ? Math.floor(itemStackHandler.getLavaGenerators() / 2f) :itemStackHandler.getLavaMelters())) <= energyStorage.getMaxEnergyStored()) {

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
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, final @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandlerLazyOptional.cast();
        }
        if (cap == CapabilityEnergy.ENERGY) {
            return energyLazyOptional.cast();
        }
        return super.getCapability(cap,side);
    }
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        if (tag != null) {
            load(tag);
        }
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        // This is called client side: remember the current state of the values that we're interested in
        int oldCooldown = cooldown;
        int oldDelay = delay;
        CobblestoneSemanticsEnergyStorage oldEnergyStorage = energyStorage;
        ItemStackHandler oldItemStackHandler = itemStackHandler;

        CompoundTag tag = pkt.getTag();
        // This will call loadClientData()
        handleUpdateTag(tag);

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
