package Strikeboom.cobblestonesemantics.blockentities.energystorage;


import net.neoforged.neoforge.transfer.energy.SimpleEnergyHandler;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

public class CobblestoneSemanticsEnergyStorage extends SimpleEnergyHandler {
    public CobblestoneSemanticsEnergyStorage(int capacity) {
        super(capacity,capacity,capacity);
    }
    protected void onEnergyChanged() {}

    @Override
    public int insert(int amount, TransactionContext transaction) {
        onEnergyChanged();
        return super.insert(amount, transaction);
    }

    @Override
    public int extract(int amount, TransactionContext transaction) {
        onEnergyChanged();
        return super.extract(amount, transaction);
    }

    @Override
    public void set(int amount) {
        super.set(amount);
        onEnergyChanged();
    }

}
