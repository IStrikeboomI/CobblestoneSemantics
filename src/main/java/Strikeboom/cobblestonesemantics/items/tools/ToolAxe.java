package Strikeboom.cobblestonesemantics.items.tools;


import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import net.minecraft.item.ItemAxe;

public class ToolAxe extends ItemAxe {
	public ToolAxe(ToolMaterial material,String name){
		super(material, 6.0F, -3.2F);
		setRegistryName(name);
		setUnlocalizedName(CobblestoneSemantics.MOD_ID + "." + name);
	}
}
