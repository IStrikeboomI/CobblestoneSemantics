package Strikeboom.cobblestonesemantics.client.render.screens;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.guis.containers.LavaGeneratorContainer;
import Strikeboom.cobblestonesemantics.guis.tileentities.energystorage.CobblestoneSemanticsEnergyStorage;
import Strikeboom.cobblestonesemantics.util.ClientUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class LavaGeneratorScreen extends ContainerScreen<LavaGeneratorContainer> {
    FluidTank tank;
    CobblestoneSemanticsEnergyStorage energy;
    public LavaGeneratorScreen(LavaGeneratorContainer pMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        tank = (FluidTank) menu.blockEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).orElse(null);
        energy = (CobblestoneSemanticsEnergyStorage) menu.blockEntity.getCapability(CapabilityEnergy.ENERGY).orElse(null);

        titleLabelX -= getGuiLeft() - 43;
        inventoryLabelX -= getGuiLeft() - 55;
    }

    @Override
    public void render(MatrixStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
        ClientUtil.drawFluidCapacityTooltip(pMouseX,pMouseY,getGuiLeft() + 21,getGuiTop() + 9, 24,66,this,pPoseStack,tank.getFluid());
        ClientUtil.drawEnergyTooltip(pMouseX,pMouseY,getGuiLeft() + 135,getGuiTop() + 9, 24,66,this,pPoseStack,energy.getEnergyStored());
    }

    @Override
    protected void renderBg(MatrixStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        minecraft.getTextureManager().bind(new ResourceLocation(CobblestoneSemantics.MOD_ID, "textures/gui/container/lava_generator.png"));
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(pPoseStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        this.blit(pPoseStack,getGuiLeft()+81,getGuiTop()+32,176,0,this.menu.blockEntity.getCooldown() * 23 / this.menu.blockEntity.getDelay(),16);
        this.blit(pPoseStack,getGuiLeft()+135, (int) (getGuiTop()+ 10 + (66 - (float)(this.energy.getEnergyStored() * 66) / this.energy.getMaxEnergyStored())),176,16,24,this.energy.getEnergyStored() * 66 / this.energy.getMaxEnergyStored());

        ClientUtil.renderFluidBar(pPoseStack,getGuiLeft() + 21, getGuiTop() + 9,24,66,tank.getFluid(),tank.getCapacity());

    }
}
