package dev.ukry.meetup.game.world

import dev.ukry.api.handler.Handler
import dev.ukry.meetup.game.world.generation.GeneratorHandler
import dev.ukry.meetup.util.CC
import org.bukkit.Bukkit
import org.bukkit.World
import java.io.File

/**
 * @author UKry
 * Created: 26/09/2022
 * Project UHCMeetup
 **/

class WorldHandler : Handler() {

    var world : World? = null
    val worldFolder = File(Bukkit.getWorldContainer(), "world")
    lateinit var generatorHandler : GeneratorHandler

    override fun preInit() {
        generatorHandler = GeneratorHandler()
        generatorHandler.preInit()
    }

    override fun init() {
        generatorHandler.init()
    }

    fun removeWorld(world : World) {
        val name = world.name
        val folder = File(Bukkit.getWorldContainer(), name)
        Bukkit.unloadWorld(name, false)
        val start = System.currentTimeMillis()
        do {
            deleteDirectory(folder)
        } while (folder.exists())
        CC.log("&aWorld ${world.name} deleted after ${System.currentTimeMillis()-start} millis!")
    }

    fun removeFolder(folder : File) {
        val start = System.currentTimeMillis()
        do {
            deleteDirectory(folder)
        } while (folder.exists())
        CC.log("Folder ${folder.path} deleted after ${System.currentTimeMillis()-start}millis")
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