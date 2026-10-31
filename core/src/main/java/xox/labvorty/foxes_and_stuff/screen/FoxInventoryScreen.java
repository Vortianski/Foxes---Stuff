package xox.labvorty.foxes_and_stuff.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import xox.labvorty.foxes_and_stuff.FoxesStuffMod;
import xox.labvorty.foxes_and_stuff.inventory.FoxInventoryMenu;

public class FoxInventoryScreen extends AbstractContainerScreen<FoxInventoryMenu> {
    private static final Identifier GUI_TEXTURE = FoxesStuffMod.location("textures/gui/fox_inventory.png");
    private static final Identifier HEART_CONTAINER = Identifier.withDefaultNamespace("hud/heart/vehicle_container");
    private static final Identifier HEART_FULL = Identifier.withDefaultNamespace("hud/heart/vehicle_full");
    private static final Identifier HEART_HALF = Identifier.withDefaultNamespace("hud/heart/vehicle_half");

    private static final int HEART_SIZE = 9;
    private static final int HEART_STEP = 8;
    private static final int HEART_COUNT = 5;
    private static final int HEARTS_X = 122;
    private static final int HEARTS_Y = 20;

    private static final int HEARTS_WIDTH = (HEART_COUNT - 1) * HEART_STEP + HEART_SIZE;
    private final Fox fox;

    public FoxInventoryScreen(FoxInventoryMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 176, 166);
        this.fox = menu.getFox();
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        int x = this.leftPos;
        int y = this.topPos;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, x, y, 0.0F, 0.0F,
                this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

        if (this.fox != null) {
            int entityX = x + 88;
            int entityY = y + 60;
            InventoryScreen.extractEntityInInventoryFollowsMouse(
                    guiGraphics,
                    entityX - 20, entityY - 40,
                    entityX + 20, entityY,
                    30, 0.0625F,
                    (float) mouseX, (float) mouseY,
                    this.fox);
            renderHearts(guiGraphics);
        }
    }

    private void renderHearts(GuiGraphicsExtractor guiGraphics) {
        float max = this.fox.getMaxHealth();
        float ratio = max > 0.0F ? Mth.clamp(this.fox.getHealth() / max, 0.0F, 1.0F) : 0.0F;
        float filled = ratio * HEART_COUNT * 2.0F;

        int startX = this.leftPos + HEARTS_X;
        int y = this.topPos + HEARTS_Y;

        for (int i = 0; i < HEART_COUNT; i++) {
            int hx = startX + i * HEART_STEP;
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, HEART_CONTAINER, hx, y, HEART_SIZE, HEART_SIZE);

            int fromRight = HEART_COUNT - 1 - i;
            float remaining = filled - fromRight * 2.0F;
            if (remaining >= 2.0F) {
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, HEART_FULL, hx, y, HEART_SIZE, HEART_SIZE);
            } else if (remaining > 0.0F) {
                guiGraphics.pose().pushMatrix();
                guiGraphics.pose().translate(hx + HEART_SIZE, y);
                guiGraphics.pose().scale(-1.0F, 1.0F);
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, HEART_HALF, 0, 0, HEART_SIZE, HEART_SIZE);
                guiGraphics.pose().popMatrix();
            }
        }
    }

    @Override
    public void extractRenderState(@NotNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);

        if (this.fox != null) {
            int hx = this.leftPos + HEARTS_X;
            int hy = this.topPos + HEARTS_Y;
            if (mouseX >= hx && mouseX < hx + HEARTS_WIDTH && mouseY >= hy && mouseY < hy + HEART_SIZE) {
                guiGraphics.setTooltipForNextFrame(this.font,
                        Component.translatable("gui.foxesstuff.fox_health",
                                String.format("%.1f", this.fox.getHealth()),
                                String.format("%.1f", this.fox.getMaxHealth())),
                        mouseX, mouseY);
            }
        }
    }
}
