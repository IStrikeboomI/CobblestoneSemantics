package Strikeboom.cobblestonesemantics.blocks;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.guis.blockentities.LavaGeneratorBlockEntity;
import Strikeboom.cobblestonesemantics.guis.menus.LavaGeneratorMenu;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LavaGenerator extends Block implements EntityBlock {
    public LavaGenerator() {
        super(BlockBehaviour.Properties.of(Material.METAL)
                .sound(SoundType.METAL)
                .strength(6f,100f)
                .lightLevel(state -> state.getValue(BlockStateProperties.POWERED) ? 14 : 0)
                .requiresCorrectToolForDrops());
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        if (pStack.hasTag()) {
            if (pStack.getTag().contains("BlockEntityTag")) {
                pTooltip.add(new TranslatableComponent("block." + CobblestoneSemantics.MOD_ID + ".tooltip.saved").withStyle(ChatFormatting.GREEN));
            }
        }
        pTooltip.add(new TranslatableComponent("block." + CobblestoneSemantics.MOD_ID + ".tooltip.lava_generator", CobblestoneSemanticsConfig.LAVA_GENERATOR_POWER_PER_LAVA_BUCKET.get(),CobblestoneSemanticsConfig.LAVA_GENERATOR_DELAY.get()));
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

    //these 2 functions below help save the data into the item stack when breaking block
    @Override
    public void playerDestroy(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState, @Nullable BlockEntity pBlockEntity, ItemStack pTool) {
        if (!pLevel.isClientSide) {
            ItemStack stack = new ItemStack(this);

            if (pBlockEntity != null) {
                pBlockEntity.saveToItem(stack);
            }

            ItemEntity itementity = new ItemEntity(pLevel, (double)pPos.getX() + 0.5D, (double)pPos.getY() + 0.5D, (double)pPos.getZ() + 0.5D, stack);
            itementity.setDefaultPickUpDelay();
            pLevel.addFreshEntity(itementity);
        }
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (!pLevel.isClientSide) {
            if (pStack.hasTag()) {
                CompoundTag tag = BlockItem.getBlockEntityData(pStack);
                pLevel.getBlockEntity(pPos).load(tag);
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new LavaGeneratorBlockEntity(pPos,pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide) {
            return null;
        }

        return (pLevel1, pPos, pState1, pBlockEntity) -> {
            if (pBlockEntity instanceof LavaGeneratorBlockEntity blockEntity) {
                blockEntity.tickServer();
            }
        };
    }
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            if (pLevel.getBlockEntity(pPos) instanceof LavaGeneratorBlockEntity) {
                if (FluidUtil.interactWithFluidHandler(pPlayer, pHand, pLevel, pPos, null)) {
                    return InteractionResult.SUCCESS;
                }
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return new TranslatableComponent("block."+CobblestoneSemantics.MOD_ID+".lava_generator");
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                        return new LavaGeneratorMenu(windowId, pPos, playerInventory, playerEntity);
                    }
                };
                NetworkHooks.openGui((ServerPlayer) pPlayer, containerProvider, pPos);
            }
        }
        return InteractionResult.SUCCESS;
    }
}
