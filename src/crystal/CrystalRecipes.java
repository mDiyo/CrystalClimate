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
    }
}
