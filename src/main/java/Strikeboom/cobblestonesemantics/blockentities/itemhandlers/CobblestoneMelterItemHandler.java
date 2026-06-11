package Strikeboom.cobblestonesemantics.blockentities.itemhandlers;

import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

public class CobblestoneMelterItemHandler extends ItemStacksResourceHandler {
    public CobblestoneMelterItemHandler(int size) {
        super(size);
    }

    @Override
    public boolean isValid(int index, ItemResource resource) {
        return resource.is(Tags.Items.COBBLESTONES) || resource.is(Tags.Items.STONES);
    }

    @Override
    public int insert(int index, ItemResource resource, int amount, TransactionContext transaction) {
        if (!resource.is(Tags.Items.COBBLESTONES) && !resource.is(Tags.Items.STONES)) {
            return 0;
        }
        return super.insert(index, resource, amount, transaction);
    }
}
