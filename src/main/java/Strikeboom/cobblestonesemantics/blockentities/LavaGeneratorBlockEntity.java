package Strikeboom.cobblestonesemantics.blockentities;

import Strikeboom.cobblestonesemantics.blockentities.energystorage.CobblestoneSemanticsEnergyStorage;
import Strikeboom.cobblestonesemantics.blockentities.fluidtanks.LavaGeneratorFluidTank;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlockEntities;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.fluid.FluidStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import javax.annotation.Nullable;
import java.util.Objects;

public class LavaGeneratorBlockEntity extends BlockEntity {
    public final FluidStacksResourceHandler fluidTank;
    public final CobblestoneSemanticsEnergyStorage energyStorage;
    private int cooldown;
    private int delay;
    public LavaGeneratorBlockEntity( BlockPos pWorldPosition, BlockState pBlockState) {
        super(CobblestoneSemanticsBlockEntities.LAVA_GENERATOR_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
        fluidTank = new LavaGeneratorFluidTank() {
            @Override
            protected void onContentsChanged(int index, FluidStack previousContents) {
                setChanged();
                level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(), Block.UPDATE_ALL);
            }
        };
        energyStorage = new CobblestoneSemanticsEnergyStorage(1000000) {
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
        delay = CobblestoneSemanticsConfig.LAVA_GENERATOR_DELAY.get();
        boolean shouldUpdate = false;
        if (!fluidTank.getResource(0).isEmpty()
                && fluidTank.getAmountAsInt(0) >= FluidType.BUCKET_VOLUME
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
            try (Transaction tx = Transaction.openRoot()) {
                int energy = energyStorage.insert(CobblestoneSemanticsConfig.LAVA_GENERATOR_POWER_PER_LAVA_BUCKET.get(),tx);
                if (energy != 0) {
                    tx.commit();
                }
            }

        }
        //send energy all around
        if (energyStorage.getAmountAsInt() > Direction.values().length) {
            for (Direction d : Direction.values()) {
                BlockPos offset = worldPosition.offset(d.getUnitVec3i());
                BlockEntity be = level.getBlockEntity(offset);
                if (be != null) {
                    EnergyHandler e = level.getCapability(Capabilities.Energy.BLOCK,offset,d);
                    if (e != null) {
                        int toSend = Math.min(energyStorage.getAmountAsInt(),10000);
                        if (e.getCapacityAsInt() >= e.getAmountAsInt() + toSend) {
                            try (Transaction tx = Transaction.openRoot()) {
                                int inserted = e.insert(energyStorage.extract(toSend,tx),tx);
                                if (inserted !=0) {tx.commit();}
                            }
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
    public void handleUpdateTag(ValueInput input) {
        super.handleUpdateTag(input);
        loadAdditional(input);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        super.getUpdateTag(registries);
        return this.saveWithoutMetadata(registries);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ValueInput valueInput) {
        super.onDataPacket(net, valueInput);
        int oldCooldown = cooldown;
        int oldDelay = delay;
        CobblestoneSemanticsEnergyStorage oldEnergyStorage = energyStorage;
        FluidStacksResourceHandler oldFluidTank = fluidTank;

        // This will call loadClientData()
        handleUpdateTag(valueInput);

        // If any of the values was changed we request a refresh of our model data and send a block update
        if (oldCooldown != cooldown || oldDelay != delay ||
                !Objects.equals(oldEnergyStorage,energyStorage) ||
                !Objects.equals(oldFluidTank, fluidTank)) {
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
