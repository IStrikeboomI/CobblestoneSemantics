package Strikeboom.cobblestonesemantics.guis.gui;

import Strikeboom.cobblestonesemantics.guis.container.ContainerCobblestoneBag;
import Strikeboom.cobblestonesemantics.guis.container.ContainerCobblestoneInfusedObsidianBag;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiCobblestoneInfusedObsidianBag extends GuiContainer {
    IInventory playerInv;
    ItemStack stack;
    public GuiCobblestoneInfusedObsidianBag(IInventory playerInv, ItemStack stack) {
        super(new ContainerCobblestoneInfusedObsidianBag(playerInv,stack));
        this.playerInv = playerInv;
        this.stack = stack;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX,mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/generic_54.png"));
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, 3 * 18 + 17);
        this.drawTexturedModalRect(i, j + 3 * 18 + 16, 0, 126, this.xSize, 96);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = this.stack.getDisplayName();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString(this.playerInv.getDisplayName().getFormattedText(), (this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2) + 5, 73, 4210752);
    }
}
