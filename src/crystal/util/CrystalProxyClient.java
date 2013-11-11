package crystal.util;

import net.minecraft.block.Block;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class CrystalProxyClient extends CrystalProxy
{
    public CrystalProxyClient()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @ForgeSubscribe
    public void postStitch (TextureStitchEvent.Post event)
    {
        Fluid finiteWater = FluidRegistry.getFluid("water.finite");
        finiteWater.setIcons(Block.waterStill.getIcon(0, 0), Block.waterStill.getIcon(2, 0));
    }
}
