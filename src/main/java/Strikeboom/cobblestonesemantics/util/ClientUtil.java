package Strikeboom.cobblestonesemantics.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Matrix4f;

import javax.annotation.Nullable;

public class ClientUtil {
    @SuppressWarnings("deprecation")
    public static void renderFluidBar(GuiGraphics guiGraphics, final int xPosition, final int yPosition, final int width, final int height, @Nullable FluidStack fluidStack, int capacityMb) {
        if (fluidStack == null) {
            return;
        }
        Fluid fluid = fluidStack.getFluid();
        if (fluidStack.isEmpty()) {
            return;
        }
        Minecraft minecraft = Minecraft.getInstance();
        IClientFluidTypeExtensions attributes = IClientFluidTypeExtensions.of(fluid);
        ResourceLocation fluidStill = attributes.getStillTexture();
        TextureAtlasSprite fluidStillSprite = minecraft.getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(fluidStill);

        int fluidColor = attributes.getTintColor(fluidStack);

        int amount = fluidStack.getAmount();
        int scaledAmount = (amount * height) / capacityMb;
        if (amount > 0 && scaledAmount < 1) {
            scaledAmount = 1;
        }
        if (scaledAmount > height) {
            scaledAmount = height;
        }
        VertexConsumer bufferBuilder = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderType.guiTextured(TextureAtlas.LOCATION_BLOCKS));        Matrix4f matrix = guiGraphics.pose().last().pose();
        RenderSystem.setShaderColor(((fluidColor >> 16) & 0xFF) / 255f,((fluidColor >> 8) & 0xFF) / 255f,(fluidColor & 0xFF) / 255f,((fluidColor >> 24) & 0xFF) / 255f);

        final int xTileCount = width / 16;
        final int xRemainder = width - (xTileCount * 16);
        final int yTileCount = scaledAmount / 16;
        final int yRemainder = scaledAmount - (yTileCount * 16);

        final int yStart = yPosition + height;

        for (int xTile = 0; xTile <= xTileCount; xTile++) {
            for (int yTile = 0; yTile <= yTileCount; yTile++) {
                int w = (xTile == xTileCount) ? xRemainder : 16;
                int h = (yTile == yTileCount) ? yRemainder : 16;
                int x = xPosition + (xTile * 16);
                int y = yStart - ((yTile + 1) * 16);
                if (w > 0 && h > 0) {
                    int maskTop = 16 - h;
                    int maskRight = 16 - w;

                    float uMin = fluidStillSprite.getU0();
                    float uMax = fluidStillSprite.getU1();
                    float vMin = fluidStillSprite.getV0();
                    float vMax = fluidStillSprite.getV1();
                    uMax = uMax - (maskRight / 16F * (uMax - uMin));
                    vMax = vMax - (maskTop / 16F * (vMax - vMin));

                    bufferBuilder.addVertex(matrix, x, y + 16, 100).setUv(uMin, vMax);
                    bufferBuilder.addVertex(matrix, x + 16 - maskRight, y + 16, 100).setUv(uMax, vMax);
                    bufferBuilder.addVertex(matrix, x + 16 - maskRight, y + maskTop, 100).setUv(uMax, vMin);
                    bufferBuilder.addVertex(matrix, x, y + maskTop, 100).setUv(uMin, vMin);
                }
            }
        }
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }
    public static void drawFluidCapacityTooltip(int mouseX, int mouseY, int xPos, int yPos, int width, int height, AbstractContainerScreen<?> gui, Font font, GuiGraphics guiGraphics, FluidStack fluidStack) {
        if (fluidStack != null && !fluidStack.getFluid().isSame(Fluids.EMPTY)) {
            if (mouseX > xPos && mouseX < xPos + width
                    && mouseY > yPos && mouseY < yPos + height ) {
                guiGraphics.renderTooltip(font,Component.literal(fluidStack.getHoverName().getString() + " " + fluidStack.getAmount() + " mB"), mouseX, mouseY);
            }
        }
    }
    public static void drawEnergyTooltip(int mouseX, int mouseY, int xPos, int yPos, int width, int height, AbstractContainerScreen<?> gui, Font font, GuiGraphics guiGraphics, int energy) {
        if (mouseX > xPos && mouseX < xPos + width
                && mouseY > yPos && mouseY < yPos + height ) {
            guiGraphics.renderTooltip(font,Component.literal(energy + " RF/FE") , mouseX, mouseY);
        }
    }


}
