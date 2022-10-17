package dev.mexican.meetup.game

import dev.mexican.meetup.Burrito
import dev.mexican.meetup.game.border.Border
import dev.mexican.meetup.game.state.GameState
import dev.mexican.meetup.util.CC
import dev.ukry.api.cooldown.Cooldown
import net.minecraft.server.v1_8_R3.Entity
import net.minecraft.server.v1_8_R3.NBTTagCompound
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPig
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.EntityType
import org.bukkit.entity.Pig
import org.bukkit.entity.Player
import org.bukkit.metadata.FixedMetadataValue
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
                    Bukkit.getScheduler().runTaskLater(Burrito.getInstance(), {sitPlayers()}, 5L)
                }
                GameState.PLAYING -> {
                    unSitePlayers()
                }
                GameState.ENDING -> {

                }
            }
        }

    val participants = mutableListOf<UUID>()
    val spectators = mutableListOf<UUID>()
    val pigs = mutableMapOf<UUID, Int>() //Pigs because dont move

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
            val max = border.initialBorder
            val x = Random.nextInt(max - min) + min
            var fX = x.toDouble()
            val z = Random.nextInt(max - min) + min
            var fZ = z.toDouble()
            val pair = Pair(fX, fZ)
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
            val top = world!!.getHighestBlockAt(fX.toInt(), fZ.toInt())
            val loc = Location(world, fX, top.y.toDouble(), fZ).add(0.0, 1.0, 0.0)
            p.teleport(loc)
        }
        state = GameState.COUNTDOWN
    }

    fun start() {
        state = GameState.SCATTING

    }

    fun end() {

    }

    fun sitPlayers() {
        participants.stream()
            .map(Bukkit::getPlayer)
            .filter(Objects::nonNull)
            .forEach {
                val pig = it.location.add(0.5, 1.0, 0.5).world.spawnEntity(it.location, EntityType.PIG) as Pig
                pig.maxHealth = 20.0
                pig.health = 20.0
                pig.setAdult()
                pig.passenger = it

                //invulnerability

                val field = Entity::class.java.getDeclaredField("invulnerable")
                field.isAccessible = true
                field.set((pig as CraftPig).handle, true)

                //nms
                val nms = (pig as CraftEntity).handle
                var tag = nms.nbtTag
                if (tag == null) {
                    tag = NBTTagCompound()
                }
                nms.c(tag)
                tag.setInt("NoAI", 1)
                tag.setInt("Silent", 1)
                nms.f(tag)

                Bukkit.getScheduler().runTaskLater(Burrito.getInstance(), { (pig as CraftEntity).handle.isInvisible = true }, 2L)

                pigs[it.uniqueId] = (pig as CraftEntity).entityId
            }
    }

    fun addParticipant(player : Player) {
        participants.add(player.uniqueId)
        Bukkit.broadcastMessage("Player ${player.name} joined!")
    }

    fun addSpectator(player : Player) {
        spectators.add(player.uniqueId)
        Bukkit.broadcastMessage("Spectator ${player.name} joined!")
    }

    fun isParticipant(player : Player) : Boolean {
        return !participants.contains(player.uniqueId)
    }

    fun unSitePlayers() {
        participants.stream()
            .map(Bukkit::getPlayer)
            .filter(Objects::nonNull)
            .forEach {
                val id = pigs[it.uniqueId]
                it.vehicle.remove()
                val packet = PacketPlayOutEntityDestroy(id!!)
                (it as CraftPlayer).handle.playerConnection.sendPacket(packet)
                pigs.remove(it.uniqueId)
            }
    }

}