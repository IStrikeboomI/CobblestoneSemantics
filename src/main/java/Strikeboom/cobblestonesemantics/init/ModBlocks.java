package Strikeboom.cobblestonesemantics.init;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

import java.util.LinkedList;
import java.util.List;

public class ModBlocks {
    public static final List<Block> BLOCKS = new LinkedList<>();

    public static Block cobbleGenerator1;
    public static Block cobbleGenerator2;
    public static Block cobbleGenerator3;
    public static Block cobbleGenerator4;
    public static Block cobbleGenerator5;
    public static Block cobbleGenerator6;
    public static Block cobbleGenerator7;
    public static Block cobbleGenerator8;
    public static Block cobbleGenerator9;
    public static Block cobblestoneMelter;
    public static Block lavaGenerator;
    public static Block allInOneGenerator;
    public static Block cobblestoneInfusedObsidian;

    public static void preInit() {
        cobbleGenerator1 = new CobblestoneGenerator(1,1,60).setRegistryName("cobblegenerator1").setUnlocalizedName(CobblestoneSemantics.MOD_ID + ".cobblegenerator1");
        cobbleGenerator2 = new CobblestoneGenerator(2,2,40).setRegistryName("cobblegenerator2").setUnlocalizedName(CobblestoneSemantics.MOD_ID + ".cobblegenerator2");
        cobbleGenerator3 = new CobblestoneGenerator(3,4,20).setRegistryName("cobblegenerator3").setUnlocalizedName(CobblestoneSemantics.MOD_ID + ".cobblegenerator3");
        cobbleGenerator4 = new CobblestoneGenerator(4,8,10).setRegistryName("cobblegenerator4").setUnlocalizedName(CobblestoneSemantics.MOD_ID + ".cobblegenerator4");
        cobbleGenerator5 = new CobblestoneGenerator(5,16,5).setRegistryName("cobblegenerator5").setUnlocalizedName(CobblestoneSemantics.MOD_ID + ".cobblegenerator5");
        cobbleGenerator6 = new CobblestoneGenerator(6,32,4).setRegistryName("cobblegenerator6").setUnlocalizedName(CobblestoneSemantics.MOD_ID + ".cobblegenerator6");
        cobbleGenerator7 = new CobblestoneGenerator(7,64,3).setRegistryName("cobblegenerator7").setUnlocalizedName(CobblestoneSemantics.MOD_ID + ".cobblegenerator7");
        cobbleGenerator8 = new CobblestoneGenerator(8,128,2).setRegistryName("cobblegenerator8").setUnlocalizedName(CobblestoneSemantics.MOD_ID + ".cobblegenerator8");
        cobbleGenerator9 = new CobblestoneGenerator(9,256,1).setRegistryName("cobblegenerator9").setUnlocalizedName(CobblestoneSemantics.MOD_ID + ".cobblegenerator9");
        cobblestoneMelter = new CobblestoneMelter().setRegistryName("cobblestonemelter").setUnlocalizedName(CobblestoneSemantics.MOD_ID + ".cobblestonemelter");
        lavaGenerator = new LavaGenerator().setRegistryName("lavagenerator").setUnlocalizedName(CobblestoneSemantics.MOD_ID + ".lavagenerator");
        allInOneGenerator = new AllInOneGenerator().setRegistryName("allinonegenerator").setUnlocalizedName(CobblestoneSemantics.MOD_ID + ".allinonegenerator" );
        cobblestoneInfusedObsidian = new CobblestoneInfusedObsidian().setRegistryName("cobblestone_infused_obsidian").setUnlocalizedName(CobblestoneSemantics.MOD_ID + ".cobblestone_infused_obsidian");

        add(cobbleGenerator1          );
        add(cobbleGenerator2          );
        add(cobbleGenerator3          );
        add(cobbleGenerator4          );
        add(cobbleGenerator5          );
        add(cobbleGenerator6          );
        add(cobbleGenerator7          );
        add(cobbleGenerator8          );
        add(cobbleGenerator9          );
        add(cobblestoneMelter         );
        add(lavaGenerator             );
        add(allInOneGenerator         );
        add(cobblestoneInfusedObsidian);
    }
    private static void add(Block block) {
        block.setCreativeTab(CobblestoneSemantics.MOD_TAB);
        BLOCKS.add(block);
        ModItems.ITEMS.add(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }

}
