package Strikeboom.cobblestonesemantics.guis.gui;

import Strikeboom.cobblestonesemantics.guis.container.ContainerCobblestoneBag;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiCobblestoneBag extends GuiContainer {
    IInventory playerInv;
    ItemStack stack;
    public GuiCobblestoneBag(IInventory playerInv, ItemStack stack) {
        super(new ContainerCobblestoneBag(playerInv,stack));
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
        this.mc.getTextureManager().bindTexture(new ResourceLocation("minecraft", "textures/gui/container/dispenser.png"));
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = this.stack.getDisplayName();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString(this.playerInv.getDisplayName().getFormattedText(), (this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2) - 40, 73, 4210752);
    }
}
