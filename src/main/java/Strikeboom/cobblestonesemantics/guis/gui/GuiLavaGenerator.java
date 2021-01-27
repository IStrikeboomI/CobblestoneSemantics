package Strikeboom.cobblestonesemantics.guis.gui;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.guis.container.ContainerLavaGenerator;
import Strikeboom.cobblestonesemantics.guis.tileentity.TileEntityCobblestoneMelter;
import Strikeboom.cobblestonesemantics.guis.tileentity.TileEntityLavaGenerator;
import Strikeboom.cobblestonesemantics.guis.tileentity.customs.CobblestoneSemanticsEnergyStorage;
import Strikeboom.cobblestonesemantics.handlers.util.ClientUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import java.util.Objects;

public class GuiLavaGenerator extends GuiContainer {
    IInventory playerInv;
    TileEntityLavaGenerator te;
    FluidTank fluidHandler;
    CobblestoneSemanticsEnergyStorage energyStorage;
    public GuiLavaGenerator(IInventory playerInv, TileEntityLavaGenerator te) {
        super(new ContainerLavaGenerator(playerInv));
        this.playerInv = playerInv;
        this.te = te;
        this.fluidHandler = (FluidTank) te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,null);
        this.energyStorage = (CobblestoneSemanticsEnergyStorage) te.getCapability(CapabilityEnergy.ENERGY,null);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX,mouseY);
        ClientUtil.drawFluidCapacityTooltip(mouseX,mouseY,this.guiLeft + 21,this.guiTop + 9,24,66,this,fluidHandler.getFluid());
        ClientUtil.drawEnergyTooltip(mouseX,mouseY,this.guiLeft + 135, this.guiTop + 9,24,66,this,energyStorage.getEnergyStored());
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(new ResourceLocation(CobblestoneSemantics.MOD_ID, "textures/gui/container/lavagenerator.png"));
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        this.drawTexturedModalRect(this.guiLeft + 81,this.guiTop + 32,176,0,this.te.getCoolDown() * 24 / this.te.getDelay() ,16);
        this.drawTexturedModalRect(this.guiLeft + 135, this.guiTop + 9 + (66 - this.energyStorage.getEnergyStored() * 66 / this.energyStorage.getMaxEnergyStored()) ,176,16,24,this.energyStorage.getEnergyStored() * 66 / this.energyStorage.getMaxEnergyStored());
        ClientUtil.renderFluidBar(mc,this.fluidHandler.getFluid(),this.fluidHandler.getCapacity(),this.guiLeft + 21, this.guiTop + 9,24,66);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRenderer.drawString(Objects.requireNonNull(this.te.getDisplayName()).getFormattedText(), (this.xSize / 2 - this.fontRenderer.getStringWidth(this.te.getDisplayName().getFormattedText()) / 2) + 3 , 6, 4210752);
        this.fontRenderer.drawString(this.playerInv.getDisplayName().getFormattedText(), (this.xSize / 2 - this.fontRenderer.getStringWidth(this.playerInv.getDisplayName().getFormattedText()) / 2) , 73, 4210752);
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }
}
