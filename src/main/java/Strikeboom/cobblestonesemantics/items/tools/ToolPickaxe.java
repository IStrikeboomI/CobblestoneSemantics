package Strikeboom.cobblestonesemantics.items.tools;


import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import net.minecraft.item.ItemPickaxe;

public class ToolPickaxe extends ItemPickaxe {
	public ToolPickaxe(ToolMaterial material,String name){
		super(material);
		setRegistryName(name);
		setUnlocalizedName(CobblestoneSemantics.MOD_ID + "." + name);

	}

}
