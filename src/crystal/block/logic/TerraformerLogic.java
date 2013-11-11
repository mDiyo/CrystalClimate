package crystal.block.logic;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import crystal.CrystalContent;

public class TerraformerLogic extends TileEntity
{
    Random random = new Random();
    boolean init;
    int type;

    @Override
    public void updateEntity ()
    {
        if (!init)
        {
            init = true;
            type = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        }
        if (worldObj.isRemote)
        {
            switch (type)
            {
            case 0: //Freezer
                for (int i = 0; i < 32; i++)
                    worldObj.spawnParticle("snowshovel", xCoord + random.nextFloat() * 25 - 12, yCoord + random.nextFloat() * 10, zCoord + random.nextFloat() * 25 - 12, 0, 0, 0);
                break;
            case 1: //Fumer
                for (int i = 0; i < 5; i++)
                    worldObj.spawnParticle("lava", xCoord + random.nextFloat() * 25 - 12, yCoord + random.nextFloat() * 10, zCoord + random.nextFloat() * 25 - 12, 0, 0, 0);
                for (int i = 0; i < 3; i++)
                    worldObj.spawnParticle("explode", xCoord + random.nextFloat() * 25 - 12, yCoord + random.nextFloat() * 10, zCoord + random.nextFloat() * 25 - 12, 0, 0, 0);
                for (int i = 0; i < 5; i++)
                    worldObj.spawnParticle("smoke", xCoord + random.nextFloat() * 25 - 12, yCoord + random.nextFloat() * 10, zCoord + random.nextFloat() * 25 - 12, 0, 0, 0);
                break;
            case 2: //Waver
                for (int i = 0; i < 50; i++)
                    worldObj.spawnParticle("splash", xCoord + random.nextFloat() * 25 - 12, yCoord + random.nextFloat() * 10, zCoord + random.nextFloat() * 25 - 12, 0, 0, 0);
                break;
            case 3: //Leecher
                for (int i = 0; i < 16; i++)
                    worldObj.spawnParticle("enchantmenttable", xCoord + random.nextFloat() * 25 - 12, yCoord + random.nextFloat() * 10, zCoord + random.nextFloat() * 25 - 12, 0, -1, 0);
                break;
            case 4: //Grower
                break;
            case 5: //Nether
                for (int i = 0; i < 40; i++)
                    worldObj.spawnParticle("depthsuspend", xCoord + random.nextFloat() * 25 - 12, yCoord + random.nextFloat() * 10, zCoord + random.nextFloat() * 25 - 12, 0, 0, 0);
                for (int i = 0; i < 3; i++)
                    worldObj.spawnParticle("largesmoke", xCoord + random.nextFloat() * 25 - 12, yCoord + random.nextFloat() * 10, zCoord + random.nextFloat() * 25 - 12, 0, 0, 0);
                break;
            case 6: //Lighter
                break;
            case 7: //Crystal
                for (int i = 0; i < 8; i++)
                    worldObj.spawnParticle("fireworksSpark", xCoord + random.nextFloat() * 25 - 12, yCoord + random.nextFloat() * 10, zCoord + random.nextFloat() * 25 - 12, 0, 0, 0);
                break;
            case 8: //Ender
                for (int i = 0; i < 16; i++)
                    worldObj.spawnParticle("portal", xCoord + random.nextFloat() * 25 - 12, yCoord + random.nextFloat() * 10, zCoord + random.nextFloat() * 25 - 12, 0, 0, 0);
                for (int i = 0; i < 6; i++)
                    worldObj.spawnParticle("witchMagic", xCoord + random.nextFloat() * 25 - 12, yCoord + random.nextFloat() * 10, zCoord + random.nextFloat() * 25 - 12, 0, 0, 0);
                break;
            case 9: //Void
                for (int i = 0; i < 16; i++)
                    worldObj.spawnParticle("portal", xCoord + random.nextFloat() * 25 - 12, yCoord + random.nextFloat() * 10, zCoord + random.nextFloat() * 25 - 12, 0, 0, 0);
                for (int i = 0; i < 8; i++)
                    worldObj.spawnParticle("dripWater", xCoord + random.nextFloat() * 25 - 12, yCoord + random.nextFloat() * 10, zCoord + random.nextFloat() * 25 - 12, 0, 0, 0);
                for (int i = 0; i < 8; i++)
                    worldObj.spawnParticle("dripLava", xCoord + random.nextFloat() * 25 - 12, yCoord + random.nextFloat() * 10, zCoord + random.nextFloat() * 25 - 12, 0, 0, 0);
                break;
            case 10: //Desert
                break;
            }

            /* Good combos:
             * smoke + flame, 20+5
             * portal + witchMagic, 16+6
             * mobSpellAmbient + spell + mobSpell, 20+3+3
             * lava + explode + smoke, 10+6+10
             * happyVillager + deathsuspend + enchantmenttable, 16+64+20
             * 
             * Good values: 
             * fireworksSpark, 16
             * depthsuspend, 40
             * enchantmenttable, 16, x5
             * explode, 110
             * splash, 50
             * snowshovel, 32
             * heart, 16
             */
        }
        else
        {
            if (worldObj.getTotalWorldTime() % 5 == 0)
            //for (int i = 0; i < 2; i++)
            {
                switch (type)
                {
                case 0:
                    snow();
                    break;
                case 1:
                    fume();
                    break;
                case 2:
                    wave();
                    break;
                case 5:
                    corrupt();
                    break;
                }
            }
        }
    }

    void snow ()
    {
        int x = xCoord + random.nextInt(25) - 12, y = yCoord + random.nextInt(6) - 1, z = zCoord + random.nextInt(25) - 12;
        Block block = Block.blocksList[worldObj.getBlockId(x, y, z)];
        if (block == Block.waterStill || block == Block.waterMoving || block == CrystalContent.finiteWater)
        {
            worldObj.setBlock(x, y, z, Block.ice.blockID);
        }
        else if (block == Block.snow)
        {
            int meta = worldObj.getBlockMetadata(x, y, z);
            if (meta < 6)
                worldObj.setBlockMetadataWithNotify(x, y, z, meta + 1, 3);
            else if (meta == 6)
                worldObj.setBlock(x, y, z, Block.blockSnow.blockID);

        }
        else if (block == null || block.isBlockReplaceable(worldObj, x, y, z))
        {
            if (worldObj.doesBlockHaveSolidTopSurface(x, y - 1, z))
            {
                worldObj.setBlock(x, y, z, Block.snow.blockID);
            }
        }
    }

    void fume ()
    {
        int x = xCoord + random.nextInt(25) - 12, y = yCoord + random.nextInt(6) - 1, z = zCoord + random.nextInt(25) - 12;
        Block block = Block.blocksList[worldObj.getBlockId(x, y, z)];
        if (block == Block.waterStill || block == Block.waterMoving)
        {
            worldObj.setBlock(x, y, z, Block.lavaStill.blockID);
        }
        else if (block == CrystalContent.ash)
        {
            int meta = worldObj.getBlockMetadata(x, y, z);
            if (meta < 7)
                worldObj.setBlockMetadataWithNotify(x, y, z, meta + 1, 3);
        }
        else if (block == null || block.isBlockReplaceable(worldObj, x, y, z))
        {
            if (worldObj.doesBlockHaveSolidTopSurface(x, y - 1, z))
            {
                if (random.nextInt(10) == 0)
                    worldObj.setBlock(x, y, z, Block.lavaStill.blockID);
                else
                    worldObj.setBlock(x, y, z, CrystalContent.ash.blockID);
            }
        }
    }

    void wave ()
    {
        int x = xCoord + random.nextInt(5) - 2, y = yCoord, z = zCoord + random.nextInt(5) - 2;

        Block block = Block.blocksList[worldObj.getBlockId(x, y, z)];
        if (block == CrystalContent.finiteWater)
        {
            int meta = worldObj.getBlockMetadata(x, y, z);
            if (meta < 7)
                worldObj.setBlockMetadataWithNotify(x, y, z, meta + 1, 3);
        }
        else if (block == null || block.isBlockReplaceable(worldObj, x, y, z))
        {
            worldObj.setBlock(x, y, z, CrystalContent.finiteWater.blockID);
        }
    }

    void corrupt ()
    {
        int x = xCoord + random.nextInt(25) - 12, y = yCoord + random.nextInt(11) - 5, z = zCoord + random.nextInt(25) - 12;
        Block block = Block.blocksList[worldObj.getBlockId(x, y, z)];
        if (block != null)
        {
            if (block == Block.stone)
                worldObj.setBlock(x, y, z, Block.netherrack.blockID);
            else if (block == Block.obsidian)
                worldObj.setBlock(x, y, z, Block.lavaStill.blockID);
            else if (block == Block.tallGrass)
                worldObj.setBlock(x, y, z, Block.deadBush.blockID);
            else
            {
                Material material = block.blockMaterial;
                if (material == Material.water)
                    worldObj.setBlock(x, y, z, Block.lavaStill.blockID);
                else if (material == Material.ground)
                    worldObj.setBlock(x, y, z, Block.slowSand.blockID);
                else if (material == Material.grass)
                {
                    Block grass = Block.blocksList[worldObj.getBlockId(x, y + 1, z)];
                    if (block == Block.tallGrass)
                        worldObj.setBlock(x, y, z, Block.deadBush.blockID);
                    
                    if (random.nextInt(3) == 0)
                        worldObj.setBlock(x, y, z, Block.netherrack.blockID);
                    else
                        worldObj.setBlock(x, y, z, Block.slowSand.blockID);
                }
                else if (material == Material.sand && block != Block.slowSand)
                    worldObj.setBlock(x, y, z, Block.gravel.blockID);
                else if (material == Material.plants)
                    worldObj.setBlock(x, y, z, Block.web.blockID);
            }
        }
    }
}
