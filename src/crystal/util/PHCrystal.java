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
        File newFile = new File(location + "/CrystalClimate.cfg");

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
        config.load();
        
        terraformer = config.getBlock("Terraformer", 2851).getInt(2851);
        essenceExtractor = config.getBlock("Essence Extractor", 2852).getInt(2852);
        aggregator = config.getBlock("Aggregator", 2853).getInt(2853);
        ash = config.getBlock("Ash", 2854).getInt(2854);
        ashBlock = config.getBlock("Ash Block", 2855).getInt(2855);
        finiteWater = config.getBlock("Finite Water", 2856).getInt(2856);
        leechedStone = config.getBlock("Leeched Stone", 2857).getInt(2857);
        crystalBlock = config.getBlock("Crystal Block", 2858).getInt(2858);
        sugarBlock = config.getBlock("Sugar Block", 2859).getInt(2859);        
        
        essenceCrystal = config.getItem("Patterns and Misc", "Essence Crystal", 18571).getInt(18571);
        
        config.save();
    }
    

    public static int essenceExtractor;
    public static int terraformer;
    public static int aggregator;
    
    public static int ash;
    public static int ashBlock;
    public static int finiteWater;
    public static int leechedStone;
    public static int crystalBlock;
    public static int sugarBlock;
    
    public static int essenceCrystal;
}
