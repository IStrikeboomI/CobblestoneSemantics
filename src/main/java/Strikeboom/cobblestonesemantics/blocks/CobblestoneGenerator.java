package Strikeboom.cobblestonesemantics.blocks;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.guis.tileentity.TileEntityCobbleGenerator;
import Strikeboom.cobblestonesemantics.guis.tileentity.customs.itemhandlers.CobblestoneGeneratorItemHandler;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.List;

public class CobblestoneGenerator extends Block {
    int tier;
    int storageSlots;
    int delayUntilNextCobbleStone;
    public CobblestoneGenerator(int tier,int storageSlots,int delayUntilNextCobbleStone) {
        super(Material.IRON);
        setSoundType(SoundType.METAL);
        setHardness(6.0F);
        setResistance(100.0F);
        setHarvestLevel("pickaxe", 2);
        this.tier = tier;
        this.storageSlots = storageSlots;
        this.delayUntilNextCobbleStone = delayUntilNextCobbleStone;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityCobbleGenerator(tier,storageSlots,delayUntilNextCobbleStone);
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        if (stack.hasTagCompound()) {
            tooltip.add(TextFormatting.GREEN + I18n.format("tile." + CobblestoneSemantics.MOD_ID + ".tooltip.saved"));
        }
        tooltip.add(TextFormatting.YELLOW + I18n.format("tile." + CobblestoneSemantics.MOD_ID + ".tooltip.outputs"));
        tooltip.add(I18n.format("tile." + CobblestoneSemantics.MOD_ID + ".tooltip.tier") + " " +tier);
        tooltip.add(I18n.format("tile." + CobblestoneSemantics.MOD_ID + ".tooltip.storage") + " " + (storageSlots * 64));
        tooltip.add(I18n.format("tile." + CobblestoneSemantics.MOD_ID + ".tooltip.delay") + " " + delayUntilNextCobbleStone);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            if (!playerIn.isSneaking()) {
                TileEntityCobbleGenerator te = (TileEntityCobbleGenerator) worldIn.getTileEntity(pos);
                ItemStack stack = ((CobblestoneGeneratorItemHandler) te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)).getLargestSlotThenRemove();
                if (!stack.isEmpty()) {
                    if (!playerIn.inventory.addItemStackToInventory(stack)) {
                        InventoryHelper.spawnItemStack(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, stack);
                    }
                }
            } else {
                if (playerIn.isSneaking()) {
                    playerIn.sendMessage(new TextComponentString(new TextComponentTranslation("tile." + CobblestoneSemantics.MOD_ID + ".message.amount").getFormattedText() + " " + ((TileEntityCobbleGenerator) worldIn.getTileEntity(pos)).getCobblestoneAmount()));
                    return true;
                }
            }
        }
        return true;
    }

    //These 4 methods help save data when breaking the block
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

    public int getTier() {
        return tier;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }
}
