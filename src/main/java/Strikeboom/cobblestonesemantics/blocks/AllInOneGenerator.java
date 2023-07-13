package Strikeboom.cobblestonesemantics.blocks;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.blockentities.AllInOneGeneratorBlockEntity;
import Strikeboom.cobblestonesemantics.menus.AllInOneGeneratorMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AllInOneGenerator extends Block implements EntityBlock {
    public AllInOneGenerator() {
        super(Properties.copy(Blocks.IRON_BLOCK)
                .sound(SoundType.METAL)
                .strength(6f,100f)
                .requiresCorrectToolForDrops());
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        if (pStack.hasTag()) {
            if (pStack.getTag().contains("BlockEntityTag")) {
                pTooltip.add(Component.translatable("block." + CobblestoneSemantics.MOD_ID + ".tooltip.saved").withStyle(ChatFormatting.GREEN));
            }
        }
        pTooltip.add(Component.translatable("block." + CobblestoneSemantics.MOD_ID + ".tooltip.all_in_one_explainer"));
        pTooltip.add(Component.translatable("block." + CobblestoneSemantics.MOD_ID + ".tooltip.all_in_one_delay"));
        pTooltip.add(Component.translatable("block." + CobblestoneSemantics.MOD_ID + ".tooltip.all_in_one_produced"));
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
                if (pStack.getTag().contains("BlockEntityTag")) {
                    CompoundTag tag = BlockItem.getBlockEntityData(pStack);
                    pLevel.getBlockEntity(pPos).load(tag);
                }
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new AllInOneGeneratorBlockEntity(pPos,pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide) {
            return null;
        }

        return (pLevel1, pPos, pState1, pBlockEntity) -> {
            if (pBlockEntity instanceof AllInOneGeneratorBlockEntity blockEntity) {
                blockEntity.tickServer();
            }
        };
    }
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            if (pLevel.getBlockEntity(pPos) instanceof AllInOneGeneratorBlockEntity) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return Component.empty();
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                        return new AllInOneGeneratorMenu(windowId, pPos, playerInventory, playerEntity);
                    }
                };
                NetworkHooks.openScreen((ServerPlayer) pPlayer, containerProvider, pPos);
            }
        }
        return InteractionResult.SUCCESS;
    }
}
