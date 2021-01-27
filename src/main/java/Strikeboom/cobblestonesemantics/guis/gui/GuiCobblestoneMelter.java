package Strikeboom.cobblestonesemantics.guis.gui;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.guis.container.ContainerCobblestoneMelter;
import Strikeboom.cobblestonesemantics.guis.tileentity.TileEntityCobblestoneMelter;
import Strikeboom.cobblestonesemantics.handlers.util.ClientUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import java.util.Objects;

public class GuiCobblestoneMelter extends GuiContainer {
    IInventory playerInv;
    TileEntityCobblestoneMelter te;
    FluidTank fluidHandler;
    public GuiCobblestoneMelter(IInventory playerInv, TileEntityCobblestoneMelter te) {
        super(new ContainerCobblestoneMelter(playerInv,te));
        this.playerInv = playerInv;
        this.te = te;
        this.fluidHandler = (FluidTank) te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,null);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX,mouseY);
        ClientUtil.drawFluidCapacityTooltip(mouseX,mouseY,this.guiLeft + 127,this.guiTop + 9,24,66,this,fluidHandler.getFluid());
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(new ResourceLocation(CobblestoneSemantics.MOD_ID, "textures/gui/container/cobblestonemelter.png"));
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        this.drawTexturedModalRect(this.guiLeft + 81,this.guiTop + 32,176,0,this.te.getCoolDown() * 23 / this.te.getDelay() ,17);
        ClientUtil.renderFluidBar(mc,this.fluidHandler.getFluid(),this.fluidHandler.getCapacity(),this.guiLeft + 127, this.guiTop + 9,24,66);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRenderer.drawString(Objects.requireNonNull(this.te.getDisplayName()).getFormattedText(), (this.xSize / 2 - this.fontRenderer.getStringWidth(this.te.getDisplayName().getFormattedText()) / 2) - 15 , 6, 4210752);
        this.fontRenderer.drawString(this.playerInv.getDisplayName().getFormattedText(), (this.xSize / 2 - this.fontRenderer.getStringWidth(this.playerInv.getDisplayName().getFormattedText()) / 2) , 73, 4210752);
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

    }
}
