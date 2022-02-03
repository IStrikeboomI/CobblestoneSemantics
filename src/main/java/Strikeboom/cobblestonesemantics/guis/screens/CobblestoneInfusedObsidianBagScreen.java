package Strikeboom.cobblestonesemantics.guis.screens;

import Strikeboom.cobblestonesemantics.guis.menus.CobblestoneInfusedObsidianBagMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CobblestoneInfusedObsidianBagScreen extends AbstractContainerScreen<CobblestoneInfusedObsidianBagMenu> {
    public CobblestoneInfusedObsidianBagScreen(CobblestoneInfusedObsidianBagMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);

        titleLabelX = getGuiLeft() + 3;
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShaderTexture(0,new ResourceLocation("textures/gui/container/generic_54.png"));
        int i = (this.width - this.getXSize()) / 2;
        int j = (this.height - this.getYSize()) / 2;
        blit(pPoseStack,i, j, 0, 0, this.getXSize(), 3 * 18 + 17);
        blit(pPoseStack,i, j + 3 * 18 + 16, 0, 126, this.getXSize(), 96);    }
}
