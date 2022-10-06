package dev.mexican.meetup.game.border.task

import org.bukkit.Material
import org.bukkit.World
import org.bukkit.scheduler.BukkitRunnable

/**
 * @author UKry
 * Created: 04/10/2022
 * Project UHCMeetup
 **/

class BorderGeneratorTask(val world : World, val border : Int) : BukkitRunnable() {

    var i = 0

    override fun run() {
        if(i >= 5) cancel()
        //z
        for (z in -border .. border) {
            var loc = world.getHighestBlockAt(border, z).location.clone()
            if(world.getBlockAt(loc).type != Material.BEDROCK) {
                loc.add(0.0, 0.5, 0.0)
                world.getBlockAt(loc).type = Material.BEDROCK
            }
            loc = world.getHighestBlockAt(-border, z).location.clone()
            if(world.getBlockAt(loc).type != Material.BEDROCK) {
                loc.add(0.0, 0.5, 0.0)
                world.getBlockAt(loc).type = Material.BEDROCK
            }
        }
        for (x in -border .. border) {
            var loc = world.getHighestBlockAt(x, border).location.clone()
            if(world.getBlockAt(loc).type != Material.BEDROCK) {
                loc.add(0.0, 0.5, 0.0)
                world.getBlockAt(loc).type = Material.BEDROCK
            }
            loc = world.getHighestBlockAt(x, -border).location.clone()
            if(world.getBlockAt(loc).type != Material.BEDROCK) {
                loc.add(0.0, 0.5, 0.0)
                world.getBlockAt(loc).type = Material.BEDROCK
            }
        }
    }
}