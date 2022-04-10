package Strikeboom.cobblestonesemantics.blocks;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.guis.tileentities.CobblestoneGeneratorTileEntity;
import Strikeboom.cobblestonesemantics.guis.tileentities.itemhandlers.CobblestoneGeneratorItemHandler;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;
import java.util.List;

public class CobblestoneGenerator extends Block {
    int tier;
    int storageSlots;
    int delayUntilNextCobbleStone;
    int amountOfCobblestoneEachOperation;
    public CobblestoneGenerator(int tier,int storageSlots,int delayUntilNextCobbleStone,int amountOfCobblestoneEachOperation) {
        super(AbstractBlock.Properties.of(Material.METAL, MaterialColor.CLAY)
                .sound(SoundType.METAL)
                .strength(8f,250f)
                .requiresCorrectToolForDrops());
        this.tier = tier;
        this.storageSlots = storageSlots;
        this.delayUntilNextCobbleStone = delayUntilNextCobbleStone;
        this.amountOfCobblestoneEachOperation = amountOfCobblestoneEachOperation;
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable IBlockReader pWorld, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
        if (pStack.hasTag()) {
            if (pStack.getTag().contains("ItemStackHandler")) {
                pTooltip.add(new TranslationTextComponent("block." + CobblestoneSemantics.MOD_ID + ".tooltip.saved").withStyle(TextFormatting.GREEN));
            }
        }
        pTooltip.add(new TranslationTextComponent("block." + CobblestoneSemantics.MOD_ID + ".tooltip.crouch").withStyle(TextFormatting.YELLOW));
        pTooltip.add(new TranslationTextComponent("block." + CobblestoneSemantics.MOD_ID + ".tooltip.outputs").withStyle(TextFormatting.YELLOW));
        pTooltip.add(new TranslationTextComponent("block." + CobblestoneSemantics.MOD_ID + ".tooltip.tier", Integer.toString(tier)));
        pTooltip.add(new TranslationTextComponent("block." + CobblestoneSemantics.MOD_ID + ".tooltip.storage", Integer.toString(storageSlots * 64)));
        pTooltip.add(new TranslationTextComponent("block." + CobblestoneSemantics.MOD_ID + ".tooltip.delay", Integer.toString(delayUntilNextCobbleStone)));
        pTooltip.add(new TranslationTextComponent("block." + CobblestoneSemantics.MOD_ID + ".tooltip.amount", Integer.toString(amountOfCobblestoneEachOperation)));

    }
    public int getTier() {
        return tier;
    }

    public int getAmountOfCobblestoneEachOperation() {
        return amountOfCobblestoneEachOperation;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CobblestoneGeneratorTileEntity(storageSlots,delayUntilNextCobbleStone,amountOfCobblestoneEachOperation);
    }

    @Override
   public ActionResultType use(BlockState pState, World pWorld, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockRayTraceResult pHit) {
       if (!pWorld.isClientSide) {
           if (pWorld.getBlockEntity(pPos) instanceof CobblestoneGeneratorTileEntity) {
               if (!pPlayer.isCrouching()) {
                   CobblestoneGeneratorTileEntity be = (CobblestoneGeneratorTileEntity) pWorld.getBlockEntity(pPos);
                   be.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
                       ItemStack stack = ((CobblestoneGeneratorItemHandler) iItemHandler).getLargestSlotThenRemove();
                       if (!stack.isEmpty()) {
                           if (!pPlayer.inventory.add(stack)) {
                               InventoryHelper.dropItemStack(pWorld, pPos.getX(), pPos.getY(), pPos.getZ(), stack);
                           }
                       }
                   });
               } else {
                   pPlayer.sendMessage(new TranslationTextComponent("block." + CobblestoneSemantics.MOD_ID + ".message.amount", ((CobblestoneGeneratorTileEntity) pWorld.getBlockEntity(pPos)).getCobblestoneAmount()), Util.NIL_UUID);
               }
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
