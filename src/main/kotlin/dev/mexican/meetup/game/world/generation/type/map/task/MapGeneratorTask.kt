package dev.mexican.meetup.game.world.generation.type.map.task

import dev.mexican.meetup.Burrito
import dev.mexican.meetup.config.SettingsFile
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

class MapGeneratorTask : BukkitRunnable() {

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
        var world : World? = Bukkit.getWorld("world")
        val folder = File(Bukkit.getWorldContainer(), "world")
        if(world != null) {
            world.players.forEach {
                plugin.lobbyHandler.sendToLobby(it)
            }
            Bukkit.unloadWorld(world, false)
            val start = System.currentTimeMillis()
            do {
                deleteDirectory(folder)
            } while (folder.exists())
            println("Deleted after ${System.currentTimeMillis()-start}millis")
        }
        if(folder.exists()) {//If world not is loaded but folder exist!
            val start = System.currentTimeMillis()
            do {
                deleteDirectory(folder)
            } while (folder.exists())
            println("Deleted after ${System.currentTimeMillis()-start}millis")
        }
        val creator = WorldCreator("world")
            .generateStructures(false)
            .seed(seed)
        try {
            world = Bukkit.getServer().createWorld(creator)
        } catch (e : Exception) {
            Bukkit.getConsoleSender().sendMessage("Error, trying create map!")
            Bukkit.getServer().unloadWorld(world, false)
            deleteDirectory(folder)
            return
        }
        var invalid = false
        var water = 0

        var breaked = false
        for (x in -plugin.borderHandler.border .. plugin.borderHandler.border) {
            for (z in -plugin.borderHandler.border .. plugin.borderHandler.border) {
                val isCenter = x >= -100 && x <= 100 && z >= -100 && z <= 100
                if(isCenter) {
                    val type = world.getHighestBlockAt(x, z).location.add(0.0, -1.0, 0.0).block.type
                    if(invalids.contains(type)) {
                        water++
                    }
                }
                if (water >= SettingsFile.getConfig().getInt("GENERATION.WATER_LIMIT")) {
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
            val start = System.currentTimeMillis()
            do {
                deleteDirectory(folder)
            } while (folder.exists())
            println("Deleted after ${System.currentTimeMillis()-start}millis")
            seed = Random.nextLong()
            busy = false
        } else {
            Bukkit.getConsoleSender().sendMessage("Successfully generated UHC world!")
            Bukkit.getConsoleSender().sendMessage("Water=$water")
            cancel()
        }
    }

    private fun deleteDirectory(path: File): Boolean {
        if (path.exists()) {
            val files = path.listFiles()
            if (files != null) {
                for (i in files.indices) {
                    if (files[i].isDirectory) {
                        deleteDirectory(files[i])
                    } else {
                        files[i].delete()
                    }
                }
            }
        }
        return path.delete()
    }
}