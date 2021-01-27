package Strikeboom.cobblestonesemantics.guis.tileentity.customs;

import net.minecraftforge.energy.EnergyStorage;

public class CobblestoneSemanticsEnergyStorage extends EnergyStorage {
    public CobblestoneSemanticsEnergyStorage(int capacity,boolean receivePower ,boolean sendPower) {
        super(capacity,receivePower ? capacity : 0,sendPower ? capacity : 0);
    }
    public void setEnergy(int energy) {
        this.energy = energy;
    }
    public void addEnergy(int energy) {
        this.energy += energy;
        if (this.energy > capacity) {this.energy = capacity;}
    }

}
