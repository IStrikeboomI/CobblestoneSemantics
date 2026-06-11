package Strikeboom.cobblestonesemantics.blockentities;

import Strikeboom.cobblestonesemantics.blockentities.energystorage.CobblestoneSemanticsEnergyStorage;
import Strikeboom.cobblestonesemantics.blockentities.fluidtanks.LavaGeneratorFluidTank;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlockEntities;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.fluid.FluidStacksResourceHandler;

import javax.annotation.Nullable;

public class LavaGeneratorBlockEntity extends BlockEntity {
    public final FluidStacksResourceHandler fluidTank;
    public final CobblestoneSemanticsEnergyStorage energyStorage;
    private int cooldown;
    private int delay;
    public LavaGeneratorBlockEntity( BlockPos pWorldPosition, BlockState pBlockState) {
        super(CobblestoneSemanticsBlockEntities.LAVA_GENERATOR_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
        fluidTank = new LavaGeneratorFluidTank(5000) {
            @Override
            protected void onContentsChanged(int index, FluidStack previousContents) {
                setChanged();
                level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(), Block.UPDATE_ALL);
            }
        };
        energyStorage = new CobblestoneSemanticsEnergyStorage(1000000,false,true) {
            @Override
            protected void onEnergyChanged() {
                if (level != null) {
                    setChanged();
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
                }
            }
        };
        cooldown = 0;
        delay = CobblestoneSemanticsConfig.LAVA_GENERATOR_DELAY.get();
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
        fluidTank.serialize(output);
        output.putInt("cooldown", cooldown);
        output.putInt("delay", delay);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        energyStorage.set(input.getInt("energy").orElseThrow());
        fluidTank.deserialize(input);
        cooldown = input.getInt("cooldown").orElseThrow();
        delay = input.getInt("delay").orElseThrow();
    }

    public void tickServer() {
        delay = CobblestoneSemanticsConfig.COBBLESTONE_MELTER_DELAY.get();
        boolean shouldUpdate = false;
        if (!fluidTank.getResource(0).isEmpty()
                && fluidTank.getAmountAsInt(0) >= 1000
                && energyStorage.getAmountAsInt() + CobblestoneSemanticsConfig.LAVA_GENERATOR_POWER_PER_LAVA_BUCKET.get() <= energyStorage.getCapacityAsInt()) {
            cooldown++;
            level.setBlockAndUpdate(getBlockPos(),getBlockState().setValue(BlockStateProperties.POWERED,true));
            shouldUpdate = true;
        } else {
            if (cooldown != 0) {
                cooldown = 0;
                if (fluidTank.getResource(0).isEmpty()) {
                    level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(BlockStateProperties.POWERED, false));
                }
                shouldUpdate = true;
            }
        }
        if (cooldown % delay == 0 && cooldown != 0) {
            cooldown = 0;
            fluidTank.set(0,fluidTank.getResource(0),Math.max(fluidTank.getAmountAsInt(0)-1000,0));
            if (fluidTank.getResource(0).isEmpty()) {
                level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(BlockStateProperties.POWERED, false));
            }
            energyStorage.addEnergy(CobblestoneSemanticsConfig.LAVA_GENERATOR_POWER_PER_LAVA_BUCKET.get(),null);
        }
        //send energy all around
        if (energyStorage.getAmountAsInt() > Direction.values().length) {
            for (Direction d : Direction.values()) {
                BlockPos offset = worldPosition.offset(d.getUnitVec3i());
                BlockEntity be = level.getBlockEntity(offset);
                if (be != null) {
                    EnergyHandler e = level.getCapability(Capabilities.Energy.BLOCK,offset,d);

                    if (e != null) {
                        int toSend = Math.min(energyStorage.getAmountAsInt() / Direction.values().length,e.getCapacityAsInt() - e.getAmountAsInt());
                        if (e.getCapacityAsInt() >= e.getAmountAsInt() + toSend - e.getAmountAsInt()) {
                            //divide up the energy so it distributes equally
                            int inserted = e.insert(energyStorage.extract(toSend,null),null);
                            //if other energy storage doesn't accept amount, send back
                            energyStorage.insert(toSend - inserted,null);
                            shouldUpdate = true;
                        }
                    }
                }
            }
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
