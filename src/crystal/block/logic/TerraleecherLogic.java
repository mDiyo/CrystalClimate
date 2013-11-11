package crystal.block.logic;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import crystal.CrystalContent;

public class TerraleecherLogic extends TileEntity
{
    Random random = new Random();
    int xLeech = 0;
    int yLeech = 0;
    int zLeech = 0;
    int pass;
    boolean init;

    @Override
    public void updateEntity ()
    {
        if (!init)
        {
            init = true;
            yLeech = yCoord - 1;
            if (yLeech > 64)
                yLeech = 64;
        }
        if (worldObj.isRemote)
        {
            for (int i = 0; i < 16; i++)
                worldObj.spawnParticle("enchantmenttable", xCoord + random.nextFloat() * 25 - 12, yCoord + random.nextFloat() * 10, zCoord + random.nextFloat() * 25 - 12, 0, -1, 0);
        }
        else
        {
            if (pass < 3)
            {
                if (worldObj.getTotalWorldTime() % 5 == 0)
                //for (int i = 0; i < 5; i++)
                {
                    eatBlock(xCoord - 12 + xLeech, zCoord - 12 + zLeech);
                    incrementPosition();
                }
            }
        }
    }

    void eatBlock (int x, int z)
    {
        Block block = Block.blocksList[worldObj.getBlockId(x, yLeech, z)];
        if (pass == 0)
        {
            if (block != null && block != Block.stone && block != Block.netherrack && block != CrystalContent.leechedStone && block.getBlockHardness(worldObj, x, yLeech, z) >= 0
                    && block.blockMaterial == Material.rock)
            {
                int meta = worldObj.getBlockMetadata(x, yLeech, z);
                if (placeBlockAbove(x, yCoord - 3, z, block, meta))
                    worldObj.setBlock(x, yLeech, z, CrystalContent.leechedStone.blockID);
            }
        }
        else if (pass == 1)
        {
            if (block != null && block != Block.stone && block != Block.netherrack && block.getBlockHardness(worldObj, x, yLeech, z) >= 0
                    && (block.blockMaterial == Material.rock || block.blockMaterial == Material.ground || block.blockMaterial == Material.sand))
            {
                int meta = worldObj.getBlockMetadata(x, yLeech, z);
                if (placeBlockAbove(x, yCoord - 3, z, block, meta))
                    worldObj.setBlockToAir(x, yLeech, z);
            }
        }
        else if (pass == 2)
        {
            if (block != null && block.getBlockHardness(worldObj, x, yLeech, z) >= 0
                    && (block.blockMaterial == Material.rock || block.blockMaterial == Material.ground || block.blockMaterial == Material.sand))
            {
                eatBlock(x, yLeech, z);
            }
        }
    }

    private boolean placeBlockAbove (int x, int y, int z, Block block, int meta)
    {
        boolean placed = false;
        do
        {
            y++;
            if (y > 255)
                break;

            Block check = Block.blocksList[worldObj.getBlockId(x, y, z)];
            if (check == null || check.isBlockReplaceable(worldObj, x, y, z))
            {
                if (worldObj.setBlock(x, y, z, block.blockID, meta, 3))
                    placed = true;
            }
        } while (!placed);
        /*if (placed)
            System.out.println("Placed block at " + new CoordTuple(x, y, z));*/

        return placed;
    }

    void eatBlock (int x, int y, int z) //TODO: Make this better
    {
        worldObj.setBlockToAir(x, y, z);
    }

    void incrementPosition ()
    {
        xLeech++;
        if (xLeech >= 25)
        {
            xLeech = 0;
            zLeech++;
            //System.out.println("Now checking z " + zLeech);
            if (zLeech >= 25)
            {
                zLeech = 0;
                yLeech--;
                System.out.println("Now checking layer " + yLeech);
                if (yLeech <= 0)
                {
                    xLeech = 0;
                    yLeech = yCoord - 1;
                    zLeech = 0;
                    pass++;
                }
            }
        }
    }
}
