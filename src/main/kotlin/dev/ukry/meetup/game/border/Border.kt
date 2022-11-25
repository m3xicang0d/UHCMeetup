package dev.ukry.meetup.game.border

import dev.ukry.meetup.Burrito
import dev.ukry.meetup.game.size.task.BorderGeneratorTask
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World

/**
 * @author UKry
 * Created: 06/10/2022
 * Project UHCMeetup
 **/

data class Border(
    val initialBorder : Int,
    val reduction : Int,
    val minBorder : Int,
    val time : Int,
    var world : World?,
    var actualBorder : Int = initialBorder,
    var min : Boolean = false
) {

    private val unsafeBlocks = mutableListOf(Material.LAVA, Material.STATIONARY_LAVA)


    fun resize() {
        val toReduct = actualBorder - reduction
        if(toReduct <= minBorder) {
            min = true
            Bukkit.broadcastMessage("El borde llego al minimo")
        } else {
            actualBorder = toReduct
            generateBedrock()
            Bukkit.broadcastMessage("Border changed to $actualBorder")

        }
    }

    fun generateBedrock() {
        BorderGeneratorTask(world!!, actualBorder).runTaskTimer(Burrito.getInstance(), 0L, 3L)
        teleportNearbies()
    }

    private fun teleportNearbies() {
        for(player in Bukkit.getServer().onlinePlayers) {
            val loc = player.location.clone()
            val x = loc.x
            val z = loc.z
            var teleport = false
            //z+
            if(z > 0 && z >= actualBorder) {
                loc.z = actualBorder.toDouble() - 10
                while (isUnSafe(loc)) {
                    loc.z -= 1
                }
                teleport = true
            }
            //x+
            if(x > 0 && x >= actualBorder) {
                loc.x = actualBorder.toDouble() - 10
                while (isUnSafe(loc)) {
                    loc.x -= 1
                }
                teleport = true
            }
            //z-
            if(z < 0 && z <= -actualBorder) {
                loc.z = -actualBorder.toDouble() + 10
                while (isUnSafe(loc)) {
                    loc.z += 1
                }
                teleport = true
            }
            //x-
            if(x < 0 && x <= -actualBorder) {
                loc.x = -actualBorder.toDouble() + 10
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