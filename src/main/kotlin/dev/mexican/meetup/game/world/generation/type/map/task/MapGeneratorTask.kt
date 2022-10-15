package dev.mexican.meetup.game.world.generation.type.map.task

import dev.mexican.meetup.Burrito
import dev.mexican.meetup.config.SettingsFile
import dev.mexican.meetup.game.Game
import dev.mexican.meetup.game.state.GameState
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.scheduler.BukkitRunnable
import java.io.File
import kotlin.random.Random

/**
 * @author UKry
 * Created: 26/09/2022
 * Project UHCMeetup
 **/

class MapGeneratorTask(private val game : Game, val forced : Boolean) : BukkitRunnable() {

    private var busy = false
    private var seed = Random.nextLong()
    private val plugin = Burrito.getInstance()
    private val invalids = mutableListOf(
        Material.WATER,
        Material.STATIONARY_WATER,
        Material.LAVA,
        Material.STATIONARY_LAVA
    )

    override fun run() {
        if(busy) return
        busy = true
        var world : World? = Burrito.getInstance().worldHandler.world
        val folder = Burrito.getInstance().worldHandler.worldFolder
        val lock = File(folder, "empty.lock")
        /*if(lock.exists() && !forced) {
            println("Cancelled world creation because is unused!")
            cancel()
            game.state = GameState.WAITING
            return
        }*/
        if(world != null) {
            world.players.forEach {
                plugin.lobbyHandler.sendToLobby(it)
            }
            Burrito.getInstance().worldHandler.removeWorld(world)
        }
        if(folder.exists()) {//If world not is loaded but folder exist!
            Burrito.getInstance().worldHandler.removeFolder(folder)
        }
        val creator = WorldCreator("world")
            .generateStructures(false)
            .seed(seed)
        try {
            world = Bukkit.getServer().createWorld(creator)
        } catch (e : Exception) {
            Bukkit.getConsoleSender().sendMessage("Error, trying create map!")
            Bukkit.getServer().unloadWorld(world, false)
            Burrito.getInstance().worldHandler.removeFolder(folder)
            return
        }
        var invalid = false
        var water = 0

        var breaked = false
        for (x in -game.border.initialBorder .. game.border.initialBorder) {
            for (z in -game.border.initialBorder .. game.border.initialBorder) {
                val isCenter = x >= -100 && x <= 100 && z >= -100 && z <= 100
                if(isCenter) {
                    val type = world.getHighestBlockAt(x, z).location.add(0.0, -1.0, 0.0).block.type
                    if(invalids.contains(type)) {
                        water++
                    }
                }
                if (water >= SettingsFile.getConfig().getInt("GENERATION.LIQUID_LIMIT")) {
                    Bukkit.getConsoleSender().sendMessage("Invalid center, too much water/lava.")
                    invalid = true
                    breaked = true
                    break
                }
            }
            if(breaked) {
                break
            }
        }
        Bukkit.getConsoleSender().sendMessage("Biome=${world.getHighestBlockAt(0, 0).biome.name}")
        if(invalid) {
            Bukkit.getServer().unloadWorld(world, false)
            Bukkit.getConsoleSender().sendMessage("World too ugly, generating other")
            Burrito.getInstance().worldHandler.removeWorld(world)
            seed = Random.nextLong()
            busy = false
            return
        } else {
            Bukkit.getConsoleSender().sendMessage("Successfully generated UHC world!")
            Bukkit.getConsoleSender().sendMessage("Water=$water")
            cancel()
        }
        lock.createNewFile()
        Burrito.getInstance().worldHandler.world = world
        game.border.world = world
        game.state = GameState.WAITING
    }


}