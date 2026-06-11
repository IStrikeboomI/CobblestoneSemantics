package Strikeboom.cobblestonesemantics.blockentities.energystorage;


import net.neoforged.neoforge.transfer.energy.SimpleEnergyHandler;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

public class CobblestoneSemanticsEnergyStorage extends SimpleEnergyHandler {
    public CobblestoneSemanticsEnergyStorage(int capacity, boolean receivePower , boolean sendPower) {
        super(capacity,receivePower ? capacity : 0,sendPower ? capacity : 0);
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

    public void addEnergy(int energy,TransactionContext transaction) {
        insert(energy,transaction);
        onEnergyChanged();
    }

}
