package Strikeboom.cobblestonesemantics.client.render.screens;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.blockentities.energystorage.CobblestoneSemanticsEnergyStorage;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlocks;
import Strikeboom.cobblestonesemantics.menus.AllInOneGeneratorMenu;
import Strikeboom.cobblestonesemantics.util.ClientUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;

public class AllInOneGeneratorScreen extends AbstractContainerScreen<AllInOneGeneratorMenu> {
    CobblestoneSemanticsEnergyStorage energy;
    public AllInOneGeneratorScreen(AllInOneGeneratorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);

        energy = (CobblestoneSemanticsEnergyStorage) pMenu.blockEntity.getLevel().getCapability(Capabilities.Energy.BLOCK,menu.blockEntity.getBlockPos(),null);
    }
    

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float a) {
        super.extractBackground(guiGraphics,mouseX,mouseY,a);
        //RenderSystem.setShaderTexture(0, new Identifier(CobblestoneSemantics.MOD_ID, "textures/gui/container/all_in_one_generator.png"));
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,Identifier.fromNamespaceAndPath(CobblestoneSemantics.MOD_ID, "textures/gui/container/all_in_one_generator.png"),relX, relY, 0, 0, this.imageWidth, this.imageHeight,256,256);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,Identifier.fromNamespaceAndPath(CobblestoneSemantics.MOD_ID, "textures/gui/container/all_in_one_generator.png"),getLeftPos()+101,getTopPos()+34,176,0,this.menu.blockEntity.getCooldown() * 23 / this.menu.blockEntity.getDelay(),16,256,256);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,Identifier.fromNamespaceAndPath(CobblestoneSemantics.MOD_ID, "textures/gui/container/all_in_one_generator.png"),getLeftPos()+135, (int) (getTopPos()+ 9 + (66 - Math.floor((float)(this.energy.getAmountAsInt() * 66) / this.energy.getCapacityAsInt()))),176,16,24,this.energy.getAmountAsInt() * 66 / this.energy.getCapacityAsInt(),256,256);
    }
    

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.extractBackground(guiGraphics,pMouseX,pMouseY,pPartialTick);
        super.extractRenderState(guiGraphics, pMouseX, pMouseY, pPartialTick);
        this.extractTooltip(guiGraphics, pMouseX, pMouseY);
        ClientUtil.drawEnergyTooltip(pMouseX,pMouseY,getLeftPos() + 135,getTopPos() + 9, 24,66,font,guiGraphics,energy.getAmountAsInt());
        
        guiGraphics.fakeItem(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_10.get()),getLeftPos() + 9,getTopPos() + 51);
        guiGraphics.fakeItem(new ItemStack(CobblestoneSemanticsBlocks.LAVA_GENERATOR.get()),getLeftPos() + 5,getTopPos() + 14);
        guiGraphics.fakeItem(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_MELTER.get()),getLeftPos() + 97,getTopPos() + 51);
    }

}
