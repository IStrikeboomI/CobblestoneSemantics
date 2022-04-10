package Strikeboom.cobblestonesemantics.guis.tileentities;

import Strikeboom.cobblestonesemantics.guis.tileentities.energystorage.CobblestoneSemanticsEnergyStorage;
import Strikeboom.cobblestonesemantics.guis.tileentities.fluidtanks.LavaGeneratorFluidTank;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsConfig;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nullable;
import java.util.Objects;

public class LavaGeneratorTileEntity extends TileEntity implements ITickableTileEntity {
    private final FluidTank fluidTank;
    private final LazyOptional<IFluidHandler> fluidHandlerLazyOptional;
    private final CobblestoneSemanticsEnergyStorage energyStorage;
    private final LazyOptional<IEnergyStorage> energyLazyOptional;
    private int cooldown;
    private int delay;
    public LavaGeneratorTileEntity() {
        super(CobblestoneSemanticsTileEntities.LAVA_GENERATOR_BLOCK_ENTITY.get());
        fluidTank = new LavaGeneratorFluidTank(FluidAttributes.BUCKET_VOLUME * 5) {
            @Override
            protected void onContentsChanged() {
                setChanged();
                level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(), 3);
            }
        };
        fluidHandlerLazyOptional = LazyOptional.of(() -> fluidTank);
        energyStorage = new CobblestoneSemanticsEnergyStorage(1000000,false,true) {
            @Override
            protected void onEnergyChanged() {
                if (level != null) {
                    setChanged();
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
                }            }
        };
        energyLazyOptional = LazyOptional.of(() -> energyStorage);
        cooldown = 0;
        delay = CobblestoneSemanticsConfig.LAVA_GENERATOR_DELAY.get();
    }
    @Override
    public void setRemoved() {
        super.setRemoved();
        energyLazyOptional.invalidate();
        fluidHandlerLazyOptional.invalidate();
    }
    @Override
    public CompoundNBT save(CompoundNBT pTag) {
        pTag.putInt("energy",energyStorage.getEnergyStored());
        fluidTank.writeToNBT(pTag);
        CompoundNBT infoTag = new CompoundNBT();
        infoTag.putInt("Cooldown", cooldown);
        infoTag.putInt("Delay", delay);
        pTag.put("Info", infoTag);
        return super.save(pTag);
    }

    @Override
    public void load(BlockState state, CompoundNBT pTag) {
        if (pTag.contains("energy")) {
            energyStorage.setEnergy(pTag.getInt("energy"));
        }
        fluidTank.readFromNBT(pTag);
        if (pTag.contains("Info")) {
            cooldown = pTag.getCompound("Info").getInt("Cooldown");
            delay = pTag.getCompound("Info").getInt("Delay");
        }
        super.load(state,pTag);
    }
    public void tick() {
        if (!level.isClientSide) {
            delay = CobblestoneSemanticsConfig.COBBLESTONE_MELTER_DELAY.get();
            boolean shouldUpdate = false;
            if (!fluidTank.isEmpty()
                    && fluidTank.getFluid().getAmount() >= FluidAttributes.BUCKET_VOLUME
                    && energyStorage.getEnergyStored() + CobblestoneSemanticsConfig.LAVA_GENERATOR_POWER_PER_LAVA_BUCKET.get() <= energyStorage.getMaxEnergyStored()) {
                cooldown++;
                level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(BlockStateProperties.POWERED, true));
                shouldUpdate = true;
            } else {
                if (cooldown != 0) {
                    cooldown = 0;
                    if (fluidTank.getFluid().isEmpty()) {
                        level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(BlockStateProperties.POWERED, false));
                    }
                    shouldUpdate = true;
                }
            }
            if (cooldown % delay == 0 && cooldown != 0) {
                cooldown = 0;
                fluidTank.getFluid().shrink(FluidAttributes.BUCKET_VOLUME);
                if (fluidTank.getFluid().isEmpty()) {
                    level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(BlockStateProperties.POWERED, false));
                }
                energyStorage.addEnergy(CobblestoneSemanticsConfig.LAVA_GENERATOR_POWER_PER_LAVA_BUCKET.get());
            }
            if (shouldUpdate) {
                setChanged();
                this.level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
        }
    }
    
    @Override
    public <T> LazyOptional<T> getCapability( Capability<T> cap, final @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY) {
            return energyLazyOptional.cast();
        }
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidHandlerLazyOptional.cast();
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
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        if (tag != null) {
            load(state, tag);
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
        FluidTank oldFluidTank = fluidTank;

        CompoundNBT tag = pkt.getTag();
        // This will call loadClientData()
        handleUpdateTag(null,tag);

        // If any of the values was changed we request a refresh of our model data and send a block update
        if (oldCooldown != cooldown || oldDelay != delay ||
                !Objects.equals(oldEnergyStorage,energyStorage) ||
                !Objects.equals(oldFluidTank, fluidTank)) {
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
