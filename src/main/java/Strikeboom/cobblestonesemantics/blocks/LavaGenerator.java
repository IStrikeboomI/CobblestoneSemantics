package Strikeboom.cobblestonesemantics.blocks;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.guis.tileentity.TileEntityLavaGenerator;
import Strikeboom.cobblestonesemantics.handlers.ConfigHandler;
import Strikeboom.cobblestonesemantics.handlers.GuiHandler;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;

import javax.annotation.Nonnull;
import java.util.List;

public class LavaGenerator extends Block {
    public static final PropertyBool RUNNING = PropertyBool.create("running");
    public LavaGenerator() {
        super(Material.IRON);
        setSoundType(SoundType.METAL);
        setHardness(6.0F);
        setResistance(100.0F);
        setHarvestLevel("pickaxe", 2);
        setDefaultState(this.getDefaultState().withProperty(RUNNING, Boolean.TRUE));
    }

    @Override
    public int getLightValue(@Nonnull IBlockState state, IBlockAccess world, @Nonnull BlockPos pos) {
        if(state.getValue(RUNNING)) {
            return 15;
        }
        return 0;
    }
    
    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
        if (stack.hasTagCompound()) {
            tooltip.add(TextFormatting.GREEN + I18n.format("tile." + CobblestoneSemantics.MOD_ID + ".tooltip.saved"));
        }
        tooltip.add(I18n.format("tile." + CobblestoneSemantics.MOD_ID + ".tooltip.produces") + " "+ ConfigHandler.BLOCKS.LAVA_GENERATOR.powerPerLavaBucket +
                " RF/FE " + I18n.format("tile." + CobblestoneSemantics.MOD_ID + ".tooltip.every") + " " +ConfigHandler.BLOCKS.LAVA_GENERATOR.generatorDelay + " " +
                I18n.format("tile." + CobblestoneSemantics.MOD_ID + ".tooltip.ticks"));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            if (FluidUtil.interactWithFluidHandler(playerIn,hand,worldIn,pos,null)) {
                return true;
            }
            playerIn.openGui(CobblestoneSemantics.instance, GuiHandler.LAVAGENERATOR,worldIn,pos.getX(),pos.getY(),pos.getZ());
        }
        return true;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityLavaGenerator();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, RUNNING);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState().withProperty(RUNNING,false);
    }
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(RUNNING,meta == 1);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(RUNNING) ? 1 : 0;
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if (willHarvest) {
            return true;
        }
        return super.removedByPlayer(state, world, pos, player, false);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        ItemStack stack = new ItemStack(this);
        NBTTagCompound nbt = new NBTTagCompound();
        world.getTileEntity(pos).writeToNBT(nbt);
        stack.setTagCompound(nbt);
        drops.add(stack);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        worldIn.setBlockToAir(pos);
    }
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (!worldIn.isRemote) {
            if (stack.getTagCompound() != null) {
                stack.getTagCompound().setInteger("x",pos.getX());
                stack.getTagCompound().setInteger("y",pos.getY());
                stack.getTagCompound().setInteger("z",pos.getZ());
                worldIn.getTileEntity(pos).readFromNBT(stack.getTagCompound());
            }
        }
    }

}
