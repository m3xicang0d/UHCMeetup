package dev.mexican.meetup.game.world.generation

import dev.mexican.meetup.config.SettingsFile
import dev.mexican.meetup.game.Game
import org.bukkit.World

/**
 * @author UKry
 * Created: 06/10/2022
 * Project UHCMeetup
 **/

abstract class Generator {

    val config = SettingsFile.getConfig()

    open fun preConfiguration() {}

    abstract fun generateWorld(game : Game)
}