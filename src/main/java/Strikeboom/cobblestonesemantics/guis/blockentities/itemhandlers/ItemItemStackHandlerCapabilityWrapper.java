package Strikeboom.cobblestonesemantics.guis.blockentities.itemhandlers;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class ItemItemStackHandlerCapabilityWrapper implements ICapabilitySerializable<CompoundTag> {
    ItemStackHandler handler;
    private final LazyOptional<IItemHandler> itemHandlerLazyOptional;
    public ItemItemStackHandlerCapabilityWrapper(int size, Item item) {
        handler = new BagItemHandler(size,item);
        itemHandlerLazyOptional = LazyOptional.of(() -> handler);
    }


    @Override
    public <T> LazyOptional<T> getCapability( Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandlerLazyOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return handler.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        handler.deserializeNBT(nbt);
    }
}
