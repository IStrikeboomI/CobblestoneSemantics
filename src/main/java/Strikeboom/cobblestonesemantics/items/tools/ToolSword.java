package Strikeboom.cobblestonesemantics.items.tools;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import net.minecraft.item.ItemSword;

public class ToolSword extends ItemSword {
	public ToolSword(ToolMaterial material,String name){
		super(material);
		setRegistryName(name);
		setUnlocalizedName(CobblestoneSemantics.MOD_ID + "." + name);

	}

}
