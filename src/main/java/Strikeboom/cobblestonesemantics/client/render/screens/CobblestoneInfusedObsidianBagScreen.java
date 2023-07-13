package Strikeboom.cobblestonesemantics.client.render.screens;

import Strikeboom.cobblestonesemantics.menus.CobblestoneInfusedObsidianBagMenu;
import net.minecraft.client.gui.GuiGraphics;
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
    protected void renderBg(GuiGraphics guiGraphics, float p_97788_, int p_97789_, int p_97790_) {
        int i = (this.width - this.getXSize()) / 2;
        int j = (this.height - this.getYSize()) / 2;
        guiGraphics.blit(new ResourceLocation("textures/gui/container/generic_54.png"),i, j, 0, 0, this.getXSize(), 3 * 18 + 17);
        guiGraphics.blit(new ResourceLocation("textures/gui/container/generic_54.png"),i, j + 3 * 18 + 16, 0, 126, this.getXSize(), 96);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(guiGraphics, pMouseX, pMouseY);
    }
}
