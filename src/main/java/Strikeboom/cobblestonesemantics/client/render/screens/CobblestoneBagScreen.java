package Strikeboom.cobblestonesemantics.client.render.screens;

import Strikeboom.cobblestonesemantics.menus.CobblestoneBagMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CobblestoneBagScreen extends AbstractContainerScreen<CobblestoneBagMenu> {
    public CobblestoneBagScreen(CobblestoneBagMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float p_97788_, int p_97789_, int p_97790_) {
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(RenderType::guiTextured,ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/container/dispenser.png"), relX, relY, 0, 0, this.imageWidth, this.imageHeight,256,256);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(guiGraphics,pMouseX,pMouseY,pPartialTick);
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(guiGraphics, pMouseX, pMouseY);
    }

}
