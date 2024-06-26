package dartcraftReloaded.tileEntity;

import dartcraftReloaded.Constants;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityInfuserRenderer extends TileEntitySpecialRenderer<TileEntityInfuser> {

    private static final ResourceLocation TEXTURE_BOOK = new ResourceLocation(Constants.modId, "textures/entity/infuser_book.png");
    private final ModelBook modelBook = new ModelBook();

    public TileEntityInfuserRenderer() {
    }

    @Override
    public void render(TileEntityInfuser te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if (te.handler.getStackInSlot(10).isEmpty()) return;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x + 0.5F, (float)y + 0.67F, (float)z + 0.5F);
        float f = (float)te.tickCount + partialTicks;
        GlStateManager.translate(0.0F, 0.1F + MathHelper.sin(f * 0.1F) * 0.01F, 0.0F);

        float f1;
        for(f1 = te.bookRotation - te.bookRotationPrev; f1 >= 3.1415927F; f1 -= 6.2831855F) {
        }

        while(f1 < -3.1415927F) {
            f1 += 6.2831855F;
        }

        float f2 = te.bookRotationPrev + f1 * partialTicks;
        GlStateManager.rotate(-f2 * 57.295776F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(80.0F, 0.0F, 0.0F, 1.0F);
        this.bindTexture(TEXTURE_BOOK);
        float f3 = te.pageFlipPrev + (te.pageFlip - te.pageFlipPrev) * partialTicks + 0.25F;
        float f4 = te.pageFlipPrev + (te.pageFlip - te.pageFlipPrev) * partialTicks + 0.75F;
        f3 = (f3 - (float)MathHelper.fastFloor(f3)) * 1.6F - 0.3F;
        f4 = (f4 - (float)MathHelper.fastFloor(f4)) * 1.6F - 0.3F;
        if (f3 < 0.0F) {
            f3 = 0.0F;
        }

        if (f4 < 0.0F) {
            f4 = 0.0F;
        }

        if (f3 > 1.0F) {
            f3 = 1.0F;
        }

        if (f4 > 1.0F) {
            f4 = 1.0F;
        }

        float f5 = te.bookSpreadPrev + (te.bookSpread - te.bookSpreadPrev) * partialTicks;
        GlStateManager.enableCull();
        this.modelBook.render((Entity)null, f, f3, f4, f5, 0.0F, 0.0625F);
        GlStateManager.popMatrix();
    }
}

