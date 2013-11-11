package crystal;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import crystal.util.*;

@Mod(modid = "CrystalClimate", name = "CrystalClimate", version = "Aron")
@NetworkMod(serverSideRequired = false, clientSideRequired = true)
public class CrystalClimate
{
    @Instance("CrystalClimate")
    public static CrystalClimate instance;
    public static TabCrystal tab;
    @SidedProxy(clientSide = "crystal.util.CrystalProxyClient", serverSide = "crystal.util.CrystalProxy")
    public static CrystalProxy proxy;

    @EventHandler
    public void preInit (FMLPreInitializationEvent event)
    {
        PHCrystal.initProps(event.getModConfigurationDirectory());
        tab = new TabCrystal("crystalclimate");
        CrystalContent.CreateContent();
        CrystalRecipes.CreateRecipes();
    }
}
