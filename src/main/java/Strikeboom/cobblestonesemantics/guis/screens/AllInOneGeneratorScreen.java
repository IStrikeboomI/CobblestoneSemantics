package Strikeboom.cobblestonesemantics.guis.screens;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.guis.blockentities.energystorage.CobblestoneSemanticsEnergyStorage;
import Strikeboom.cobblestonesemantics.guis.menus.AllInOneGeneratorMenu;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlocks;
import Strikeboom.cobblestonesemantics.util.ClientUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;

public class AllInOneGeneratorScreen extends AbstractContainerScreen<AllInOneGeneratorMenu> {
    CobblestoneSemanticsEnergyStorage energy;
    public AllInOneGeneratorScreen(AllInOneGeneratorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);

        energy = (CobblestoneSemanticsEnergyStorage) menu.blockEntity.getCapability(CapabilityEnergy.ENERGY).orElse(null);
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
        ClientUtil.drawEnergyTooltip(pMouseX,pMouseY,getGuiLeft() + 135,getGuiTop() + 9, 24,66,this,pPoseStack,energy.getEnergyStored());

        itemRenderer.renderAndDecorateFakeItem(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_10.get()),getGuiLeft() + 9,getGuiTop() + 51);
        itemRenderer.renderAndDecorateFakeItem(new ItemStack(CobblestoneSemanticsBlocks.LAVA_GENERATOR.get()),getGuiLeft() + 5,getGuiTop() + 14);
        itemRenderer.renderAndDecorateFakeItem(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_MELTER.get()),getGuiLeft() + 97,getGuiTop() + 51);

    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShaderTexture(0, new ResourceLocation(CobblestoneSemantics.MOD_ID, "textures/gui/container/all_in_one_generator.png"));
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(pPoseStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        this.blit(pPoseStack,getGuiLeft()+101,getGuiTop()+34,176,0,this.menu.blockEntity.getCooldown() * 23 / this.menu.blockEntity.getDelay(),16);
        this.blit(pPoseStack,getGuiLeft()+135, (int) (getGuiTop()+ 10 + (66 - (float)(this.energy.getEnergyStored() * 66) / this.energy.getMaxEnergyStored())),176,16,24,this.energy.getEnergyStored() * 66 / this.energy.getMaxEnergyStored());

    }

}
