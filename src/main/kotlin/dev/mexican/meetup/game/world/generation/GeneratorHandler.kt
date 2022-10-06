package dev.mexican.meetup.game.world.generation

import dev.mexican.meetup.config.SettingsFile
import dev.mexican.meetup.game.world.command.GenerateCommand
import dev.mexican.meetup.game.world.generation.type.map.MapGenerator
import dev.mexican.meetup.game.world.generation.type.seed.SeedGenerator
import dev.ukry.api.handler.Handler
import me.gleeming.command.CommandHandler
import org.bukkit.Bukkit
import kotlin.system.exitProcess

/**
 * @author UKry
 * Created: 06/10/2022
 * Project UHCMeetup
 **/

class GeneratorHandler : Handler() {

    private val config = SettingsFile.getConfig()
    lateinit var generator : Generator

    override fun preInit() {
        registerGenerator()
    }

    private fun registerGenerator() {
        val type = config.getString("GENERATION.TYPE")
        generator = when(type.uppercase()) {
            "SEED" -> SeedGenerator()
            "INTEGRATED" -> MapGenerator()
            else -> {
                Bukkit.getConsoleSender().sendMessage("Error, the generator type $type not exist!")
                exitProcess(0)
            }
        }
    }

    override fun init() {
        generator.preConfiguration()
        generator.generateWorld()
        CommandHandler.registerCommands(GenerateCommand::class.java)
    }
}