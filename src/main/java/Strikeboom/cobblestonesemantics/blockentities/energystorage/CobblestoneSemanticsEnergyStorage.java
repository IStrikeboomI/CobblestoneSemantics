package Strikeboom.cobblestonesemantics.blockentities.energystorage;


import net.neoforged.neoforge.energy.EnergyStorage;

public class CobblestoneSemanticsEnergyStorage extends EnergyStorage {
    public CobblestoneSemanticsEnergyStorage(int capacity, boolean receivePower , boolean sendPower) {
        super(capacity,receivePower ? capacity : 0,sendPower ? capacity : 0);
    }
    protected void onEnergyChanged() {}
    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int rc = super.extractEnergy(maxExtract, simulate);
        if (rc > 0 && !simulate) {
            onEnergyChanged();
        }
        return rc;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
        onEnergyChanged();
    }

    public void addEnergy(int energy) {
        this.energy += energy;
        if (this.energy > getMaxEnergyStored()) {
            this.energy = getEnergyStored();
        }
        onEnergyChanged();
    }

}
