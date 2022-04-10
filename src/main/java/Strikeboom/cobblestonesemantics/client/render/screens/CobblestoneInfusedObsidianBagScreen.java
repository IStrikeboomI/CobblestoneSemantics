package Strikeboom.cobblestonesemantics.client.render.screens;

import Strikeboom.cobblestonesemantics.guis.containers.CobblestoneInfusedObsidianBagContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CobblestoneInfusedObsidianBagScreen extends ContainerScreen<CobblestoneInfusedObsidianBagContainer> {
    public CobblestoneInfusedObsidianBagScreen(CobblestoneInfusedObsidianBagContainer pMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
        super(pMenu, pPlayerInventory, pTitle);

        titleLabelX = getGuiLeft() + 3;
    }

    @Override
    public void render(MatrixStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(MatrixStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        minecraft.getTextureManager().bind(new ResourceLocation("textures/gui/container/generic_54.png"));
        int i = (this.width - this.getXSize()) / 2;
        int j = (this.height - this.getYSize()) / 2;
        blit(pPoseStack,i, j, 0, 0, this.getXSize(), 3 * 18 + 17);
        blit(pPoseStack,i, j + 3 * 18 + 16, 0, 126, this.getXSize(), 96);
    }
}
