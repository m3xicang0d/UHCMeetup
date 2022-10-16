package dev.mexican.meetup.game

import dev.mexican.meetup.Burrito
import dev.mexican.meetup.config.SettingsFile
import dev.mexican.meetup.game.border.Border
import dev.mexican.meetup.game.border.command.BorderCommand
import dev.mexican.meetup.game.command.ScatteCommand
import dev.mexican.meetup.game.command.StartCommand
import dev.mexican.meetup.game.command.processors.GameStateProcessor
import dev.mexican.meetup.game.listener.*
import dev.ukry.api.handler.Handler
import me.gleeming.command.CommandHandler
import me.gleeming.command.paramter.ParamProcessor
import org.bukkit.Bukkit

/**
 * @author UKry
 * Created: 27/09/2022
 * Project UHCMeetup
 **/

class GameHandler : Handler() {

    val config = SettingsFile.getConfig()
    var actualGame : Game? = null

    fun createGame() {
        if(actualGame != null) {
            Bukkit.getConsoleSender().sendMessage("Actually is running a game!")
            return
        }
        actualGame = Game(
            Border(
                config.getInt("GAME.BORDER.INITIAL"),
                config.getInt("GAME.BORDER.REDUCTION"),
                config.getInt("GAME.BORDER.MINIMAL"),
                config.getInt("GAME.BORDER.TIME"),
                Burrito.getInstance().worldHandler.world
            )
        )
        actualGame!!.generateMap()
    }

    override fun init() {
        ParamProcessor.createProcessor(GameStateProcessor())
        CommandHandler.registerCommands(BorderCommand::class.java)
        CommandHandler.registerCommands(ScatteCommand::class.java)
        CommandHandler.registerCommands(StartCommand::class.java)
        Bukkit.getServer().pluginManager.registerEvents(PigListener(), Burrito.getInstance())
        Bukkit.getServer().pluginManager.registerEvents(SpectatorListener(), Burrito.getInstance())
        Bukkit.getServer().pluginManager.registerEvents(GameListener(), Burrito.getInstance())
        Bukkit.getServer().pluginManager.registerEvents(LootListener(), Burrito.getInstance())
        Bukkit.getServer().pluginManager.registerEvents(TablistListener(), Burrito.getInstance())
        createGame()
    }
}