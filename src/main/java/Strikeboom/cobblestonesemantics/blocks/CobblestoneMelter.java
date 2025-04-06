package Strikeboom.cobblestonesemantics.blocks;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.blockentities.CobblestoneMelterBlockEntity;
import Strikeboom.cobblestonesemantics.menus.CobblestoneMelterMenu;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.fluids.FluidUtil;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CobblestoneMelter extends Block implements EntityBlock {
    public CobblestoneMelter() {
        super(Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .sound(SoundType.METAL)
                    .strength(6f,100f)
                    .lightLevel(state -> state.getValue(BlockStateProperties.POWERED) ? 14 : 0)
                    .requiresCorrectToolForDrops());
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext context, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, context, pTooltip, pFlag);
        if (!pStack.getOrDefault(DataComponents.BLOCK_ENTITY_DATA,CustomData.EMPTY).isEmpty()) {
            pTooltip.add(Component.translatable("block." + CobblestoneSemantics.MOD_ID + ".tooltip.saved").withStyle(ChatFormatting.GREEN));
        }
        pTooltip.add(Component.translatable("block." + CobblestoneSemantics.MOD_ID + ".tooltip.cobblestone_melter", CobblestoneSemanticsConfig.COBBLESTONE_MELTER_LAVA_PER_COBBLESTONE.get(),CobblestoneSemanticsConfig.COBBLESTONE_MELTER_DELAY.get()));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.POWERED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(BlockStateProperties.POWERED, false);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CobblestoneMelterBlockEntity(pPos,pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide) {
            return null;
        }

        return (pLevel1, pPos, pState1, pBlockEntity) -> {
            if (pBlockEntity instanceof CobblestoneMelterBlockEntity blockEntity) {
                blockEntity.tickServer();
            }
        };
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof CobblestoneMelterBlockEntity) {
                if (FluidUtil.interactWithFluidHandler(player,hand,level,pos, null)) {
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            if (pLevel.getBlockEntity(pPos) instanceof CobblestoneMelterBlockEntity) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return Component.translatable("block."+CobblestoneSemantics.MOD_ID+".cobblestone_melter");
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                        return new CobblestoneMelterMenu(windowId, pPos, playerInventory);
                    }
                };
                pPlayer.openMenu(containerProvider);
            }
        }
        return InteractionResult.SUCCESS;
    }

    //these 2 functions below help save the data into the item stack when breaking block
    @Override
    public void playerDestroy(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState, @Nullable BlockEntity pBlockEntity, ItemStack pTool) {
        if (!pLevel.isClientSide) {
            ItemStack stack = new ItemStack(this);

            if (pBlockEntity != null) {
                stack.set(DataComponents.BLOCK_ENTITY_DATA,CustomData.of(pBlockEntity.saveCustomOnly(pLevel.registryAccess())));
            }

            ItemEntity itementity = new ItemEntity(pLevel, (double)pPos.getX() + 0.5D, (double)pPos.getY() + 0.5D, (double)pPos.getZ() + 0.5D, stack);
            itementity.setDefaultPickUpDelay();
            pLevel.addFreshEntity(itementity);
        }
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (!pLevel.isClientSide) {
            CustomData data = pStack.getOrDefault(DataComponents.BLOCK_ENTITY_DATA, CustomData.EMPTY);
            if (!data.isEmpty()) {
                pLevel.getBlockEntity(pPos).loadWithComponents(data.copyTag(),pLevel.registryAccess());
            }
        }
    }
}
