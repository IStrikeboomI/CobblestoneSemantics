package Strikeboom.cobblestonesemantics.items;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.handlers.GuiHandler;
import Strikeboom.cobblestonesemantics.items.wrappers.ItemItemStackHandlerCapabilityWrapper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.List;

public class CobblestoneInfusedObsidianBag extends Item {

    public CobblestoneInfusedObsidianBag() {
        maxStackSize = 1;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.format("tooltip." + CobblestoneSemantics.MOD_ID + ".holds27"));
    }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (!worldIn.isRemote) {
            playerIn.openGui(CobblestoneSemantics.instance, GuiHandler.COBBLESTONEINFUSEDOBSIDIANBAG,worldIn,(int)playerIn.posX,(int)playerIn.posY,(int)playerIn.posZ);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new ItemItemStackHandlerCapabilityWrapper(27,this);
    }
}
