package dev.mexican.meetup

import dev.mexican.meetup.command.InfoCommand
import dev.mexican.meetup.command.admin.WorldCommand
import dev.mexican.meetup.game.border.BorderHandler
import dev.mexican.meetup.game.world.WorldHandler
import dev.mexican.meetup.game.world.chunk.EmptyChunkGenerator
import dev.mexican.meetup.lobby.LobbyHandler
import dev.mexican.meetup.storage.Storage
import dev.mexican.meetup.storage.StorageHandler
import me.gleeming.command.CommandHandler
import org.bukkit.generator.ChunkGenerator
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author UKry
 * Created: 26/09/2022
 * Project UHCMeetup
 **/

class Burrito : JavaPlugin() {

    //Map section
    lateinit var lobbyHandler : LobbyHandler
    lateinit var worldHandler : WorldHandler

    //Storage section
    lateinit var storageHandler : StorageHandler
    lateinit var storage : Storage

    //Game setion
    lateinit var borderHandler : BorderHandler

    override fun onLoad() {
        saveDefaultConfig()

        lobbyHandler = LobbyHandler()
        lobbyHandler.preInit()

        worldHandler = WorldHandler()
        worldHandler.preInit()

        storageHandler = StorageHandler().also { it.load() }
        CommandHandler.init(this)
    }

    override fun onEnable() {
        lobbyHandler.init()

        worldHandler.init()
        worldHandler.generateMap()
        CommandHandler.registerCommands(InfoCommand::class.java)
        CommandHandler.registerCommands(WorldCommand::class.java)
        borderHandler = BorderHandler().also { it.init() }
    }

    override fun getDefaultWorldGenerator(worldName: String, id: String?): ChunkGenerator? {
        return if(worldName.equals(lobbyHandler.lobbyName, true)) {
            EmptyChunkGenerator()
        } else {
            null
        }
    }

    companion object {
        @JvmStatic
        fun getInstance() : Burrito {
            return getPlugin(Burrito::class.java)
        }
    }
}