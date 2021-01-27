package Strikeboom.cobblestonesemantics.handlers.util;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collections;

public final class ClientUtil{
    public static void renderFluidBar(Minecraft mc, FluidStack fluidStack,int fluidHandlerMaxCapacity,int xPos,int yPos,int width,int height) {
        if (fluidStack != null && fluidStack.getFluid() != null) {
            TextureAtlasSprite sprite = mc.getTextureMapBlocks().getAtlasSprite(fluidStack.getFluid().getStill().toString());

            int fluidHeight = (fluidStack.amount * height) / fluidHandlerMaxCapacity;
            if (fluidStack.amount > 0 && fluidHeight < 1) {
                fluidHeight = 1;
            }
            if (fluidHeight > height) {
                fluidHeight = height;
            }

            mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            GlStateManager.color(((fluidStack.getFluid().getColor() >> 16) & 0xFF) / 255f,((fluidStack.getFluid().getColor() >> 8) & 0xFF) / 255f,(fluidStack.getFluid().getColor() & 0xFF) / 255f,((fluidStack.getFluid().getColor() >> 24) & 0xFF) / 255f);

            final int xTileCount = width / 16;
            final int xRemainder = width - (xTileCount * 16);
            final int yTileCount = fluidHeight / 16;
            final int yRemainder = fluidHeight - (yTileCount * 16);

            final int yStart = yPos + height;

            for (int xTile = 0; xTile <= xTileCount; xTile++) {
                for (int yTile = 0; yTile <= yTileCount; yTile++) {
                    int w = (xTile == xTileCount) ? xRemainder : 16;
                    int h = (yTile == yTileCount) ? yRemainder : 16;
                    int x = xPos + (xTile * 16);
                    int y = yStart - ((yTile + 1) * 16);
                    if (w > 0 && h > 0) {
                        int maskTop = 16 - h;
                        int maskRight = 16 - w;

                        double uMin = sprite.getMinU();
                        double uMax = sprite.getMaxU();
                        double vMin = sprite.getMinV();
                        double vMax = sprite.getMaxV();
                        uMax -= (maskRight / 16.0f * (uMax - uMin));
                        vMax -= (maskTop / 16.0f * (vMax - vMin));

                        Tessellator tessellator = Tessellator.getInstance();
                        BufferBuilder bufferBuilder = tessellator.getBuffer();
                        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
                        bufferBuilder.pos(x, y + 16, 0).tex(uMin, vMax).endVertex();
                        bufferBuilder.pos(x + 16 - maskRight, y + 16, 0).tex(uMax, vMax).endVertex();
                        bufferBuilder.pos(x + 16 - maskRight, y + maskTop, 0).tex(uMax, vMin).endVertex();
                        bufferBuilder.pos(x, y + maskTop, 0).tex(uMin, vMin).endVertex();
                        tessellator.draw();
                    }
                }
            }
        }
    }
    public static void drawFluidCapacityTooltip(int mouseX, int mouseY, int xPos,int yPos, int width,int height, GuiScreen gui, FluidStack fluidStack) {
        if (fluidStack != null && fluidStack.getFluid() != null) {
            if (mouseX > xPos && mouseX < xPos + width
                    && mouseY > yPos && mouseY < yPos + height ) {
                gui.drawHoveringText(Collections.singletonList(fluidStack.getLocalizedName() + " " + fluidStack.amount + "mB"), mouseX, mouseY);
            }
        }
    }
    public static void drawEnergyTooltip(int mouseX, int mouseY, int xPos,int yPos, int width,int height, GuiScreen gui, int energy) {
        if (mouseX > xPos && mouseX < xPos + width
                && mouseY > yPos && mouseY < yPos + height ) {
            gui.drawHoveringText(Collections.singletonList(energy + " RF/FE"), mouseX, mouseY);
        }
    }
    public static void registerFluidTexture(Fluid fluid) {
        ModelResourceLocation loc = new ModelResourceLocation(CobblestoneSemantics.MOD_ID + ":" + fluid.getName(), "fluid");
        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(fluid.getBlock()), stack -> loc);
        ModelLoader.setCustomStateMapper(fluid.getBlock(), new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return loc;
            }
        });

    }
}
