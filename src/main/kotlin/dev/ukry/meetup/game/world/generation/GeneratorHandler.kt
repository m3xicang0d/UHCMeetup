package dev.ukry.meetup.game.world.generation

import dev.ukry.api.handler.Handler
import dev.ukry.meetup.config.SettingsFile
import dev.ukry.meetup.game.world.command.RegenerateCommand
import dev.ukry.meetup.game.world.generation.type.map.MapGenerator
import dev.ukry.meetup.game.world.generation.type.seed.SeedGenerator
import dev.ukry.meetup.util.CC
import me.gleeming.command.CommandHandler
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
                CC.log("&cError, the generator type $type not exist!")
                exitProcess(0)
            }
        }
    }

    override fun init() {
        generator.preConfiguration()
        CommandHandler.registerCommands(RegenerateCommand::class.java)
    }
}