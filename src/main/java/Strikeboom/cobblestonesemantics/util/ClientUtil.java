package Strikeboom.cobblestonesemantics.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.block.FluidStateModelSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.fluid.FluidTintSource;
import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.Collections;

public class ClientUtil {
    @SuppressWarnings("deprecation")
    public static void renderFluidBar(GuiGraphicsExtractor guiGraphics, final int xPosition, final int yPosition, final int width, final int height, @Nullable FluidStack fluidStack, int capacityMb) {
        if (fluidStack == null) {
            return;
        }
        Fluid fluid = fluidStack.getFluid();
        if (fluidStack.isEmpty()) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        ModelManager modelManager = minecraft.getModelManager();
        FluidStateModelSet fluidStateModelSet = modelManager.getFluidStateModelSet();
        FluidModel fluidModel = fluidStateModelSet.get(fluid.defaultFluidState());
        Material.Baked stillMaterial = fluidModel.stillMaterial();
        TextureAtlasSprite fluidStillSprite = stillMaterial.sprite();

        FluidTintSource tintSource = fluidModel.fluidTintSource();
        int fluidColor = tintSource == null ? 0xFFFFFFFF : tintSource.colorAsStack(fluidStack);

        int amount = fluidStack.getAmount();
        int scaledAmount = (amount * height) / capacityMb;
        if (amount > 0 && scaledAmount < 1) {
            scaledAmount = 1;
        }
        if (scaledAmount > height) {
            scaledAmount = height;
        }
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                fluidStillSprite.atlasLocation(),
                xPosition,
                yPosition,
                fluidStillSprite.getU0(),
                fluidStillSprite.getV0(),
                width,
                height,
                fluidStillSprite.contents().width(),
                fluidStillSprite.contents().height(),
                fluidColor);
//        VertexConsumer bufferBuilder = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderType.);
//        Matrix4f matrix = guiGraphics.pose().last().pose();
//        RenderSystem.(((fluidColor >> 16) & 0xFF) / 255f,((fluidColor >> 8) & 0xFF) / 255f,(fluidColor & 0xFF) / 255f,((fluidColor >> 24) & 0xFF) / 255f);
//
//        final int xTileCount = width / 16;
//        final int xRemainder = width - (xTileCount * 16);
//        final int yTileCount = scaledAmount / 16;
//        final int yRemainder = scaledAmount - (yTileCount * 16);
//
//        final int yStart = yPosition + height;
//
//        for (int xTile = 0; xTile <= xTileCount; xTile++) {
//            for (int yTile = 0; yTile <= yTileCount; yTile++) {
//                int w = (xTile == xTileCount) ? xRemainder : 16;
//                int h = (yTile == yTileCount) ? yRemainder : 16;
//                int x = xPosition + (xTile * 16);
//                int y = yStart - ((yTile + 1) * 16);
//                if (w > 0 && h > 0) {
//                    int maskTop = 16 - h;
//                    int maskRight = 16 - w;
//
//                    float uMin = fluidStillSprite.getU0();
//                    float uMax = fluidStillSprite.getU1();
//                    float vMin = fluidStillSprite.getV0();
//                    float vMax = fluidStillSprite.getV1();
//                    uMax = uMax - (maskRight / 16F * (uMax - uMin));
//                    vMax = vMax - (maskTop / 16F * (vMax - vMin));
//
//                    bufferBuilder.addVertex(matrix, x, y + 16, 100).setUv(uMin, vMax).setColor(fluidColor);
//                    bufferBuilder.addVertex(matrix, x + 16 - maskRight, y + 16, 100).setUv(uMax, vMax).setColor(fluidColor);
//                    bufferBuilder.addVertex(matrix, x + 16 - maskRight, y + maskTop, 100).setUv(uMax, vMin).setColor(fluidColor);
//                    bufferBuilder.addVertex(matrix, x, y + maskTop, 100).setUv(uMin, vMin).setColor(fluidColor);
//                }
//            }
//        }
//        RenderSystem.setShaderColor(1, 1, 1, 1);
    }
    public static void drawFluidCapacityTooltip(int mouseX, int mouseY, int xPos, int yPos, int width, int height, Font font, GuiGraphicsExtractor guiGraphics, FluidStack fluidStack) {
        if (fluidStack != null && !fluidStack.getFluid().isSame(Fluids.EMPTY)) {
            if (mouseX > xPos && mouseX < xPos + width
                    && mouseY > yPos && mouseY < yPos + height ) {
                guiGraphics.setComponentTooltipForNextFrame(font, Collections.singletonList(Component.literal(fluidStack.getHoverName().getString() + " " + fluidStack.getAmount() + " mB")), mouseX, mouseY);
            }
        }
    }
    public static void drawEnergyTooltip(int mouseX, int mouseY, int xPos, int yPos, int width, int height, Font font, GuiGraphicsExtractor guiGraphics, int energy) {
        if (mouseX > xPos && mouseX < xPos + width
                && mouseY > yPos && mouseY < yPos + height ) {
            guiGraphics.setComponentTooltipForNextFrame(font, Collections.singletonList(Component.literal(energy + " RF/FE")), mouseX, mouseY);
        }
    }


}
