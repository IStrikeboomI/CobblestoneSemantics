package Strikeboom.cobblestonesemantics.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.StagedVertexBuffer;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.block.FluidStateModelSet;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.fluid.FluidTintSource;
import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Matrix3x2fStack;
import org.joml.Matrix4f;

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
        if (amount <= 0) return; // Prevent rendering empty tanks

        float fillPercentage = (float) amount / capacityMb;
        int filledHeight = Math.round(fillPercentage * height);
        int startY = yPosition + (height - filledHeight);

        int spriteSize = 16;
        // Loop over the width and the SCALED height
        for (int xTile = 0; xTile < width; xTile += spriteSize) {
            for (int yTile = 0; yTile < filledHeight; yTile += spriteSize) {

                int drawW = Math.min(spriteSize, width - xTile);
                int drawH = Math.min(spriteSize, filledHeight - yTile);

                int drawX = xPosition + xTile;
                // Anchor to the bottom of the FILLED area, not the total max area
                int drawY = startY + filledHeight - yTile - drawH;

                float u0 = fluidStillSprite.getU0();
                float u1 = u0 + (fluidStillSprite.getU1() - u0) * (drawW / (float) spriteSize);

                float v0 = fluidStillSprite.getV0();
                float v1 = v0 + (fluidStillSprite.getV1() - v0) * (drawH / (float) spriteSize);

                guiGraphics.blit(
                        fluidStillSprite.atlasLocation(),
                        drawX,
                        drawY,
                        drawX + drawW,
                        drawY + drawH,
                        u0,
                        u1,
                        v0,
                        v1
                );
            }
        }
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
