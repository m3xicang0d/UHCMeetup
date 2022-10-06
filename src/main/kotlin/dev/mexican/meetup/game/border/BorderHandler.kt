package dev.mexican.meetup.game.border

import dev.mexican.meetup.Burrito
import dev.mexican.meetup.game.border.command.BorderCommand
import dev.mexican.meetup.game.border.task.BorderGeneratorTask
import me.gleeming.command.CommandHandler
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material

/**
 * @author UKry
 * Created: 04/10/2022
 * Project UHCMeetup
 **/

class BorderHandler {

    private val unsafeBlocks = mutableListOf(Material.LAVA, Material.STATIONARY_LAVA)
    var border = 300
        set(value) {
            field = value
            generateBedrock()
            teleportNearbies()
        }

    fun init() {
        CommandHandler.registerCommands(BorderCommand::class.java)
    }

    private fun generateBedrock() {
        val world = Bukkit.getWorld("Lobby")
        BorderGeneratorTask(world, border).runTaskTimer(Burrito.getInstance(), 0L, 3L)
    }

    private fun teleportNearbies() {
        for(player in Bukkit.getServer().onlinePlayers) {
            val loc = player.location.clone()
            val x = loc.x
            val z = loc.z
            var teleport = false
            //z+
            if(z > 0 && z >= border) {
                loc.z = border.toDouble() - 10
                while (isUnSafe(loc)) {
                    loc.z -= 1
                }
                teleport = true
            }
            //x+
            if(x > 0 && x >= border) {
                loc.x = border.toDouble() - 10
                while (isUnSafe(loc)) {
                    loc.x -= 1
                }
                teleport = true
            }
            //z-
            if(z < 0 && z <= -border) {
                loc.z = -border.toDouble() + 10
                while (isUnSafe(loc)) {
                    loc.z += 1
                }
                teleport = true
            }
            //x-
            if(x < 0 && x <= -border) {
                loc.x = -border.toDouble() + 10
                while (isUnSafe(loc)) {
                    loc.x += 1
                }
                teleport = true
            }
            loc.y = loc.world.getHighestBlockAt(loc).y.toDouble()
            if(teleport) {
                player.teleport(loc)
            }
        }
    }

    private fun isUnSafe(loc : Location): Boolean {
        return unsafeBlocks.contains(loc.world.getHighestBlockAt(loc).type)
    }
}