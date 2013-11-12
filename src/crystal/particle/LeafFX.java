package crystal.particle;

import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LeafFX extends EntityFX
{
    public LeafFX(World par1World, double par2, double par4, double par6, Icon icon)
    {
        super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
        this.setParticleIcon(icon);
        this.particleRed = this.particleBlue = 0.5F;
        this.particleGreen = 0.7F;
        this.particleGravity = 0.1f;
        this.particleMaxAge = (int) (8.0F / (this.rand.nextFloat() * 0.9F + 0.1F));
        this.particleScale /= 1.5F;
    }

    public LeafFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, Icon icon)
    {
        this(par1World, par2, par4, par6, icon);
        this.motionX += par8;
        this.motionY += par10;
        this.motionZ += par12;
    }

    public int getFXLayer ()
    {
        return 2;
    }

    public void renderParticle (Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        float minU = particleIcon.getMinU();
        float maxU = particleIcon.getMaxU();
        float minV = particleIcon.getMinV();
        float maxV = particleIcon.getMaxV();
        float f10 = 0.1F * this.particleScale;

        float f11 = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) par2 - interpPosX);
        float f12 = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) par2 - interpPosY);
        float f13 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) par2 - interpPosZ);
        float f14 = 1.0F;
        par1Tessellator.setColorOpaque_F(f14 * this.particleRed, f14 * this.particleGreen, f14 * this.particleBlue);
        //x, y, z, u, v
        par1Tessellator.addVertexWithUV((double) (f11 - par3 * f10 - par6 * f10), (double) (f12 - par4 * f10), (double) (f13 - par5 * f10 - par7 * f10), (double) minU, (double) maxV);
        par1Tessellator.addVertexWithUV((double) (f11 - par3 * f10 + par6 * f10), (double) (f12 + par4 * f10), (double) (f13 - par5 * f10 + par7 * f10), (double) minU, (double) minV);
        par1Tessellator.addVertexWithUV((double) (f11 + par3 * f10 + par6 * f10), (double) (f12 + par4 * f10), (double) (f13 + par5 * f10 + par7 * f10), (double) maxU, (double) minV);
        par1Tessellator.addVertexWithUV((double) (f11 + par3 * f10 - par6 * f10), (double) (f12 - par4 * f10), (double) (f13 + par5 * f10 - par7 * f10), (double) maxU, (double) maxV);
    }
}
