package dev.ukry.meetup.game.world.generation

import dev.ukry.meetup.config.SettingsFile
import dev.ukry.meetup.game.Game

/**
 * @author UKry
 * Created: 06/10/2022
 * Project UHCMeetup
 **/

abstract class Generator {

    val config = SettingsFile.getConfig()

    open fun preConfiguration() {}

    abstract fun generateWorld(game : Game, forced : Boolean)
}