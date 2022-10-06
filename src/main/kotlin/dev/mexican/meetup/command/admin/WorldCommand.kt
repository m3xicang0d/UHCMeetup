package dev.mexican.meetup.command.admin

import me.gleeming.command.Command
import me.gleeming.command.paramter.Param
import org.bukkit.World
import org.bukkit.entity.Player

/**
 * @author UKry
 * Created: 06/10/2022
 * Project UHCMeetup
 **/

class WorldCommand {

    @Command(names = ["world"], permission = "command.world")
    fun execute(player : Player, @Param(name = "world") world : World) {
        player.teleport(world.spawnLocation)
    }
}