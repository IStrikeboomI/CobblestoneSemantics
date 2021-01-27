package Strikeboom.cobblestonesemantics.handlers;

import Strikeboom.cobblestonesemantics.init.ModBlocks;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictHandler {
    public static void init() {
        OreDictionary.registerOre("cobblestoneinfusedobsidian", ModBlocks.cobblestoneInfusedObsidian);
    }
}
