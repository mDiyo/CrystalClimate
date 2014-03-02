package crystal.block;

import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import crystal.CrystalClimate;
import crystal.block.logic.*;

public class Terraformer extends BlockContainer
{
    public Terraformer(int id)
    {
        super(id, Material.iron);
        this.setCreativeTab(CrystalClimate.tab);
        setStepSound(soundMetalFootstep);
    }
    
    @Override
    public TileEntity createTileEntity (World world, int metadata)
    {
        switch (metadata)
        {
        case 3: return new TerraleecherLogic();
        case 4: return new TerragrowerLogic();
        }
        return new TerraformerLogic();
    }

    @Override
    public TileEntity createNewTileEntity (World world)
    {
        return new TerraformerLogic();
    }

    @Override
    public int damageDropped (int meta)
    {
        return meta;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks (int id, CreativeTabs tab, List list)
    {
        for (int iter = 0; iter < textureNames.length - 1; iter++)
        {
            list.add(new ItemStack(id, 1, iter));
        }
    }

    /* Rendering */

    @SideOnly(Side.CLIENT)
    public Icon[] icons;

    static String[] textureNames = { "crystal_machine_top", "terrafreezer", "terrafumer", "terrawaver", "terraleecher", "terragrower", "terranether", "terralighter", "terracrystal" };

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons (IconRegister iconRegister)
    {
        this.icons = new Icon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("crystal:" + textureNames[i]);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon (int side, int meta)
    {
        if ((side == 0 || side == 1) && (meta != 0 && meta != 5))
            return icons[0];
        return icons[meta + 1];
    }
}
