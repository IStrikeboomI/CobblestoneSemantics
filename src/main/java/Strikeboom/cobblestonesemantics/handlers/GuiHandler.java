package Strikeboom.cobblestonesemantics.handlers;

import Strikeboom.cobblestonesemantics.guis.container.*;
import Strikeboom.cobblestonesemantics.guis.gui.*;
import Strikeboom.cobblestonesemantics.guis.tileentity.TileEntityAllInOneGenerator;
import Strikeboom.cobblestonesemantics.guis.tileentity.TileEntityCobblestoneMelter;
import Strikeboom.cobblestonesemantics.guis.tileentity.TileEntityLavaGenerator;
import Strikeboom.cobblestonesemantics.items.CobblestoneBag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
    public static final int COBBLESTONEMELTER = 1;
    public static final int LAVAGENERATOR = 2;
    public static final int ALLINONEGENERATOR = 3;
    public static final int COBBLESTONEBAG = 4;
    public static final int COBBLESTONEINFUSEDOBSIDIANBAG = 5;
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case COBBLESTONEMELTER:
                return new ContainerCobblestoneMelter(player.inventory,(TileEntityCobblestoneMelter) world.getTileEntity(new BlockPos(x,y,z)));

            case LAVAGENERATOR:
                return new ContainerLavaGenerator(player.inventory);

            case ALLINONEGENERATOR:
                return new ContainerAllInOneGenerator(player.inventory,(TileEntityAllInOneGenerator)world.getTileEntity(new BlockPos(x,y,z)));

            case COBBLESTONEBAG:
                return new ContainerCobblestoneBag(player.inventory,player.getHeldItemMainhand());

            case COBBLESTONEINFUSEDOBSIDIANBAG:
                return new ContainerCobblestoneInfusedObsidianBag(player.inventory,player.getHeldItemMainhand());

        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case COBBLESTONEMELTER:
                return new GuiCobblestoneMelter(player.inventory,(TileEntityCobblestoneMelter) world.getTileEntity(new BlockPos(x,y,z)));

            case LAVAGENERATOR:
                return new GuiLavaGenerator(player.inventory,(TileEntityLavaGenerator) world.getTileEntity(new BlockPos(x,y,z)));

            case ALLINONEGENERATOR:
                return new GuiAllInOneGenerator(player.inventory,(TileEntityAllInOneGenerator) world.getTileEntity(new BlockPos(x,y,z)));

            case COBBLESTONEBAG:
                return new GuiCobblestoneBag(player.inventory,player.getHeldItemMainhand());

            case COBBLESTONEINFUSEDOBSIDIANBAG:
                return new GuiCobblestoneInfusedObsidianBag(player.inventory,player.getHeldItemMainhand());
        }
        return null;
    }
}
