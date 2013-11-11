package crystal;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import cpw.mods.fml.common.registry.GameRegistry;
import crystal.block.*;
import crystal.block.logic.*;
import crystal.item.*;
import crystal.util.*;

public class CrystalContent
{
    public static Block essenceExtractor;
    public static Block terraformer;
    
    public static Block ash;
    public static Block finiteWater;
    public static Block leechedStone;
    
    public static Fluid finiteWaterFluid;
    
    public static Item essenceCrystal;
    public static ItemStack essenceStack;
    
    public static void CreateContent()
    {
        //Blocks
        essenceExtractor = new EssenceExtractor(PHCrystal.essenceExtractor).setHardness(12f).setUnlocalizedName("extractor.essence");
        GameRegistry.registerBlock(essenceExtractor, "extractor.essence");
        GameRegistry.registerTileEntity(EssenceExtractorLogic.class, "extractor.essence");

        terraformer = new Terraformer(PHCrystal.terraformer).setHardness(50f).setUnlocalizedName("terraformer");
        GameRegistry.registerBlock(terraformer, TerraformerItem.class, "terraformer");
        GameRegistry.registerTileEntity(TerraformerLogic.class, "Terraformer");
        GameRegistry.registerTileEntity(TerraleecherLogic.class, "Terraleecher");
        
        ash = new Ash(PHCrystal.ash).setUnlocalizedName("ash").setTextureName("crystal:ash");
        GameRegistry.registerBlock(ash, "ash");
        
        finiteWaterFluid = new Fluid("water.finite");
        if (!FluidRegistry.registerFluid(finiteWaterFluid))
            finiteWaterFluid = FluidRegistry.getFluid("water.finite");
        finiteWater = new FiniteWater(PHCrystal.finiteWater, finiteWaterFluid, Material.water).setUnlocalizedName(Block.waterStill.getUnlocalizedName()).setTextureName("water_still");
        finiteWaterFluid.setBlockID(finiteWater).setUnlocalizedName(Block.waterStill.getUnlocalizedName());
        FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(finiteWaterFluid, 1000), new ItemStack(Item.bucketWater), new ItemStack(Item.bucketEmpty)));
        GameRegistry.registerBlock(finiteWater, "water.finite");

        leechedStone = new Block(PHCrystal.leechedStone, Material.rock).setHardness(3F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("stone.leeched")
                .setCreativeTab(CrystalClimate.tab).setTextureName("crystal:leechedstone");
        //Items
        essenceCrystal = new EssenceCrystal(PHCrystal.essenceCrystal).setUnlocalizedName("crystal.essence");
        essenceStack = new ItemStack(essenceCrystal);
    }
}
