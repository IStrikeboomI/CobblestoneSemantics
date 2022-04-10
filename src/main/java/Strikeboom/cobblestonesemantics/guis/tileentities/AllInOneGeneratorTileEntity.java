package Strikeboom.cobblestonesemantics.guis.tileentities;

import Strikeboom.cobblestonesemantics.guis.tileentities.energystorage.CobblestoneSemanticsEnergyStorage;
import Strikeboom.cobblestonesemantics.guis.tileentities.itemhandlers.AllInOneGeneratorItemHandler;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsConfig;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Objects;

public class AllInOneGeneratorTileEntity extends TileEntity implements ITickableTileEntity {
    private final AllInOneGeneratorItemHandler itemStackHandler;
    private final LazyOptional<IItemHandler> itemHandlerLazyOptional;
    private final CobblestoneSemanticsEnergyStorage energyStorage;
    private final LazyOptional<IEnergyStorage> energyLazyOptional;
    private int cooldown;
    private int delay;
    public AllInOneGeneratorTileEntity() {
        super(CobblestoneSemanticsTileEntities.ALL_IN_ONE_GENERATOR_BLOCK_ENTITY.get());
        itemStackHandler = new AllInOneGeneratorItemHandler() {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(),3);
            }
        };
        itemHandlerLazyOptional = LazyOptional.of(() -> itemStackHandler);
        energyStorage = new CobblestoneSemanticsEnergyStorage(10000000,false,true) {
            @Override
            protected void onEnergyChanged() {
                if (level != null) {
                    setChanged();
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
                }
            }
        };
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
    public CompoundNBT save(CompoundNBT pTag) {
        pTag.putInt("energy",energyStorage.getEnergyStored());
        pTag.put("ItemStackHandler",itemStackHandler.serializeNBT());
        CompoundNBT infoTag = new CompoundNBT();
        infoTag.putInt("Cooldown", cooldown);
        infoTag.putInt("Delay", delay);
        pTag.put("Info", infoTag);
        return super.save(pTag);
    }

    @Override
    public void load(BlockState state, CompoundNBT pTag) {
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
        super.load(state,pTag);
    }
    @Override
    public void tick() {
        if (!level.isClientSide) {
            boolean shouldUpdate = false;
            int energyProduced = CobblestoneSemanticsConfig.LAVA_GENERATOR_POWER_PER_LAVA_BUCKET.get() * (int) (Math.floor(itemStackHandler.getLavaGenerators() / 2f) < itemStackHandler.getLavaMelters() ? Math.floor(itemStackHandler.getLavaGenerators() / 2f) : itemStackHandler.getLavaMelters());
            if (!itemStackHandler.getStackInSlot(0).isEmpty() &&
                    itemStackHandler.getLavaGenerators() > 0 &&
                    itemStackHandler.getLavaMelters() > 0 &&
                    energyProduced != 0 &&
                    energyStorage.getEnergyStored() + energyProduced <= energyStorage.getMaxEnergyStored()) {

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
                energyStorage.addEnergy(energyProduced);
            }
            if (shouldUpdate) {
                setChanged();
                this.level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability( Capability<T> cap, final @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandlerLazyOptional.cast();
        }
        if (cap == CapabilityEnergy.ENERGY) {
            return energyLazyOptional.cast();
        }
        return super.getCapability(cap,side);
    }
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT tag = super.getUpdateTag();
        save(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(BlockState state,CompoundNBT tag) {
        if (tag != null) {
            load(state,tag);
        }
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(worldPosition,3,getUpdateTag());
    }


    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        // This is called client side: remember the current state of the values that we're interested in
        int oldCooldown = cooldown;
        int oldDelay = delay;
        CobblestoneSemanticsEnergyStorage oldEnergyStorage = energyStorage;
        ItemStackHandler oldItemStackHandler = itemStackHandler;

        CompoundNBT tag = pkt.getTag();
        // This will call loadClientData()
        handleUpdateTag(null,tag);

        // If any of the values was changed we request a refresh of our model data and send a block update
        if (oldCooldown != cooldown || oldDelay != delay ||
                !Objects.equals(oldEnergyStorage,energyStorage) ||
                !Objects.equals(oldItemStackHandler, itemStackHandler)) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getDelay() {
        return delay;
    }
}
