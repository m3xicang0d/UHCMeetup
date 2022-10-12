package dev.mexican.meetup.game

import dev.mexican.meetup.Burrito
import dev.mexican.meetup.game.border.Border
import dev.mexican.meetup.game.state.GameState
import dev.mexican.meetup.util.CC
import dev.ukry.api.cooldown.Cooldown
import org.bukkit.Bukkit
import org.bukkit.Location
import java.util.*
import kotlin.random.Random

/**
 * @author UKry
 * Created: 27/09/2022
 * Project UHCMeetup
 **/

class Game(
    var border : Border
) {

    var cooldown : Cooldown? = null
    var state = GameState.GENERATING
        set(value) {
            field = value
            when(value) {
                GameState.GENERATING -> {
                    CC.announceAndLog("Generating UHC world!")
                    generateMap()
                }
                GameState.WAITING -> {
                    CC.announceAndLog("Map generated successfully")
                }
                GameState.SCATTING -> {
                    border.generateBedrock()
                    CC.announceAndLog("Scatting players!")
                    scatte()
                }
                GameState.COUNTDOWN -> {
                    cooldown = Cooldown().also { it.setCooldown(30) }
                }
                GameState.PLAYING -> {
                }
                GameState.ENDING -> {

                }
            }
        }

    fun generateMap() {
        Burrito.getInstance().worldHandler.generatorHandler.generator.generateWorld(this, false)
    }

    fun a() {
        state = GameState.SCATTING
    }

    fun scatte() {
        val world = Burrito.getInstance().worldHandler.world
        if(world == null) {
            CC.announceAndLog("Map no generated! generating")
            state = GameState.GENERATING
        }
        val locs = mutableMapOf<UUID, Pair<Double, Double>>()
        val radius = 15
        Bukkit.getServer().onlinePlayers.forEach {p ->
            val min = -border.initialBorder
            println("Min: $min")
            val max = border.initialBorder
            println("Max: $max")
            val x = Random.nextInt(max - min) + min
            println(x)
            var fX = x.toDouble()
            val z = Random.nextInt(max - min) + min
            println(z)
            var fZ = z.toDouble()
            val pair = Pair(fX, fZ)
//            val loc = Location(world, fX,  fZ)
            if(locs.containsValue(pair)) {
                //z+
                if (z > 0) {
                    //Prevent left for border
                    var zz = z + radius
                    do {
                        zz -= 1
                    } while (zz > max)
                    fZ = zz.toDouble()
                }
                //x+
                if (x > 0) {
                    //Prevent left for border
                    var xx = x + radius
                    do {
                        xx -= 1
                    } while (xx > max)
                    fX = xx.toDouble()
                }
                //z-
                if (z < 0) {
                    //Prevent left for border
                    var zz = z - radius
                    do {
                        zz += 1
                    } while (zz < max)
                    fZ = zz.toDouble()
                }
                //x-
                if (x < 0) {
                    //Prevent left for border
                    var xx = x - radius
                    do {
                        xx += 1
                    } while (xx < max)
                    fX = xx.toDouble()
                }
            } else {
                locs[p.uniqueId] = pair
            }
            println(fX)
            println(fZ)
            val top = world!!.getHighestBlockAt(fX.toInt(), fZ.toInt())
            val loc = Location(world, fX, top.y.toDouble(), fZ).add(0.0, 1.0, 0.0)
            p.teleport(loc)
        }
        state = GameState.COUNTDOWN
    }

    fun start() {

    }

    fun end() {

    }
}