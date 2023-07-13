package Strikeboom.cobblestonesemantics.client.render.screens;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.menus.CobblestoneMelterMenu;
import Strikeboom.cobblestonesemantics.util.ClientUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class CobblestoneMelterScreen extends AbstractContainerScreen<CobblestoneMelterMenu> {
    FluidTank tank;
    public CobblestoneMelterScreen(CobblestoneMelterMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        tank = (FluidTank) menu.blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER).orElse(null);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float p_97788_, int p_97789_, int p_97790_) {
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(new ResourceLocation(CobblestoneSemantics.MOD_ID, "textures/gui/container/cobblestone_melter.png"), relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        guiGraphics.blit(new ResourceLocation(CobblestoneSemantics.MOD_ID, "textures/gui/container/cobblestone_melter.png"),getGuiLeft()+81,getGuiTop()+32,176,0,this.menu.blockEntity.getCooldown() * 23 / this.menu.blockEntity.getDelay(),16);
        ClientUtil.renderFluidBar(guiGraphics,getGuiLeft() + 127, getGuiTop() + 9,24,66,tank.getFluid(),tank.getCapacity());

    }

    @Override
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(guiGraphics, pMouseX, pMouseY);
        ClientUtil.drawFluidCapacityTooltip(pMouseX,pMouseY,getGuiLeft() + 127,getGuiTop() + 9, 24,66,this,font,guiGraphics,tank.getFluid());
    }

}
