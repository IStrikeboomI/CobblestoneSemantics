package Strikeboom.cobblestonesemantics.guis.gui;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.guis.container.ContainerAllInOneGenerator;
import Strikeboom.cobblestonesemantics.guis.tileentity.TileEntityAllInOneGenerator;
import Strikeboom.cobblestonesemantics.guis.tileentity.customs.CobblestoneSemanticsEnergyStorage;
import Strikeboom.cobblestonesemantics.handlers.util.ClientUtil;
import Strikeboom.cobblestonesemantics.init.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.CapabilityEnergy;

public class GuiAllInOneGenerator extends GuiContainer {
    TileEntityAllInOneGenerator te;
    CobblestoneSemanticsEnergyStorage energyStorage;
    public GuiAllInOneGenerator(IInventory playerInv, TileEntityAllInOneGenerator te) {
        super(new ContainerAllInOneGenerator(playerInv,te));
        this.te = te;
        this.energyStorage = (CobblestoneSemanticsEnergyStorage) te.getCapability(CapabilityEnergy.ENERGY,null);
    }
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX,mouseY);
        ClientUtil.drawEnergyTooltip(mouseX,mouseY,this.guiLeft + 135, this.guiTop + 9,24,66,this,energyStorage.getEnergyStored());
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(new ResourceLocation(CobblestoneSemantics.MOD_ID, "textures/gui/container/allinonegenerator.png"));
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        this.drawTexturedModalRect(this.guiLeft + 101,this.guiTop + 34,176,0,this.te.getCoolDown() * 24 / this.te.getDelay() ,16);
        this.drawTexturedModalRect(this.guiLeft + 135, this.guiTop + 9 + (66 - this.energyStorage.getEnergyStored() * 66 / this.energyStorage.getMaxEnergyStored()) ,176,16,24,this.energyStorage.getEnergyStored() * 66 / this.energyStorage.getMaxEnergyStored());
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(new ItemStack(ModBlocks.cobbleGenerator9),29,51);
        for (int i = 60; i <= 78;i += 18) {
            for (int j = 44; j <= 72;j += 18) {
                Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(new ItemStack(ModBlocks.cobblestoneMelter),i,j);

            }
        }
        for (int i = 24; i <= 78;i += 18) {
            for (int j = 5; j <= 32;j += 18) {
                Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(new ItemStack(ModBlocks.lavaGenerator),i,j);
            }
        }
    }
}
