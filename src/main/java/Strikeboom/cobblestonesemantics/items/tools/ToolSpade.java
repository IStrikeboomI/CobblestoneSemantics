package Strikeboom.cobblestonesemantics.items.tools;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import net.minecraft.item.ItemSpade;

public class ToolSpade extends ItemSpade {
	public ToolSpade( ToolMaterial material,String name){
		super(material);
		setRegistryName(name);
		setUnlocalizedName(CobblestoneSemantics.MOD_ID + "." + name);

	}

}
