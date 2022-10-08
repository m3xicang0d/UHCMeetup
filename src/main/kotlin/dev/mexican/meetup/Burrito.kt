package dev.mexican.meetup

import dev.mexican.meetup.command.InfoCommand
import dev.mexican.meetup.command.admin.WorldCommand
import dev.mexican.meetup.config.command.ReloadConfigCommand
import dev.mexican.meetup.game.GameHandler
import dev.mexican.meetup.game.world.WorldHandler
import dev.mexican.meetup.lobby.chunk.EmptyChunkGenerator
import dev.mexican.meetup.lobby.LobbyHandler
import dev.mexican.meetup.scoreboard.ScoreboardProvider
import dev.mexican.meetup.scoreboard.type.*
import dev.mexican.meetup.storage.Storage
import dev.mexican.meetup.storage.StorageHandler
import io.github.thatkawaiisam.assemble.Assemble
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
    lateinit var gameHandler : GameHandler

    override fun onLoad() {
        saveDefaultConfig()
        CommandHandler.init(this)
        CommandHandler.registerCommands(ReloadConfigCommand::class.java)

        lobbyHandler = LobbyHandler()
        lobbyHandler.preInit()

        worldHandler = WorldHandler()
        worldHandler.preInit()

        storageHandler = StorageHandler().also { it.load() }

        gameHandler = GameHandler()
        gameHandler.preInit()
    }

    override fun onEnable() {
        CommandHandler.registerCommands(InfoCommand::class.java)
        CommandHandler.registerCommands(WorldCommand::class.java)

        lobbyHandler.init()

        worldHandler.init()

        gameHandler.init()

        Assemble(this, ScoreboardProvider(
            GeneratingScoreboard(),
            LobbyScoreboard(),
            ScattingScoreboard(),
            CountdownScoreboard(),
            PlayingScoreboard(),
            EndingScoreboard()
        ))
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