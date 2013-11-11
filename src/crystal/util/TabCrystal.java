package crystal.util;

import crystal.CrystalContent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabCrystal extends CreativeTabs
{
    public TabCrystal(String label)
    {
        super(label);
    }

    public ItemStack getIconItemStack ()
    {
        return CrystalContent.essenceStack;
    }
}