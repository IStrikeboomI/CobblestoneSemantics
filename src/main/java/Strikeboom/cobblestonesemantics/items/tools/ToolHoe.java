package Strikeboom.cobblestonesemantics.items.tools;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import net.minecraft.item.ItemHoe;

public class ToolHoe extends ItemHoe {
	public ToolHoe(ToolMaterial material,String name){
		super(material);
		setRegistryName(name);
		setUnlocalizedName(CobblestoneSemantics.MOD_ID + "." + name);

	}


}
