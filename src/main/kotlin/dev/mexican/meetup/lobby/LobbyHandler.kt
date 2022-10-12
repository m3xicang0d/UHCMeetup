package dev.mexican.meetup.lobby

import dev.mexican.meetup.Burrito
import dev.mexican.meetup.config.SettingsFile
import dev.mexican.meetup.lobby.listener.LobbyListener
import dev.ukry.api.handler.Handler
import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import dev.mexican.meetup.util.Properties
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
        Bukkit.getConsoleSender().sendMessage("-".repeat(20))
        Bukkit.getConsoleSender().sendMessage("Bukkit properties has been configured")
        Bukkit.getConsoleSender().sendMessage("THIS NOT IS A ERROR!")
        Bukkit.getConsoleSender().sendMessage("-".repeat(20))
        exitProcess(0)
    }

    override fun init() {
        Bukkit.getPluginManager().registerEvents(LobbyListener(), Burrito.getInstance())
    }

    fun sendToLobby(player : Player) {
        val world = Bukkit.getWorld(lobbyName)!!
        player.teleport(world.spawnLocation)
    }
}