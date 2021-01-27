package Strikeboom.cobblestonesemantics.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class CobblestoneInfusedObsidian extends Block {
    public CobblestoneInfusedObsidian() {
        super(Material.ROCK, MapColor.BLACK);
        setSoundType(SoundType.STONE);
        setHardness(40f);
        setResistance(2500f);
    }
}
