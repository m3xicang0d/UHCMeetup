package dev.mexican.meetup.game.world

import dev.mexican.meetup.Burrito
import dev.mexican.meetup.game.world.generation.GeneratorHandler
import dev.mexican.meetup.game.world.generation.type.map.task.MapGeneratorTask
import dev.ukry.api.handler.Handler

/**
 * @author UKry
 * Created: 26/09/2022
 * Project UHCMeetup
 **/

class WorldHandler : Handler() {

    lateinit var generatorHandler : GeneratorHandler

    override fun preInit() {
        generatorHandler = GeneratorHandler()
        generatorHandler.preInit()
    }

    fun generateMap() {
    }

    override fun init() {
        generatorHandler.init()
    }
}