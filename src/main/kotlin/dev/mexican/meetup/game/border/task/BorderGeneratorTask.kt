package dev.mexican.meetup.game.size.task

import org.bukkit.Material
import org.bukkit.World
import org.bukkit.scheduler.BukkitRunnable

/**
 * @author UKry
 * Created: 04/10/2022
 * Project UHCMeetup
 **/

class BorderGeneratorTask(val world : World, val size: Int) : BukkitRunnable() {

    var i = 0

    override fun run() {
        if(i >= 5) cancel()
        i++
        //z
        for (z in -size+ 1 .. size-1) {
            var loc = world.getHighestBlockAt(size, z).location.clone()
            if(world.getBlockAt(loc).type != Material.BEDROCK) {
                loc.add(0.0, 0.5, 0.0)
                world.getBlockAt(loc).type = Material.BEDROCK
            }
            loc = world.getHighestBlockAt(-size, z).location.clone()
            if(world.getBlockAt(loc).type != Material.BEDROCK) {
                loc.add(0.0, 0.5, 0.0)
                world.getBlockAt(loc).type = Material.BEDROCK
            }
        }
        for (x in -size+1 .. size - 1) {
            var loc = world.getHighestBlockAt(x, size).location.clone()
            if(world.getBlockAt(loc).type != Material.BEDROCK) {
                loc.add(0.0, 0.5, 0.0)
                world.getBlockAt(loc).type = Material.BEDROCK
            }
            loc = world.getHighestBlockAt(x, -size).location.clone()
            if(world.getBlockAt(loc).type != Material.BEDROCK) {
                loc.add(0.0, 0.5, 0.0)
                world.getBlockAt(loc).type = Material.BEDROCK
            }
        }
    }
}