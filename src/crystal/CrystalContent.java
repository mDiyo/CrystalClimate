package crystal;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlockWithMetadata;
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
    public static Block aggregator;
    
    public static Block ash;
    public static Block ashBlock;
    public static Block finiteWater;
    public static Block leechedStone;
    public static Block crystalBlock;
    public static Block sugarBlock;
    
    public static Fluid finiteWaterFluid;
    
    public static EssenceCrystal essenceCrystal;
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
        GameRegistry.registerTileEntity(TerragrowerLogic.class, "Terragrower");
        
        aggregator = new Aggregator(PHCrystal.aggregator).setHardness(10f).setUnlocalizedName("aggregator.redstone");
        GameRegistry.registerBlock(aggregator, AggregatorItem.class, "aggregator");
        GameRegistry.registerTileEntity(RedstoneAggregator.class, "aggregator.redstone");
        
        crystalBlock = new CrystalBlock(PHCrystal.crystalBlock).setHardness(1.0f).setUnlocalizedName("crystal");
        GameRegistry.registerBlock(crystalBlock, CrystalBlockItem.class, "crystalblock");
        GameRegistry.registerTileEntity(CrystalLogic.class, "crystallogic");
        
        ash = new Ash(PHCrystal.ash).setHardness(0.1F).setStepSound(Block.soundSandFootstep).setUnlocalizedName("ash").setTextureName("crystal:ash");
        GameRegistry.registerBlock(ash, "ash");
        ashBlock = new Block(PHCrystal.ashBlock, Material.sand).setHardness(0.2F).setStepSound(Block.soundSandFootstep).setUnlocalizedName("ash")
                .setCreativeTab(CrystalClimate.tab).setTextureName("crystal:ash");
        GameRegistry.registerBlock(ashBlock, "ashBlock");
        
        finiteWaterFluid = new Fluid("water.finite");
        if (!FluidRegistry.registerFluid(finiteWaterFluid))
            finiteWaterFluid = FluidRegistry.getFluid("water.finite");
        finiteWater = new FiniteWater(PHCrystal.finiteWater, finiteWaterFluid, Material.water).setUnlocalizedName(Block.waterStill.getUnlocalizedName()).setTextureName("water_still");
        finiteWaterFluid.setBlockID(finiteWater).setUnlocalizedName(Block.waterStill.getUnlocalizedName());
        FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(finiteWaterFluid, 1000), new ItemStack(Item.bucketWater), new ItemStack(Item.bucketEmpty)));
        GameRegistry.registerBlock(finiteWater, "water.finite");

        leechedStone = new Block(PHCrystal.leechedStone, Material.rock).setHardness(3F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("stone.leeched")
                .setCreativeTab(CrystalClimate.tab).setTextureName("crystal:leechedstone");
        GameRegistry.registerBlock(leechedStone, "leechedstone");
        
        sugarBlock = new SugarBlock(PHCrystal.sugarBlock).setHardness(0.3F).setUnlocalizedName("sugar");
        GameRegistry.registerBlock(sugarBlock, ItemBlockWithMetadata.class, "sugarblock");
        
        //Items
        essenceCrystal = (EssenceCrystal) new EssenceCrystal(PHCrystal.essenceCrystal).setUnlocalizedName("crystal.essence");
        essenceStack = new ItemStack(essenceCrystal);
    }
}
