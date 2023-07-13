package Strikeboom.cobblestonesemantics.blockentities.itemhandlers;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
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
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return itemHandlerLazyOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.put("ItemStackHandler",handler.serializeNBT());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("ItemStackHandler")) {
            handler.deserializeNBT(nbt.getCompound("ItemStackHandler"));
        }
    }
}
