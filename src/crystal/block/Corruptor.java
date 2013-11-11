package crystal.block;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import crystal.CrystalContent;

public class Corruptor extends Block
{
    public Corruptor(int id)
    {
        super(id, Material.rock);
        this.setTickRandomly(true);
        this.setHardness(1.0F);
    }

    public int tickRate()
    {
        return 5;
    }

    public int damageDropped(int meta)
    {
        return meta % 8;
    }
}
