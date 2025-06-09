package Strikeboom.cobblestonesemantics.blocks;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.blockentities.AllInOneGeneratorBlockEntity;
import Strikeboom.cobblestonesemantics.menus.AllInOneGeneratorMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.TooltipProvider;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class AllInOneGenerator extends Block implements EntityBlock, TooltipProvider {
    public AllInOneGenerator(ResourceLocation resourceLocation) {
        super(Properties.ofFullCopy(Blocks.IRON_BLOCK)
                .sound(SoundType.METAL)
                .strength(6f,100f)
                .requiresCorrectToolForDrops().setId(ResourceKey.create(BuiltInRegistries.BLOCK.key(),resourceLocation)));
    }
    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag flag, DataComponentGetter componentGetter) {
        if (!componentGetter.getOrDefault(DataComponents.BLOCK_ENTITY_DATA, CustomData.EMPTY).isEmpty()) {
            tooltipAdder.accept(Component.translatable("block." + CobblestoneSemantics.MOD_ID + ".tooltip.saved").withStyle(ChatFormatting.GREEN));
        }
        tooltipAdder.accept(Component.translatable("block." + CobblestoneSemantics.MOD_ID + ".tooltip.all_in_one_explainer"));
        tooltipAdder.accept(Component.translatable("block." + CobblestoneSemantics.MOD_ID + ".tooltip.all_in_one_delay"));
        tooltipAdder.accept(Component.translatable("block." + CobblestoneSemantics.MOD_ID + ".tooltip.all_in_one_produced"));
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

    @Nullable
    @Override
    protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return Component.empty();
            }

            @Override
            public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                return new AllInOneGeneratorMenu(windowId, pos, playerInventory);
            }
        };
    }

    @Override
    public InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            if (pLevel.getBlockEntity(pPos) instanceof AllInOneGeneratorBlockEntity) {
                pPlayer.openMenu(pState.getMenuProvider(pLevel,pPos));
            }
        }
        return InteractionResult.SUCCESS;
    }


}
