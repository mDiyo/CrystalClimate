package crystal;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class CrystalRecipes
{
    public static void CreateRecipes()
    {
        GameRegistry.addRecipe(new ItemStack(CrystalContent.essenceExtractor, 1, 0), " b ", "eme", "mmm", 'b', Item.book, 'e', Item.emerald, 'm', Block.whiteStone);
        GameRegistry.addRecipe(new ItemStack(CrystalContent.sugarBlock, 1, 7), "###", "###", "###", '#', Item.sugar);
        GameRegistry.addRecipe(new ItemStack(CrystalContent.aggregator, 1, 0), "#d#", "#r#", "#e#", '#', Item.netherQuartz, 'd', Item.diamond, 'e', Item.enderPearl, 'r', Block.blockRedstone);
        GameRegistry.addRecipe(new ItemStack(CrystalContent.aggregator, 1, 0), "#e#", "#r#", "#d#", '#', Item.netherQuartz, 'd', Item.diamond, 'e', Item.enderPearl, 'r', Block.blockRedstone);
    }
}
