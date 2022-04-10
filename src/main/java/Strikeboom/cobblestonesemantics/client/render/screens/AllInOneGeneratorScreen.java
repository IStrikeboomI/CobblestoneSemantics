package Strikeboom.cobblestonesemantics.client.render.screens;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.guis.containers.AllInOneGeneratorContainer;
import Strikeboom.cobblestonesemantics.guis.tileentities.energystorage.CobblestoneSemanticsEnergyStorage;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlocks;
import Strikeboom.cobblestonesemantics.util.ClientUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.energy.CapabilityEnergy;

public class AllInOneGeneratorScreen extends ContainerScreen<AllInOneGeneratorContainer> {
    CobblestoneSemanticsEnergyStorage energy;
    public AllInOneGeneratorScreen(AllInOneGeneratorContainer pMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
        super(pMenu, pPlayerInventory, pTitle);

        energy = (CobblestoneSemanticsEnergyStorage) menu.blockEntity.getCapability(CapabilityEnergy.ENERGY).orElse(null);
    }

    @Override
    public void render(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pMatrixStack);
        super.render(pMatrixStack, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pMatrixStack, pMouseX, pMouseY);
        ClientUtil.drawEnergyTooltip(pMouseX,pMouseY,getGuiLeft() + 135,getGuiTop() + 9, 24,66,this,pMatrixStack,energy.getEnergyStored());

        itemRenderer.renderAndDecorateFakeItem(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_10.get()),getGuiLeft() + 9,getGuiTop() + 51);
        itemRenderer.renderAndDecorateFakeItem(new ItemStack(CobblestoneSemanticsBlocks.LAVA_GENERATOR.get()),getGuiLeft() + 5,getGuiTop() + 14);
        itemRenderer.renderAndDecorateFakeItem(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_MELTER.get()),getGuiLeft() + 97,getGuiTop() + 51);

    }

    @Override
    protected void renderBg(MatrixStack pMatrixStack, float pPartialTick, int pMouseX, int pMouseY) {
        minecraft.getTextureManager().bind(new ResourceLocation(CobblestoneSemantics.MOD_ID, "textures/gui/container/all_in_one_generator.png"));
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;

        this.blit(pMatrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        this.blit(pMatrixStack,getGuiLeft()+101,getGuiTop()+34,176,0,this.menu.blockEntity.getCooldown() * 23 / this.menu.blockEntity.getDelay(),16);
        this.blit(pMatrixStack,getGuiLeft()+135, (int) (getGuiTop()+ 10 + (66 - (float)(this.energy.getEnergyStored() * 66) / this.energy.getMaxEnergyStored())),176,16,24,this.energy.getEnergyStored() * 66 / this.energy.getMaxEnergyStored());

    }

}
