package Strikeboom.cobblestonesemantics.blockentities.itemhandlers;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

public class BagItemHandler extends ItemStacksResourceHandler {
    Item item;
    public BagItemHandler(int size, Item item) {
        super(size);
        this.item = item;
    }

    @Override
    public int insert(int index, ItemResource resource, int amount, TransactionContext transaction) {
        if (resource.getItem() == item) {
            return 0;
        }
        return super.insert(index, resource, amount, transaction);
    }

}
