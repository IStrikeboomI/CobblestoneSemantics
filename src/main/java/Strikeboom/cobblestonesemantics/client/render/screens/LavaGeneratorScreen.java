package Strikeboom.cobblestonesemantics.client.render.screens;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.blockentities.energystorage.CobblestoneSemanticsEnergyStorage;
import Strikeboom.cobblestonesemantics.menus.LavaGeneratorMenu;
import Strikeboom.cobblestonesemantics.util.ClientUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;


public class LavaGeneratorScreen extends AbstractContainerScreen<LavaGeneratorMenu> {
    FluidTank tank;
    CobblestoneSemanticsEnergyStorage energy;
    public LavaGeneratorScreen(LavaGeneratorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        tank = menu.blockEntity.fluidTank;
        energy = menu.blockEntity.energyStorage;

        titleLabelX -= getGuiLeft() - 43;
        inventoryLabelX -= getGuiLeft() - 55;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float p_97788_, int p_97789_, int p_97790_) {
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(RenderType::guiTextured,ResourceLocation.fromNamespaceAndPath(CobblestoneSemantics.MOD_ID, "textures/gui/container/lava_generator.png"), relX, relY, 0, 0, this.imageWidth, this.imageHeight,256,256);
        guiGraphics.blit(RenderType::guiTextured,ResourceLocation.fromNamespaceAndPath(CobblestoneSemantics.MOD_ID, "textures/gui/container/lava_generator.png"),getGuiLeft()+81,getGuiTop()+32,176,0,this.menu.blockEntity.getCooldown() * 23 / this.menu.blockEntity.getDelay(),16,256,256);
        guiGraphics.blit(RenderType::guiTextured,ResourceLocation.fromNamespaceAndPath(CobblestoneSemantics.MOD_ID, "textures/gui/container/lava_generator.png"),getGuiLeft()+135, (int) (getGuiTop()+ 9 + (66 - Math.floor((float)(this.energy.getEnergyStored() * 66) / this.energy.getMaxEnergyStored()))),176,16,24,this.energy.getEnergyStored() * 66 / this.energy.getMaxEnergyStored(),256,256);

        ClientUtil.renderFluidBar(guiGraphics,getGuiLeft() + 21, getGuiTop() + 9,24,66,tank.getFluid(),tank.getCapacity());

    }

    @Override
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(guiGraphics,pMouseX,pMouseY,pPartialTick);
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(guiGraphics, pMouseX, pMouseY);
        ClientUtil.drawFluidCapacityTooltip(pMouseX,pMouseY,getGuiLeft() + 21,getGuiTop() + 9, 24,66,this,font,guiGraphics,tank.getFluid());
        ClientUtil.drawEnergyTooltip(pMouseX,pMouseY,getGuiLeft() + 135,getGuiTop() + 9, 24,66,this,font,guiGraphics,energy.getEnergyStored());
    }
}
