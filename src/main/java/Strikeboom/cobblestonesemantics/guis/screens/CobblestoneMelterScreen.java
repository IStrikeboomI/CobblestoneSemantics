package Strikeboom.cobblestonesemantics.guis.screens;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.guis.menus.CobblestoneMelterMenu;
import Strikeboom.cobblestonesemantics.util.ClientUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class CobblestoneMelterScreen extends AbstractContainerScreen<CobblestoneMelterMenu> {
    FluidTank tank;
    public CobblestoneMelterScreen(CobblestoneMelterMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        tank = (FluidTank) menu.blockEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).orElse(null);
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
        ClientUtil.drawFluidCapacityTooltip(pMouseX,pMouseY,getGuiLeft() + 127,getGuiTop() + 9, 24,66,this,pPoseStack,tank.getFluid());
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShaderTexture(0, new ResourceLocation(CobblestoneSemantics.MOD_ID, "textures/gui/container/cobblestone_melter.png"));
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(pPoseStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        this.blit(pPoseStack,getGuiLeft()+81,getGuiTop()+32,176,0,this.menu.blockEntity.getCooldown() * 23 / this.menu.blockEntity.getDelay(),16);
        ClientUtil.renderFluidBar(pPoseStack,getGuiLeft() + 127, getGuiTop() + 9,24,66,tank.getFluid(),tank.getCapacity());
    }
}
