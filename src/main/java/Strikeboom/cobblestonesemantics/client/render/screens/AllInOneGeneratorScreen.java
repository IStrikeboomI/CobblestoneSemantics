package Strikeboom.cobblestonesemantics.client.render.screens;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.blockentities.energystorage.CobblestoneSemanticsEnergyStorage;
import Strikeboom.cobblestonesemantics.menus.AllInOneGeneratorMenu;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlocks;
import Strikeboom.cobblestonesemantics.util.ClientUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;

public class AllInOneGeneratorScreen extends AbstractContainerScreen<AllInOneGeneratorMenu> {
    CobblestoneSemanticsEnergyStorage energy;
    public AllInOneGeneratorScreen(AllInOneGeneratorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);

        energy = (CobblestoneSemanticsEnergyStorage) pMenu.blockEntity.getLevel().getCapability(Capabilities.EnergyStorage.BLOCK,menu.blockEntity.getBlockPos(),null);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float p_97788_, int p_97789_, int p_97790_) {
        //RenderSystem.setShaderTexture(0, new ResourceLocation(CobblestoneSemantics.MOD_ID, "textures/gui/container/all_in_one_generator.png"));
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(RenderType::guiTextured,ResourceLocation.fromNamespaceAndPath(CobblestoneSemantics.MOD_ID, "textures/gui/container/all_in_one_generator.png"),relX, relY, 0, 0, this.imageWidth, this.imageHeight,256,256);
        guiGraphics.blit(RenderType::guiTextured,ResourceLocation.fromNamespaceAndPath(CobblestoneSemantics.MOD_ID, "textures/gui/container/all_in_one_generator.png"),getGuiLeft()+101,getGuiTop()+34,176,0,this.menu.blockEntity.getCooldown() * 23 / this.menu.blockEntity.getDelay(),16,256,256);
        guiGraphics.blit(RenderType::guiTextured,ResourceLocation.fromNamespaceAndPath(CobblestoneSemantics.MOD_ID, "textures/gui/container/all_in_one_generator.png"),getGuiLeft()+135, (int) (getGuiTop()+ 10 + (66 - (float)(this.energy.getEnergyStored() * 66) / this.energy.getMaxEnergyStored())),176,16,24,this.energy.getEnergyStored() * 66 / this.energy.getMaxEnergyStored(),256,256);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(guiGraphics,pMouseX,pMouseY,pPartialTick);
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(guiGraphics, pMouseX, pMouseY);
        ClientUtil.drawEnergyTooltip(pMouseX,pMouseY,getGuiLeft() + 135,getGuiTop() + 9, 24,66,this,font,guiGraphics,energy.getEnergyStored());

        guiGraphics.renderFakeItem(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_10.get()),getGuiLeft() + 9,getGuiTop() + 51);
        guiGraphics.renderFakeItem(new ItemStack(CobblestoneSemanticsBlocks.LAVA_GENERATOR.get()),getGuiLeft() + 5,getGuiTop() + 14);
        guiGraphics.renderFakeItem(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_MELTER.get()),getGuiLeft() + 97,getGuiTop() + 51);
    }

}
