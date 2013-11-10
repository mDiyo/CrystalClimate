package cclimate

import cclimate.lib.Repo._
import cpw.mods.fml.common.{SidedProxy, FMLLog, Mod}
import cpw.mods.fml.common.network.NetworkMod
import cclimate.network.PacketHandler
import cpw.mods.fml.common.event.{FMLPostInitializationEvent, FMLInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.Mod.EventHandler
import cclimate.lib.ConfigCore
import net.minecraftforge.common.Configuration
import cclimate.common.CommonProxy

@Mod(modid = modId, name = modName, version = modVer, modLanguage = "scala")
@NetworkMod(serverSideRequired = false, clientSideRequired = true, channels = Array("CClimate"), packetHandler = classOf[PacketHandler])
object CClimate {

  @SidedProxy(clientSide = "cclimate.client.ClientProxy", serverSide = "cclimate.common.CommonProxy", modId = modId)
  var proxy: CommonProxy = null

  @EventHandler
  def preInit(evt:FMLPreInitializationEvent) {
    logger.setParent(FMLLog.getLogger)
    logger.info(s"$modName ($modVer) starting...")
    ConfigCore.loadConfig(new Configuration(evt.getSuggestedConfigurationFile))
  }

  @EventHandler
  def init(evt:FMLInitializationEvent) {

  }

  @EventHandler
  def postInit(evt:FMLPostInitializationEvent) {
    logger.info("Setup complete.")
  }

}
