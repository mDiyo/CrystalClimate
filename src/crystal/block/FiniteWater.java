package crystal.block;

import crystal.CrystalClimate;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;

public class FiniteWater extends BlockFluidFinite
{
    public FiniteWater(int id, Fluid fluid, Material material)
    {
        super(id, fluid, material);
        this.setCreativeTab(CrystalClimate.tab);
    }

    public void registerIcons (IconRegister par1IconRegister)
    {
    }

    public Icon getIcon (int side, int meta)
    {
        return Block.waterStill.getIcon(side, meta);
    }
}
