package crystal.util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Random;

import tconstruct.TConstruct;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class CrystalPacketHandler implements IPacketHandler
{
    private static Random random = new Random();

    @Override
    public void onPacketData (INetworkManager manager, Packet250CustomPayload packet, Player player)
    {
        Side side = FMLCommonHandler.instance().getEffectiveSide();

        if (packet.channel.equals("CrystalClimate"))
        {
            if (side == Side.SERVER)
                handleServerPacket(packet, (EntityPlayerMP) player);
            else
                handleClientPacket(packet, (EntityPlayer) player);
        }
    }

    private void handleClientPacket (Packet250CustomPayload packet, EntityPlayer player)
    {
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
        byte packetID;

        try
        {
            packetID = inputStream.readByte();
            
            if (packetID == 1) //Eating sugar
            {
                int dimension = inputStream.readInt();
                World world = DimensionManager.getWorld(dimension);
                int x = inputStream.readInt();
                int y = inputStream.readInt();
                int z = inputStream.readInt();
                
                for (int i = 0; i < 16; i++)
                    world.spawnParticle("smoke", x + random.nextFloat(), y + 1.0f + random.nextFloat() * 0.5f, z + random.nextFloat(), 0, 0, 0);
            }
        }
        catch (IOException e)
        {
            TConstruct.logger.warning("Failed at reading client packet for Crystal Climate.");
            e.printStackTrace();
        }
    }

    private void handleServerPacket (Packet250CustomPayload packet, EntityPlayerMP player)
    {
        
    }

}
