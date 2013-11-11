package crystal.util;

import java.io.File;
import java.io.IOException;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLCommonHandler;

public class PHCrystal
{
    public static void initProps (File location)
    {
        /* Here we will set up the config file for the mod 
         * First: Create a folder inside the config folder
         * Second: Create the actual config file
         * Note: Configs are a pain, but absolutely necessary for every mod.
         */
        File newFile = new File(location + "/CrystalClimate.txt");

        try
        {
            newFile.createNewFile();
        }
        catch (IOException e)
        {
            FMLCommonHandler.instance().getFMLLogger().severe("Could not create configuration file for Crystal Climate. Reason:");
            FMLCommonHandler.instance().getFMLLogger().severe(e.getLocalizedMessage());
        }

        Configuration config = new Configuration(newFile);
        
        terraformer = config.getBlock("Terraformer", 2851).getInt(2851);
        essenceExtractor = config.getBlock("Essence Extractor", 2852).getInt(2852);
        ash = config.getBlock("Ash", 2853).getInt(2853);
        finiteWater = config.getBlock("Finite Water", 2854).getInt(2854);
        leechedStone = config.getBlock("Leeched Stone", 2855).getInt(2855);
        
        essenceCrystal = config.getItem("Patterns and Misc", "Essence Crystal", 18571).getInt(18571);
    }
    

    public static int essenceExtractor;
    public static int terraformer;
    public static int ash;
    public static int finiteWater;
    public static int leechedStone;
    
    public static int essenceCrystal;
}
