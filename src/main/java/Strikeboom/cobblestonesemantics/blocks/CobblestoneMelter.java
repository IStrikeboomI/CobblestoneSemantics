package Strikeboom.cobblestonesemantics.blocks;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.guis.containers.CobblestoneMelterContainer;
import Strikeboom.cobblestonesemantics.guis.tileentities.CobblestoneMelterTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

public class CobblestoneMelter extends Block {
    public CobblestoneMelter() {
        super(AbstractBlock.Properties.of(Material.METAL)
                    .sound(SoundType.METAL)
                    .strength(6f,100f)
                    .lightLevel(state -> state.getValue(BlockStateProperties.POWERED) ? 14 : 0)
                    .requiresCorrectToolForDrops());
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable IBlockReader pWorld, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
        super.appendHoverText(pStack, pWorld, pTooltip, pFlag);
        if (pStack.hasTag()) {
            if (pStack.getTag().contains("ItemStackHandler")) {
                pTooltip.add(new TranslationTextComponent("block." + CobblestoneSemantics.MOD_ID + ".tooltip.saved").withStyle(TextFormatting.GREEN));
            }
        }
        pTooltip.add(new TranslationTextComponent("block." + CobblestoneSemantics.MOD_ID + ".tooltip.cobblestone_melter","500","200"));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.POWERED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return super.getStateForPlacement(context).setValue(BlockStateProperties.POWERED, false);
    }


    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CobblestoneMelterTileEntity();
    }

    @Override
    public ActionResultType use(BlockState pState, World pWorld, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockRayTraceResult pHit) {
        if (!pWorld.isClientSide) {
            if (pWorld.getBlockEntity(pPos) instanceof CobblestoneMelterTileEntity) {
                if (FluidUtil.interactWithFluidHandler(pPlayer, pHand, pWorld, pPos, null)) {
                    return ActionResultType.SUCCESS;
                }
                INamedContainerProvider containerProvider = new INamedContainerProvider() {
                    @Nullable
                    @Override
                    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
                        return new CobblestoneMelterContainer(p_createMenu_1_, pPos, p_createMenu_2_, p_createMenu_3_, IWorldPosCallable.create(pWorld,pPos));
                    }
                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("block."+CobblestoneSemantics.MOD_ID+".cobblestone_melter");
                    }
                };
                NetworkHooks.openGui((ServerPlayerEntity) pPlayer, containerProvider, pPos);
            }
        }
        return ActionResultType.SUCCESS;
    }

    //these 2 functions below help save the data into the item stack when breaking block
    @Override
    public void playerDestroy(World pWorld, PlayerEntity pPlayer, BlockPos pPos, BlockState pState, @Nullable TileEntity pBlockEntity, ItemStack pTool) {
        if (!pWorld.isClientSide) {
            ItemStack stack = new ItemStack(this);

            if (pBlockEntity != null) {
                CompoundNBT nbt = new CompoundNBT();
                pBlockEntity.save(nbt);
                stack.setTag(nbt);
            }
            InventoryHelper.dropItemStack(pWorld,pPos.getX(), pPos.getY(), pPos.getZ(),stack);
        }
    }

    @Override
    public void setPlacedBy(World pWorld, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (!pWorld.isClientSide) {
            if (pStack.hasTag()) {
                pStack.getTag().putInt("x",pPos.getX());
                pStack.getTag().putInt("y",pPos.getY());
                pStack.getTag().putInt("z",pPos.getZ());
                pWorld.getBlockEntity(pPos).load(pState,pStack.getTag());
            }
        }
    }
}
