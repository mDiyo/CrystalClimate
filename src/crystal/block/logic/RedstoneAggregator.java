package crystal.block.logic;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.PacketDispatcher;
import crystal.CrystalContent;

public class RedstoneAggregator extends TileEntity
{
    short currentTime;
    public boolean active;
    int crystalValue;
    boolean blocked;
    public short sugar;
    private static Random random = new Random();
    int[] nom;

    @Override
    public void updateEntity ()
    {
        if (!worldObj.isRemote)
        {
            if (!blocked && crystalValue < 528)
            {
                if (sugar > 0)
                {
                    currentTime++;
                    sugar--;
                    if (currentTime >= 240)
                    {
                        currentTime = 0;
                        crystalValue++;
                        growCrystal();
                    }
                }

                if (sugar == 0 && this.worldObj.getTotalWorldTime() % 80L == 0)
                {
                    scanForSugar(true);
                }
            }
        }
        else
        {
            if (random.nextInt(5) == 0)
                worldObj.spawnParticle("mobSpellAmbient", xCoord + random.nextFloat() * 3 - 1, yCoord + random.nextFloat() * 3 - 1, zCoord + random.nextFloat() * 3 - 1, 0, 0, 0);
        }
    }

    void scanForSugar (boolean eat)
    {
        if (nom != null)
        {
            eatSugar();
        }
        else
        {
            for (int i = 1; i <= 4; i++)
            {
                if (findSugarInLayer(i))
                {
                    if (eat)
                    eatSugar();
                    return;
                }
            }
        }
    }

    void eatSugar ()
    {
        if (Block.blocksList[worldObj.getBlockId(nom[0], nom[1], nom[2])] == CrystalContent.sugarBlock)
        {
            int meta = worldObj.getBlockMetadata(nom[0], nom[1], nom[2]);
            if (meta == 0)
            {
                worldObj.setBlockToAir(nom[0], nom[1], nom[2]);
                nom = null;
                scanForSugar(false);
            }
            else
                worldObj.setBlockMetadataWithNotify(nom[0], nom[1], nom[2], meta - 1, 3);

            sugar += 300;

            //Server-only. this won't do a thing
            //for (int i = 0; i < 16; i++)
                //worldObj.spawnParticle("smoke", nom[0] + random.nextFloat(), nom[1] + 1.0f + random.nextFloat() * 0.5f, nom[2] + random.nextFloat(), 0, 0, 0);
            return;
        }
    }
    
    void updateNearbyClients()
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
        DataOutputStream outputStream = new DataOutputStream(bos);
        try
        {
            outputStream.writeByte(1);
            outputStream.writeInt(worldObj.provider.dimensionId);
            outputStream.writeInt(nom[0]);
            outputStream.writeInt(nom[1]);
            outputStream.writeInt(nom[2]);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = "CrystalClimate";
        packet.data = bos.toByteArray();
        packet.length = bos.size();

        PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 16, worldObj.provider.dimensionId, packet);
    }

    boolean findSugarInLayer (int range)
    {
        for (int x = -range; x <= range; x++)
        {
            for (int z = -range; z <= range; z++)
            {
                if (x == 0 && z == 0)
                    continue;

                if (Block.blocksList[worldObj.getBlockId(xCoord + x, yCoord - range, zCoord + z)] == CrystalContent.sugarBlock)
                {
                    nom = new int[] { xCoord + x, yCoord - range, zCoord + z };
                    return true;
                }
            }
        }
        return false;
    }

    int heightOfCrystal ()
    {
        if (crystalValue > 440)
            return 4;
        if (crystalValue > 224)
            return 3;
        if (crystalValue > 80)
            return 2;

        return 1;
    }

    void growCrystal ()
    {
        if (crystalValue < 10)
        {
            Block block = Block.blocksList[worldObj.getBlockId(xCoord, yCoord + 1, zCoord)];
            if (block == null || block.isAirBlock(worldObj, xCoord, yCoord + 1, zCoord) || block == CrystalContent.crystalBlock)
            {
                worldObj.setBlock(xCoord, yCoord + 1, zCoord, CrystalContent.crystalBlock.blockID, 0, 3);
            }
            else
            {
                crystalValue--;
                blocked = true;
            }
        }
        else if (crystalValue == 10)
        {
            Block block = Block.blocksList[worldObj.getBlockId(xCoord, yCoord + 1, zCoord)];
            if (validBlock(block))
            {
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord + 1, zCoord, 1, 3);
                //spawnNegativeAir(1, 1);

                int value = 10;
                //WorldTracker.updateTheft(worldObj.provider.dimensionId, xCoord, zCoord, value, CrystalType.Light);
                //WorldTracker.updateCharge(worldObj.provider.dimensionId, xCoord, zCoord, -value, CrystalType.Light);
                CrystalLogic crystal = (CrystalLogic) worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord);
                crystal.setCrystalValue(10);
            }
        }
        else if (crystalValue == 24)
        {
            Block block = Block.blocksList[worldObj.getBlockId(xCoord, yCoord + 1, zCoord)];
            if (validBlock(block))
            {
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord + 1, zCoord, 2, 3);
                //spawnNegativeAir(2, 1);

                int value = 16;
                //WorldTracker.updateTheft(worldObj.provider.dimensionId, xCoord, zCoord, value, CrystalType.Light);
                //WorldTracker.updateCharge(worldObj.provider.dimensionId, xCoord, zCoord, -value, CrystalType.Light);
                CrystalLogic crystal = (CrystalLogic) worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord);
                crystal.setCrystalValue(24);
            }
        }
        else if (crystalValue == 48)
        {
            Block block = Block.blocksList[worldObj.getBlockId(xCoord, yCoord + 1, zCoord)];
            if (validBlock(block))
            {
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord + 1, zCoord, 3, 3);
                //spawnNegativeAir(3, 1);

                int value = 24;
                //WorldTracker.updateTheft(worldObj.provider.dimensionId, xCoord, zCoord, value, CrystalType.Light);
                //WorldTracker.updateCharge(worldObj.provider.dimensionId, xCoord, zCoord, -value, CrystalType.Light);
                CrystalLogic crystal = (CrystalLogic) worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord);
                crystal.setCrystalValue(48);
            }
        }
        else if (crystalValue == 80) //Transition
        {
            Block upperBlock = Block.blocksList[worldObj.getBlockId(xCoord, yCoord + 2, zCoord)];
            if (upperBlock == null || upperBlock.isAirBlock(worldObj, xCoord, yCoord + 2, zCoord))
            {
                Block block = Block.blocksList[worldObj.getBlockId(xCoord, yCoord + 1, zCoord)];
                if (validBlock(block))
                {
                    worldObj.setBlockMetadataWithNotify(xCoord, yCoord + 1, zCoord, 4, 3);
                    worldObj.setBlock(xCoord, yCoord + 2, zCoord, CrystalContent.crystalBlock.blockID, 6, 3);
                    //spawnNegativeAir(5, 1);

                    int value = 32;
                    //WorldTracker.updateTheft(worldObj.provider.dimensionId, xCoord, zCoord, value, CrystalType.Light);
                    //WorldTracker.updateCharge(worldObj.provider.dimensionId, xCoord, zCoord, -value, CrystalType.Light);
                    CrystalLogic crystal = (CrystalLogic) worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord);
                    crystal.setCrystalValue(80);
                }
            }
            else
            {
                crystalValue--;
                blocked = true;
            }
        }
        else if (crystalValue == 120)
        {
            Block block = Block.blocksList[worldObj.getBlockId(xCoord, yCoord + 1, zCoord)];
            Block upperBlock = Block.blocksList[worldObj.getBlockId(xCoord, yCoord + 2, zCoord)];
            if (validBlock(block) && validBlock(upperBlock))
            {
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord + 1, zCoord, 5, 3);
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord + 2, zCoord, 7, 3);
                //spawnNegativeAir(6, 1);

                int value = 40;
                //WorldTracker.updateTheft(worldObj.provider.dimensionId, xCoord, zCoord, value, CrystalType.Light);
                //WorldTracker.updateCharge(worldObj.provider.dimensionId, xCoord, zCoord, -value, CrystalType.Light);
                CrystalLogic crystal = (CrystalLogic) worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord);
                crystal.setCrystalValue(120);
            }
        }
        else if (crystalValue == 168)
        {
            Block block = Block.blocksList[worldObj.getBlockId(xCoord, yCoord + 2, zCoord)];
            if (validBlock(block))
            {
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord + 2, zCoord, 8, 3);
                //spawnNegativeAir(7, 1);

                int value = 48;
                //WorldTracker.updateTheft(worldObj.provider.dimensionId, xCoord, zCoord, value, CrystalType.Light);
                //WorldTracker.updateCharge(worldObj.provider.dimensionId, xCoord, zCoord, -value, CrystalType.Light);
                CrystalLogic crystal = (CrystalLogic) worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord);
                crystal.setCrystalValue(168);
            }
        }
        else if (crystalValue == 224) //Transition
        {
            Block upperBlock = Block.blocksList[worldObj.getBlockId(xCoord, yCoord + 3, zCoord)];
            if (upperBlock == null || upperBlock.isAirBlock(worldObj, xCoord, yCoord + 3, zCoord))
            {
                Block block = Block.blocksList[worldObj.getBlockId(xCoord, yCoord + 2, zCoord)];
                if (validBlock(block))
                {
                    worldObj.setBlockMetadataWithNotify(xCoord, yCoord + 2, zCoord, 9, 3);
                    worldObj.setBlock(xCoord, yCoord + 3, zCoord, CrystalContent.crystalBlock.blockID, 10, 3);
                    //spawnNegativeAir(9, 1);

                    int value = 56;
                    //WorldTracker.updateTheft(worldObj.provider.dimensionId, xCoord, zCoord, value, CrystalType.Light);
                    //WorldTracker.updateCharge(worldObj.provider.dimensionId, xCoord, zCoord, -value, CrystalType.Light);
                    CrystalLogic crystal = (CrystalLogic) worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord);
                    crystal.setCrystalValue(224);
                }
            }
            else
            {
                crystalValue--;
                blocked = true;
            }
        }
        else if (crystalValue == 288)
        {
            Block block = Block.blocksList[worldObj.getBlockId(xCoord, yCoord + 3, zCoord)];
            if (validBlock(block))
            {
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord + 3, zCoord, 11, 3);
                //spawnNegativeAir(10, 1);

                int value = 64;
                //WorldTracker.updateTheft(worldObj.provider.dimensionId, xCoord, zCoord, value, CrystalType.Light);
                //WorldTracker.updateCharge(worldObj.provider.dimensionId, xCoord, zCoord, -value, CrystalType.Light);
                CrystalLogic crystal = (CrystalLogic) worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord);
                crystal.setCrystalValue(288);
            }
        }
        else if (crystalValue == 360)
        {
            Block block = Block.blocksList[worldObj.getBlockId(xCoord, yCoord + 3, zCoord)];
            if (validBlock(block))
            {
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord + 3, zCoord, 12, 3);
                //spawnNegativeAir(11, 1);

                int value = 72;
                //WorldTracker.updateTheft(worldObj.provider.dimensionId, xCoord, zCoord, value, CrystalType.Light);
                //WorldTracker.updateCharge(worldObj.provider.dimensionId, xCoord, zCoord, -value, CrystalType.Light);
                CrystalLogic crystal = (CrystalLogic) worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord);
                crystal.setCrystalValue(360);
            }
        }
        else if (crystalValue == 440) //Transition
        {
            Block upperBlock = Block.blocksList[worldObj.getBlockId(xCoord, yCoord + 4, zCoord)];
            if (upperBlock == null || upperBlock.isAirBlock(worldObj, xCoord, yCoord + 4, zCoord))
            {
                Block block = Block.blocksList[worldObj.getBlockId(xCoord, yCoord + 3, zCoord)];
                if (validBlock(block))
                {
                    worldObj.setBlockMetadataWithNotify(xCoord, yCoord + 3, zCoord, 13, 3);
                    worldObj.setBlock(xCoord, yCoord + 4, zCoord, CrystalContent.crystalBlock.blockID, 14, 3);
                    //spawnNegativeAir(13, 1);

                    int value = 80;
                    //WorldTracker.updateTheft(worldObj.provider.dimensionId, xCoord, zCoord, value, CrystalType.Light);
                    //WorldTracker.updateCharge(worldObj.provider.dimensionId, xCoord, zCoord, -value, CrystalType.Light);
                    CrystalLogic crystal = (CrystalLogic) worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord);
                    crystal.setCrystalValue(440);
                }
            }
            else
            {
                crystalValue--;
                blocked = true;
            }
        }
        else if (crystalValue == 528)
        {
            Block block = Block.blocksList[worldObj.getBlockId(xCoord, yCoord + 4, zCoord)];
            if (validBlock(block))
            {
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord + 4, zCoord, 15, 3);
                //spawnNegativeAir(15, 1);

                int value = 88;
                //WorldTracker.updateTheft(worldObj.provider.dimensionId, xCoord, zCoord, value, CrystalType.Light);
                //WorldTracker.updateCharge(worldObj.provider.dimensionId, xCoord, zCoord, -value, CrystalType.Light);
                CrystalLogic crystal = (CrystalLogic) worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord);
                crystal.setCrystalValue(528);
            }
        }
    }

    public void spawnNegativeAir (int range, int darkness)
    {
        /*for (int xPos = -range; xPos <= range; xPos++)
        {
            for (int yPos = -range; yPos <= range; yPos++)
            {
                for (int zPos = -range; zPos <= range; zPos++)
                {
                    if (Math.abs(xPos) + Math.abs(yPos) + Math.abs(zPos) <= range)
                    {
                        Block block = Block.blocksList[worldObj.getBlockId(xCoord + xPos, yCoord + yPos, zCoord + zPos)];
                        if (block == null || block.isAirBlock(worldObj, xCoord + xPos, yCoord + yPos, zCoord + zPos))
                            worldObj.setBlock(xCoord + xPos, yCoord + yPos, zCoord + zPos, CrystalContent.antilight.blockID, darkness, 3);
                    }
                }
            }
        }*/
    }
    
    public int getCrystalValue()
    {
        return crystalValue;
    }

    boolean validBlock (Block block)
    {
        return block == CrystalContent.crystalBlock;
    }

    public boolean getActive ()
    {
        return active;
    }

    /*public void setActive (boolean flag)
    {
        if (active != flag)
        {
            active = flag;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }*/

    @Override
    public void readFromNBT (NBTTagCompound tags)
    {
        super.readFromNBT(tags);
        currentTime = tags.getShort("Time");
        sugar = tags.getShort("Sugar");
        readCustomNBT(tags);
    }

    @Override
    public void writeToNBT (NBTTagCompound tags)
    {
        super.writeToNBT(tags);
        tags.setShort("Time", currentTime);
        tags.setShort("Sugar", sugar);
        writeCustomNBT(tags);
    }

    public void readCustomNBT (NBTTagCompound tags)
    {
        active = tags.getBoolean("Active");
        crystalValue = tags.getInteger("CrystalValue");
    }

    public void writeCustomNBT (NBTTagCompound tags)
    {
        tags.setBoolean("Active", active);
        tags.setInteger("CrystalValue", crystalValue);
    }

    /* Packets */
    @Override
    public Packet getDescriptionPacket ()
    {
        NBTTagCompound tag = new NBTTagCompound();
        writeCustomNBT(tag);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
    }

    @Override
    public void onDataPacket (INetworkManager net, Packet132TileEntityData packet)
    {
        readCustomNBT(packet.data);
        worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
    }

    public int harvestCrystal ()
    {
        crystalValue = crystalValue % 10;
        return 0;
        //int tmp = crystalValue;
        //TheftTracker.updateValue(worldObj.provider.dimensionId, xCoord, zCoord, -tmp, CrystalType.Light);
        //crystalValue = 0;
        //return tmp;
    }
}
