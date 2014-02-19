package crystal.block;

import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import crystal.CrystalClimate;
import crystal.block.logic.CrystalLogic;
import crystal.block.logic.RedstoneAggregator;

public class Aggregator extends BlockContainer
{
    public String[] textureNames = { "redstone" };
    public Icon[] icons;

    public Aggregator(int id)
    {
        super(id, Material.iron);
        this.setCreativeTab(CrystalClimate.tab);
    }

    @Override
    public int damageDropped (int meta)
    {
        return meta;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons (IconRegister iconRegister)
    {
        this.icons = new Icon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("crystal:aggregator_" + textureNames[i]);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon (int side, int meta)
    {
        return icons[0];
    }

    @Override
    public void getSubBlocks (int id, CreativeTabs tab, List list)
    {
        list.add(new ItemStack(id, 1, 0));
    }

    @Override
    public TileEntity createTileEntity (World world, int metadata)
    {
        return new RedstoneAggregator();
    }

    @Override
    public TileEntity createNewTileEntity (World world)
    {
        return null;
    }
    
    @Override
    public boolean onBlockActivated (World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if (player.isSneaking())
            return false;
        if (!player.worldObj.isRemote)
        {
            RedstoneAggregator logic = (RedstoneAggregator) world.getBlockTileEntity(x, y, z);
            float value = logic.getCrystalValue() / 10f;
            
            player.addChatMessage(StatCollector.translateToLocal("tooltip.crystalvalue")+": "+value);
        }
        return true;
    }
}
