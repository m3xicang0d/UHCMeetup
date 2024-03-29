package dev.ukry.meetup

import dev.ukry.meetup.command.InfoCommand
import dev.ukry.meetup.command.admin.ConfigCommand
import dev.ukry.meetup.command.admin.WorldCommand
import dev.ukry.meetup.config.command.ReloadConfigCommand
import dev.ukry.meetup.game.GameHandler
import dev.ukry.meetup.game.listener.GlitchListener
import dev.ukry.meetup.game.listener.PearlCooldown
import dev.ukry.meetup.game.world.WorldHandler
import dev.ukry.meetup.lobby.LobbyHandler
import dev.ukry.meetup.lobby.chunk.EmptyChunkGenerator
import dev.ukry.meetup.scoreboard.ScoreboardProvider
import dev.ukry.meetup.scoreboard.type.*
import dev.ukry.meetup.storage.StorageHandler
import dev.ukry.meetup.tablist.TabListHandler
import dev.ukry.meetup.user.profile.ProfileHandler
import dev.ukry.menu.ButtonListeners
import dev.ukry.menu.MenuAutoUpdater
import io.github.thatkawaiisam.assemble.Assemble
import me.gleeming.command.CommandHandler
import org.bukkit.Bukkit
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

    //Game setion
    lateinit var gameHandler : GameHandler
    lateinit var profileHandler : ProfileHandler

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

        profileHandler = ProfileHandler()
    }

    override fun onEnable() {
        CommandHandler.registerCommands(InfoCommand::class.java)
        CommandHandler.registerCommands(WorldCommand::class.java)
        CommandHandler.registerCommands(ConfigCommand::class.java)

        lobbyHandler.init()

        worldHandler.init()

        gameHandler.init()

        Assemble(this, ScoreboardProvider(
            GeneratingScoreboard(),
            LobbyScoreboard(),
            CountdownScoreboard(),
            PlayingScoreboard(SpectatorScoreboard()),
            EndingScoreboard()
        ))

        if (config.getBoolean("TABLIST.ENABLED")) {
            Bukkit.getPluginManager().registerEvents(TabListHandler(), this)
        }

        Bukkit.getPluginManager().registerEvents(PearlCooldown(), this)
        Bukkit.getPluginManager().registerEvents(GlitchListener(), this)
        Bukkit.getPluginManager().registerEvents(ButtonListeners, this)
        Bukkit.getPluginManager().registerEvents(MenuAutoUpdater, this)
    }

    override fun onDisable() {
        storageHandler.storage.stop()
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