package Strikeboom.cobblestonesemantics.client.render.screens;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.blockentities.energystorage.CobblestoneSemanticsEnergyStorage;
import Strikeboom.cobblestonesemantics.menus.CobblestoneMelterMenu;
import Strikeboom.cobblestonesemantics.util.ClientUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.transfer.fluid.FluidStacksResourceHandler;


public class CobblestoneMelterScreen extends AbstractContainerScreen<CobblestoneMelterMenu> {
    FluidStacksResourceHandler tank;
    CobblestoneSemanticsEnergyStorage energy;
    public CobblestoneMelterScreen(CobblestoneMelterMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.tank = menu.blockEntity.fluidTank;
        this.energy = menu.blockEntity.energyStorage;

        titleLabelX = getLeftPos() + 70;
        inventoryLabelX = getLeftPos() + 60;
    }


    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float a) {
        super.extractBackground(guiGraphics,mouseX,mouseY,a);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,Identifier.fromNamespaceAndPath(CobblestoneSemantics.MOD_ID, "textures/gui/container/cobblestone_melter.png"), relX, relY, 0, 0, this.imageWidth, this.imageHeight,256,256);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,Identifier.fromNamespaceAndPath(CobblestoneSemantics.MOD_ID, "textures/gui/container/cobblestone_melter.png"),getLeftPos()+81,getTopPos()+32,176,0,this.menu.blockEntity.getCooldown() * 23 / this.menu.blockEntity.getDelay(),16,256,256);
        ClientUtil.renderFluidBar(guiGraphics,getLeftPos() + 127, getTopPos() + 9,24,66,tank.getResource(0).toStack(tank.getAmountAsInt(0)),tank.getCapacityAsInt(0,tank.getResource(0)));
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,Identifier.fromNamespaceAndPath(CobblestoneSemantics.MOD_ID, "textures/gui/container/lava_generator.png"),getLeftPos()+8, (int) (getTopPos() + 9 + (66 - Math.floor((float)(this.energy.getAmountAsInt() * 66) / this.energy.getCapacityAsInt()))),176,16,24,this.energy.getAmountAsInt() * 66 / this.energy.getCapacityAsInt(),256,256);

    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.extractBackground(guiGraphics,pMouseX,pMouseY,pPartialTick);
        super.extractRenderState(guiGraphics, pMouseX, pMouseY, pPartialTick);
        this.extractTooltip(guiGraphics, pMouseX, pMouseY);
        ClientUtil.drawFluidCapacityTooltip(pMouseX,pMouseY,getLeftPos() + 127,getTopPos() + 9, 24,66,font,guiGraphics,tank.getResource(0).toStack(tank.getAmountAsInt(0)));
        ClientUtil.drawEnergyTooltip(pMouseX,pMouseY,getLeftPos() + 8,getTopPos() + 9, 24,66,font,guiGraphics,energy.getAmountAsInt());

    }

}
