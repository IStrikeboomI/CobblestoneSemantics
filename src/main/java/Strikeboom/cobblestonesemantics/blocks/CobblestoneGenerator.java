package Strikeboom.cobblestonesemantics.blocks;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.blockentities.CobblestoneGeneratorBlockEntity;
import Strikeboom.cobblestonesemantics.blockentities.itemhandlers.CobblestoneGeneratorItemHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
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
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CobblestoneGenerator extends Block implements EntityBlock {
    int tier;
    int storageSlots;
    int delayUntilNextCobbleStone;
    int amountOfCobblestoneEachOperation;
    public CobblestoneGenerator(int tier,int storageSlots,int delayUntilNextCobbleStone,int amountOfCobblestoneEachOperation) {
        super(Properties.ofFullCopy(Blocks.IRON_BLOCK)
                .mapColor(MapColor.CLAY)
                .sound(SoundType.METAL)
                .strength(8f,250f)
                .requiresCorrectToolForDrops());
        this.tier = tier;
        this.storageSlots = storageSlots;
        this.delayUntilNextCobbleStone = delayUntilNextCobbleStone;
        this.amountOfCobblestoneEachOperation = amountOfCobblestoneEachOperation;
    }


    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext context, List<Component> pTooltip, TooltipFlag pFlag) {
        if (!pStack.getOrDefault(DataComponents.BLOCK_ENTITY_DATA, CustomData.EMPTY).isEmpty()) {
            pTooltip.add(Component.translatable("block." + CobblestoneSemantics.MOD_ID + ".tooltip.saved").withStyle(ChatFormatting.GREEN));
        }
        pTooltip.add(Component.translatable("block." + CobblestoneSemantics.MOD_ID + ".tooltip.crouch").withStyle(ChatFormatting.YELLOW));
        pTooltip.add(Component.translatable("block." + CobblestoneSemantics.MOD_ID + ".tooltip.outputs").withStyle(ChatFormatting.YELLOW));
        pTooltip.add(Component.translatable("block." + CobblestoneSemantics.MOD_ID + ".tooltip.tier", Integer.toString(tier)));
        pTooltip.add(Component.translatable("block." + CobblestoneSemantics.MOD_ID + ".tooltip.storage", Integer.toString(storageSlots * 64)));
        pTooltip.add(Component.translatable("block." + CobblestoneSemantics.MOD_ID + ".tooltip.delay", Integer.toString(delayUntilNextCobbleStone)));
        pTooltip.add(Component.translatable("block." + CobblestoneSemantics.MOD_ID + ".tooltip.amount", Integer.toString(amountOfCobblestoneEachOperation)));

    }

    public int getTier() {
        return tier;
    }

    public int getAmountOfCobblestoneEachOperation() {
        return amountOfCobblestoneEachOperation;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CobblestoneGeneratorBlockEntity(pPos,pState,storageSlots,delayUntilNextCobbleStone,amountOfCobblestoneEachOperation);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide) {
            return null;
        }

        return (pLevel1, pPos, pState1, pBlockEntity) -> {
            if (pBlockEntity instanceof CobblestoneGeneratorBlockEntity blockEntity) {
                blockEntity.tickServer();
            }
        };
    }


    @Override
   public InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHit) {
       if (!pLevel.isClientSide) {
           if (!pPlayer.isCrouching()) {
               CobblestoneGeneratorBlockEntity be = (CobblestoneGeneratorBlockEntity) pLevel.getBlockEntity(pPos);
               IItemHandler iItemHandler = pLevel.getCapability(Capabilities.ItemHandler.BLOCK,pPos,null);
               if (iItemHandler != null) {
                   ItemStack stack = ((CobblestoneGeneratorItemHandler) iItemHandler).getLargestSlotThenRemove();
                   if (!stack.isEmpty()) {
                       if (!pPlayer.getInventory().add(stack)) {
                           pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), stack));
                       }
                   }
               }
           } else {
               if (pPlayer.isCrouching()) {
                   pPlayer.displayClientMessage(Component.translatable("block." + CobblestoneSemantics.MOD_ID + ".message.amount",((CobblestoneGeneratorBlockEntity) pLevel.getBlockEntity(pPos)).getCobblestoneAmount()),false);
               }
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
