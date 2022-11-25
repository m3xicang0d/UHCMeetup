package dev.ukry.meetup.lobby

import dev.ukry.api.handler.Handler
import dev.ukry.meetup.Burrito
import dev.ukry.meetup.config.SettingsFile
import dev.ukry.meetup.lobby.listener.LobbyListener
import dev.ukry.meetup.util.CC
import dev.ukry.meetup.util.Properties
import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File
import kotlin.system.exitProcess

/**
 * @author UKry
 * Created: 26/09/2022
 * Project UHCMeetup
 **/

class LobbyHandler : Handler() {

    private val config : FileConfiguration = SettingsFile.getConfig()
    val lobbyName : String = config.getString("LOBBY.WORLD")

    override fun preInit() {
        verifyBukkitConfig()
        verifyProperties()
    }

    private fun verifyProperties() {
        Properties.setServerProperty("generate-structures", false)
        Properties.setServerProperty("level-name", Burrito.getInstance().lobbyHandler.lobbyName)
        Properties.setServerProperty("level-type", "DEFAULT")
        Properties.savePropertiesFile()
    }

    private fun verifyBukkitConfig() {
        val mark = File(Burrito.getInstance().dataFolder, "bukkit.lock")
        if(mark.exists()) return
        val configFile = File(Bukkit.getServer().worldContainer, "bukkit.yml")
        val configYaml = YamlConfiguration.loadConfiguration(configFile)
        configYaml.set("worlds.${Burrito.getInstance().lobbyHandler.lobbyName}.generator", "Burrito")
        configYaml.save(configFile)
        mark.createNewFile()
        CC.log("&7${"-".repeat(20)}")
        CC.log("&aBukkit properties has been configured")
        CC.log("&7THIS &aNOT IS A ERROR!")
        CC.log("&7${"-".repeat(20)}")
        exitProcess(0)
    }

    override fun init() {
        Bukkit.getPluginManager().registerEvents(LobbyListener(), Burrito.getInstance())
    }

    fun sendToLobby(player : Player) {
        val world = Bukkit.getWorld(lobbyName)!!
        player.teleport(world.spawnLocation.clone().add(0.5, 1.0, 0.5))
    }
}