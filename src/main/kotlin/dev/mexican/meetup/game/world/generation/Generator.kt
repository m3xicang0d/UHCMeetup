package dev.mexican.meetup.game.world.generation

import dev.mexican.meetup.config.SettingsFile

/**
 * @author UKry
 * Created: 06/10/2022
 * Project UHCMeetup
 **/

abstract class Generator {

    val config = SettingsFile.getConfig()

    open fun preConfiguration() {}

    abstract fun generateWorld()
}