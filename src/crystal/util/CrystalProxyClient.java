package crystal.util;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import org.lwjgl.opengl.GL11;
import cpw.mods.fml.client.registry.RenderingRegistry;

import crystal.*;
import crystal.client.*;
import crystal.particle.*;
import crystal.particle.*;

public class CrystalProxyClient extends CrystalProxy
{
    public CrystalProxyClient()
    {
        MinecraftForge.EVENT_BUS.register(this);
        RenderingRegistry.registerBlockHandler(new CrystalBlockRender());
    }

    @ForgeSubscribe
    public void postStitch (TextureStitchEvent.Post event)
    {
        Fluid finiteWater = FluidRegistry.getFluid("water.finite");
        finiteWater.setIcons(Block.waterStill.getIcon(0, 0), Block.waterStill.getIcon(2, 0));
    }

    @Override
    public void spawnParticle (String particle, double xPos, double yPos, double zPos, double velX, double velY, double velZ)
    {
        doSpawnParticle(particle, xPos, yPos, zPos, velX, velY, velZ);
    }

    static Minecraft mc = Minecraft.getMinecraft();

    public EntityFX doSpawnParticle (String par1Str, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        if (mc.renderViewEntity != null && mc.effectRenderer != null)
        {
            int i = mc.gameSettings.particleSetting;

            if (i == 1 && mc.theWorld.rand.nextInt(3) == 0)
            {
                i = 2;
            }

            double d6 = mc.renderViewEntity.posX - par2;
            double d7 = mc.renderViewEntity.posY - par4;
            double d8 = mc.renderViewEntity.posZ - par6;
            EntityFX entityfx = null;

            double d9 = 16.0D;

            if (d6 * d6 + d7 * d7 + d8 * d8 > d9 * d9)
            {
                return null;
            }
            else if (i > 1)
            {
                return null;
            }
            else
            {
                if (par1Str.equals("leaf"))
                {
                    entityfx = new LeafFX(mc.theWorld, par2, par4, par6, CrystalContent.essenceCrystal.leafIcon);
                }

                else if (par1Str.equals("essence"))
                {
                    entityfx = new EssenceFX(mc.theWorld, par2, par4, par6, par8, par10, par12);
                    ((EntityFX) entityfx).setParticleTextureIndex(82);
                    ((EntityFX) entityfx).setRBGColorF(1.0F, 1.0F, 1.0F);
                }

                if (entityfx != null)
                {
                    mc.effectRenderer.addEffect((EntityFX) entityfx);
                }

                return (EntityFX) entityfx;
            }

        }
        else
        {
            return null;
        }
    }
    
    public static void renderStandardInvBlock (RenderBlocks renderblocks, Block block, int meta)
    {
        Tessellator tessellator = Tessellator.instance;
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1F, 0.0F);
        renderblocks.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderblocks.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1F);
        renderblocks.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderblocks.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1F, 0.0F, 0.0F);
        renderblocks.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderblocks.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, meta));
        tessellator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }
}
